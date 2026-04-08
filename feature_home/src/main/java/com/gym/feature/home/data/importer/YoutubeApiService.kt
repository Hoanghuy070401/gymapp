package com.gym.feature.home.data.importer

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface targeting YouTube Data API v3.
 * Base URL: https://www.googleapis.com/youtube/v3/
 *
 * Rate note: search costs 100 quota units; videos.list costs 1 unit.
 * Use sparingly — only call from admin/manual import flows.
 */
interface YoutubeApiService {

    /**
     * Search for videos matching a query.
     * @param query       e.g. "Barbell Squat tutorial form"
     * @param type        always "video"
     * @param maxResults  keep low (1–3) to minimize quota burn
     * @param key         YouTube Data API v3 key from BuildConfig.YOUTUBE_API_KEY
     */
    @GET("search")
    suspend fun searchVideos(
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 5,
        @Query("part") part: String = "id",
        @Query("videoEmbeddable") videoEmbeddable: String = "true",
        @Query("videoSyndicated") videoSyndicated: String = "true",
        @Query("key") key: String
    ): YoutubeSearchResponse

    /**
     * Fetch full detail (snippet, contentDetails, status) for a list of video IDs.
     * This is where we check status.embeddable.
     * @param ids  comma-separated video IDs (e.g. "abc123,def456")
     * @param key  YouTube Data API v3 key
     */
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("id") ids: String,
        @Query("part") part: String = "snippet,contentDetails,status",
        @Query("key") key: String
    ): YoutubeVideosResponse
}
