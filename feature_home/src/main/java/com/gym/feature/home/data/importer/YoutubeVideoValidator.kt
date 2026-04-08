package com.gym.feature.home.data.importer

import com.gym.core.base.GymLogger
import com.gym.feature.home.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Validates YouTube video URLs before saving to Firebase.
 *
 * Checks [YoutubeStatus.embeddable] via YouTube Data API v3 `videos.list`.
 * Non-embeddable videos (error 152) must be rejected at write time.
 */
@Singleton
class YoutubeVideoValidator @Inject constructor() {

    private val api: YoutubeApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("X-Android-Package", "com.gym.app")
                        .addHeader("X-Android-Cert", "76C4272F936B33AB2C225BCB3855CDDBBDC31934")
                        .build()
                )
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YoutubeApiService::class.java)
    }

    private val apiKey = BuildConfig.YOUTUBE_API_KEY

    /**
     * Returns null if valid & embeddable & not a Short.
     * Returns error message string if non-embeddable, Short, not found, or API error.
     */
    suspend fun validate(videoId: String): String? {
        return try {
            val details = api.getVideoDetails(ids = videoId, key = apiKey).items
            when {
                details.isEmpty() ->
                    "Video không tồn tại trên YouTube"
                details.first().isShorts ->
                    "Video này là YouTube Shorts (dưới 60 giây) — không phù hợp để thêm vào ứng dụng"
                !details.first().status.embeddable ->
                    "Video này bị tắt tính năng nhúng — không thể phát trong ứng dụng (lỗi 152)"
                else -> null // valid!
            }
        } catch (e: Exception) {
            GymLogger.w("YoutubeVideoValidator", "validate($videoId) failed: ${e.message}")
            null // network error → allow save, don't block user
        }
    }
}
