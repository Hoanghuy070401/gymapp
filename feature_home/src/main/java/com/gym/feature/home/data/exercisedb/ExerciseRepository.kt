package com.gym.feature.home.data.exercisedb

import com.gym.core.base.GymLogger
import com.gym.domain.model.ExerciseInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for exercises.
 * Strategy: Cache-first
 *   1. Kiểm tra Firebase (ExerciseFirebaseCache).
 *   2. Nếu Firebase có data → trả về cache, KHÔNG gọi API.
 *   3. Nếu Firebase trống → gọi ExerciseDB API → trả kết quả về caller.
 *      (Admin tự quyết định có push lên Firebase không qua AdminExerciseImporter)
 */
@Singleton
class ExerciseRepository @Inject constructor(
    private val cache: ExerciseFirebaseCache
) {

    private val api: ExerciseDbApiService by lazy {
        val logging = HttpLoggingInterceptor { msg ->
            GymLogger.d(TAG, "[HTTP] $msg")
        }.apply { level = HttpLoggingInterceptor.Level.HEADERS }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(ExerciseDbApiService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseDbApiService::class.java)
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /** Cache-first: all exercises */
    suspend fun getExercises(
        apiKey: String,
        limit: Int = 30,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> {
        // 1. Try Firebase cache
        val cached = cache.getAllExercises(limit)
        if (cached.isSuccess && cached.getOrDefault(emptyList()).isNotEmpty()) {
            GymLogger.d(TAG, "Cache HIT — all exercises (${cached.getOrNull()?.size})")
            return cached
        }
        // 2. Fallback to API
        GymLogger.d(TAG, "Cache MISS — calling API for all exercises")
        return runCatching {
            api.getExercises(apiKey = apiKey, limit = limit, offset = offset)
                .map { it.toDomain(apiKey) }
        }.onFailure { GymLogger.e(TAG, it, "getExercises API failed offset=$offset") }
    }

    /** Cache-first: exercises by body part */
    suspend fun getExercisesByBodyPart(
        apiKey: String,
        bodyPart: String,
        limit: Int = 30,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> {
        // 1. Try Firebase cache
        if (cache.hasBodyPart(bodyPart)) {
            val cached = cache.getExercises(bodyPart, limit)
            if (cached.isSuccess && cached.getOrDefault(emptyList()).isNotEmpty()) {
                GymLogger.d(TAG, "Cache HIT — bodyPart=$bodyPart (${cached.getOrNull()?.size})")
                return cached
            }
        }
        // 2. Fallback to API
        GymLogger.d(TAG, "Cache MISS — calling API bodyPart=$bodyPart")
        return runCatching {
            api.getExercisesByBodyPart(apiKey = apiKey, bodyPart = bodyPart, limit = limit, offset = offset)
                .map { it.toDomain(apiKey) }
        }.onFailure { GymLogger.e(TAG, it, "getExercisesByBodyPart API failed bodyPart=$bodyPart") }
    }

    /** Cache-first: search by name */
    suspend fun searchByName(
        apiKey: String,
        name: String,
        limit: Int = 20
    ): Result<List<ExerciseInfo>> {
        // 1. Try Firebase full-text search (client-side on cached data)
        val cached = cache.searchByName(name, limit)
        if (cached.isSuccess && cached.getOrDefault(emptyList()).isNotEmpty()) {
            GymLogger.d(TAG, "Cache search HIT — query=$name")
            return cached
        }
        // 2. Fallback to API
        GymLogger.d(TAG, "Cache search MISS — calling API name=$name")
        return runCatching {
            api.searchByName(apiKey = apiKey, name = name.lowercase(), limit = limit)
                .map { it.toDomain(apiKey) }
        }.onFailure { GymLogger.e(TAG, it, "searchByName API failed name=$name") }
    }

    /** Body parts: Firebase first, auto-cache API result */
    suspend fun getBodyPartList(apiKey: String): Result<List<String>> {
        val cached = cache.getBodyPartList()
        if (cached.isSuccess && cached.getOrDefault(emptyList()).isNotEmpty()) {
            GymLogger.d(TAG, "Cache HIT — body parts")
            return cached
        }
        GymLogger.d(TAG, "Cache MISS — calling API for body parts")
        return runCatching {
            val parts = api.getBodyPartList(apiKey = apiKey)
            // Auto-cache lần tới không cần gọi API nữa
            cache.pushBodyPartList(parts)
            parts
        }.onFailure { GymLogger.e(TAG, it, "getBodyPartList API failed") }
    }

    /** Fetch exercise detail by ID (always API — detail không cache) */
    suspend fun getExerciseById(apiKey: String, id: String): Result<ExerciseInfo> =
        runCatching {
            api.getExerciseById(apiKey = apiKey, id = id).toDomain(apiKey)
        }.onFailure { GymLogger.e(TAG, it, "getExerciseById API failed id=$id") }

    // ── Admin: Direct API access (no cache) ──────────────────────────────────

    /** Dành cho Admin: Fetch raw từ API để import vào Firebase */
    suspend fun fetchRawExercisesForImport(
        apiKey: String,
        bodyPart: String? = null,
        limit: Int = 100,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> = runCatching {
        if (bodyPart != null) {
            api.getExercisesByBodyPart(apiKey = apiKey, bodyPart = bodyPart, limit = limit, offset = offset)
                .map { it.toDomain(apiKey) }
        } else {
            api.getExercises(apiKey = apiKey, limit = limit, offset = offset)
                .map { it.toDomain(apiKey) }
        }
    }.onFailure { GymLogger.e(TAG, it, "fetchRawForImport failed bodyPart=$bodyPart") }

    // ── Mapper ────────────────────────────────────────────────────────────────

    private fun ExerciseDbItem.toDomain(apiKey: String) = ExerciseInfo(
        id = id ?: "",
        name = (name ?: "").replaceFirstChar { it.uppercase() },
        bodyPart = bodyPart ?: "",
        target = target ?: "",
        equipment = equipment ?: "",
        gifUrl = "https://exercisedb.p.rapidapi.com/image?exerciseId=$id&resolution=180&rapidapi-key=$apiKey",
        secondaryMuscles = secondaryMuscles ?: emptyList(),
        instructions = instructions ?: emptyList()
    )

    companion object {
        private const val TAG = "ExerciseRepository"
    }
}
