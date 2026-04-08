package com.gym.feature.home.data.importer

import com.google.gson.annotations.SerializedName

// ── YouTube Data API v3 Response Models ──────────────────────────────────────
// Search: GET /youtube/v3/search?q=...&type=video&videoCategoryId=17&key=...
// Videos detail: GET /youtube/v3/videos?id=...&part=snippet,contentDetails,status&key=...

// ── Search response ───────────────────────────────────────────────────────────

data class YoutubeSearchResponse(
    val items: List<YoutubeSearchItem>
)

data class YoutubeSearchItem(
    val id: YoutubeVideoId
)

data class YoutubeVideoId(
    @SerializedName("videoId") val videoId: String
)

// ── Videos detail response ────────────────────────────────────────────────────

data class YoutubeVideosResponse(
    val items: List<YoutubeVideoItem>
)

data class YoutubeVideoItem(
    val id: String,
    val snippet: YoutubeSnippet,
    val contentDetails: YoutubeContentDetails,
    val status: YoutubeStatus
) {
    /** Duration in minutes (parsed from ISO 8601 duration, e.g. "PT12M30S" → 12) */
    val durationMinutes: Int
        get() = parseDurationMinutes(contentDetails.duration)
}

data class YoutubeSnippet(
    val title: String,
    val description: String,
    val channelTitle: String
)

data class YoutubeContentDetails(
    val duration: String   // ISO 8601: "PT12M30S"
)

data class YoutubeStatus(
    /** false → video owner disabled embedding — must be filtered out */
    val embeddable: Boolean
)

// ── Utility ───────────────────────────────────────────────────────────────────

/** Parse ISO 8601 duration string to total minutes (rounded down). */
private fun parseDurationMinutes(isoDuration: String): Int {
    return try {
        val hours = Regex("(\\d+)H").find(isoDuration)?.groupValues?.get(1)?.toInt() ?: 0
        val minutes = Regex("(\\d+)M").find(isoDuration)?.groupValues?.get(1)?.toInt() ?: 0
        hours * 60 + minutes
    } catch (e: Exception) {
        0
    }
}

/** Parse ISO 8601 duration string to total seconds. e.g. "PT45S" → 45, "PT1M5S" → 65 */
fun parseDurationSeconds(isoDuration: String): Int {
    return try {
        val hours = Regex("(\\d+)H").find(isoDuration)?.groupValues?.get(1)?.toInt() ?: 0
        val minutes = Regex("(\\d+)M").find(isoDuration)?.groupValues?.get(1)?.toInt() ?: 0
        val seconds = Regex("(\\d+)S").find(isoDuration)?.groupValues?.get(1)?.toInt() ?: 0
        hours * 3600 + minutes * 60 + seconds
    } catch (e: Exception) {
        Int.MAX_VALUE  // parse fail → assume valid (don't block)
    }
}

private val SHORTS_REGEX = Regex("#shorts", RegexOption.IGNORE_CASE)

/**
 * Returns true if this video is a YouTube Short.
 * Criteria (Option C — both checked):
 *  1. Title or description contains "#shorts" (case-insensitive)
 *  2. Total duration < 60 seconds
 */
val YoutubeVideoItem.isShorts: Boolean
    get() {
        val durationSeconds = parseDurationSeconds(contentDetails.duration)
        if (durationSeconds < 60) return true
        if (SHORTS_REGEX.containsMatchIn(snippet.title)) return true
        if (SHORTS_REGEX.containsMatchIn(snippet.description)) return true
        return false
    }

