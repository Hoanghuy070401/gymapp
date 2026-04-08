package com.gym.feature.home.data.exercisedb

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

// ── ExerciseDB Retrofit API Interface ────────────────────────────────────────
interface ExerciseDbApiService {

    /** List all exercises — paginated */
    @GET("exercises")
    suspend fun getExercises(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = RAPID_HOST,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): List<ExerciseDbItem>

    /** List exercises by body part */
    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = RAPID_HOST,
        @Path("bodyPart") bodyPart: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): List<ExerciseDbItem>

    /** Search exercises by name */
    @GET("exercises/name/{name}")
    suspend fun searchByName(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = RAPID_HOST,
        @Path("name") name: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): List<ExerciseDbItem>

    /** Get exercise detail by ID */
    @GET("exercises/exercise/{id}")
    suspend fun getExerciseById(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = RAPID_HOST,
        @Path("id") id: String
    ): ExerciseDbItem

    /** Get all available body part categories */
    @GET("exercises/bodyPartList")
    suspend fun getBodyPartList(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = RAPID_HOST
    ): List<String>

    companion object {
        const val BASE_URL = "https://exercisedb.p.rapidapi.com/"
        const val RAPID_HOST = "exercisedb.p.rapidapi.com"
    }
}
