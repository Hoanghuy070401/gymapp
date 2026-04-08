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
 * Repository for fetching exercises from ExerciseDB API.
 * Requires RapidAPI key set in local.properties as EXERCISEDB_API_KEY.
 */
@Singleton
class ExerciseRepository @Inject constructor() {

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

    /** Fetch paginated exercises — all body parts */
    suspend fun getExercises(
        apiKey: String,
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> = runCatching {
        api.getExercises(apiKey = apiKey, limit = limit, offset = offset)
            .map { it.toDomain() }
    }.onFailure { e ->
        GymLogger.e(TAG, e, "getExercises failed offset=$offset")
    }

    /** Fetch exercises filtered by body part */
    suspend fun getExercisesByBodyPart(
        apiKey: String,
        bodyPart: String,
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<ExerciseInfo>> = runCatching {
        api.getExercisesByBodyPart(apiKey = apiKey, bodyPart = bodyPart, limit = limit, offset = offset)
            .map { it.toDomain() }
    }.onFailure { e ->
        GymLogger.e(TAG, e, "getExercisesByBodyPart failed bodyPart=$bodyPart")
    }

    /** Search exercises by name keyword */
    suspend fun searchByName(
        apiKey: String,
        name: String,
        limit: Int = 20
    ): Result<List<ExerciseInfo>> = runCatching {
        api.searchByName(apiKey = apiKey, name = name.lowercase(), limit = limit)
            .map { it.toDomain() }
    }.onFailure { e ->
        GymLogger.e(TAG, e, "searchByName failed name=$name")
    }

    /** Fetch exercise detail by ID */
    suspend fun getExerciseById(
        apiKey: String,
        id: String
    ): Result<ExerciseInfo> = runCatching {
        api.getExerciseById(apiKey = apiKey, id = id).toDomain()
    }.onFailure { e ->
        GymLogger.e(TAG, e, "getExerciseById failed id=$id")
    }

    /** Fetch available body part categories */
    suspend fun getBodyPartList(apiKey: String): Result<List<String>> = runCatching {
        api.getBodyPartList(apiKey = apiKey)
    }.onFailure { e ->
        GymLogger.e(TAG, e, "getBodyPartList failed")
    }

    private fun ExerciseDbItem.toDomain() = ExerciseInfo(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        bodyPart = bodyPart,
        target = target,
        equipment = equipment,
        gifUrl = gifUrl,
        secondaryMuscles = secondaryMuscles,
        instructions = instructions
    )

    companion object {
        private const val TAG = "ExerciseRepository"
    }
}
