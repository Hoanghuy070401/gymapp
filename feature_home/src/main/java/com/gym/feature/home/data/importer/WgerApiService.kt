package com.gym.feature.home.data.importer

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface targeting wger.de public REST API.
 * Base URL: https://wger.de/api/v2/
 *
 * No auth required for public exercise data.
 * language=2 → English only
 */
interface WgerApiService {

    /**
     * Fetch a page of exercise information including muscles, category, and translations.
     * @param language 2 = English
     * @param limit    items per page (max 100 per WGER docs)
     * @param offset   pagination offset
     * @param format   always "json"
     */
    @GET("exerciseinfo/")
    suspend fun getExercises(
        @Query("language") language: Int = 2,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("format") format: String = "json"
    ): WgerExerciseListResponse
}
