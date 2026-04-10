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
 * Repository cho bài tập — tách biệt hoàn toàn 2 luồng:
 *
 * ── User (Exercise Library) ────────────────────────────────────────────────
 *   CHỈ đọc từ Firebase Realtime Database.
 *   Nếu Firebase trống → trả về empty list (không fallback API).
 *   Data do Admin push lên trước.
 *
 * ── Admin (AdminExerciseImportScreen) ─────────────────────────────────────
 *   Dùng fetchRawExercisesForImport() để gọi ExerciseDB API.
 *   Sau đó Admin push kết quả lên Firebase qua ExerciseFirebaseCache.
 */
@Singleton
class ExerciseRepository @Inject constructor(
    private val cache: ExerciseFirebaseCache
) {

    // API chỉ dùng cho Admin import — lazy để không tạo khi user dùng app
    private val api: ExerciseDbApiService by lazy {
        val logging = HttpLoggingInterceptor { msg ->
            GymLogger.d(TAG, "[HTTP] $msg")
        }.apply { level = HttpLoggingInterceptor.Level.HEADERS }

        Retrofit.Builder()
            .baseUrl(ExerciseDbApiService.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseDbApiService::class.java)
    }

    // ── User: Firebase only ───────────────────────────────────────────────────

    /**
     * Lấy tất cả bài tập từ Firebase.
     * Trả về empty list nếu admin chưa import data.
     * KHÔNG gọi ExerciseDB API.
     */
    suspend fun getExercises(limit: Int = 30): Result<List<ExerciseInfo>> {
        return cache.getAllExercises(limit).also { result ->
            val count = result.getOrDefault(emptyList()).size
            if (count == 0) GymLogger.d(TAG, "Firebase empty — admin chưa import bài tập")
            else GymLogger.d(TAG, "Firebase HIT — ${count} bài tập (all)")
        }
    }

    /**
     * Lấy bài tập theo body part từ Firebase.
     * Trả về empty list nếu body part chưa được import.
     * KHÔNG gọi ExerciseDB API.
     */
    suspend fun getExercisesByBodyPart(bodyPart: String, limit: Int = 30): Result<List<ExerciseInfo>> {
        return cache.getExercises(bodyPart, limit).also { result ->
            val count = result.getOrDefault(emptyList()).size
            GymLogger.d(TAG, "Firebase bodyPart=$bodyPart → ${count} bài tập")
        }
    }

    /**
     * Tìm kiếm bài tập theo tên trong Firebase (client-side).
     * KHÔNG gọi ExerciseDB API.
     */
    suspend fun searchByName(query: String, limit: Int = 20): Result<List<ExerciseInfo>> {
        return cache.searchByName(query, limit).also { result ->
            GymLogger.d(TAG, "Firebase search '$query' → ${result.getOrDefault(emptyList()).size} kết quả")
        }
    }

    /**
     * Lấy danh sách body parts từ Firebase.
     * Trả về empty list nếu admin chưa import.
     * KHÔNG gọi ExerciseDB API.
     */
    suspend fun getBodyPartList(): Result<List<String>> {
        return cache.getBodyPartList().also { result ->
            val count = result.getOrDefault(emptyList()).size
            GymLogger.d(TAG, "Firebase body parts → ${count} nhóm cơ")
        }
    }

    // ── Admin: ExerciseDB API (chỉ dùng trong AdminExerciseImportScreen) ──────

    /**
     * Fetch body parts từ ExerciseDB API — CHỈ DÀNH CHO ADMIN.
     * Dùng để hiển thị danh sách cần import trong Admin screen.
     */
    suspend fun fetchBodyPartListFromApi(apiKey: String): Result<List<String>> =
        runCatching {
            api.getBodyPartList(apiKey = apiKey)
        }.onFailure { GymLogger.e(TAG, it, "Admin: fetchBodyPartList API failed") }

    /**
     * Fetch exercises từ ExerciseDB API — CHỈ DÀNH CHO ADMIN.
     * Admin gọi hàm này rồi push kết quả lên Firebase.
     */
    suspend fun fetchRawExercisesForImport(
        apiKey: String,
        bodyPart: String? = null,
        limit: Int = 100,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> = runCatching {
        if (bodyPart != null) {
            api.getExercisesByBodyPart(
                apiKey = apiKey, bodyPart = bodyPart,
                limit = limit, offset = offset
            ).map { it.toDomain(apiKey) }
        } else {
            api.getExercises(apiKey = apiKey, limit = limit, offset = offset)
                .map { it.toDomain(apiKey) }
        }
    }.onFailure { GymLogger.e(TAG, it, "Admin: fetchRaw failed bodyPart=$bodyPart") }

    // ── Mapper ────────────────────────────────────────────────────────────────

    private fun ExerciseDbItem.toDomain(apiKey: String) = ExerciseInfo(
        id               = id ?: "",
        name             = (name ?: "").replaceFirstChar { it.uppercase() },
        bodyPart         = bodyPart ?: "",
        target           = target ?: "",
        equipment        = equipment ?: "",
        gifUrl           = "https://exercisedb.p.rapidapi.com/image?exerciseId=$id&resolution=180&rapidapi-key=$apiKey",
        secondaryMuscles = secondaryMuscles ?: emptyList(),
        instructions     = instructions ?: emptyList()
    )

    companion object {
        private const val TAG = "ExerciseRepository"
    }
}
