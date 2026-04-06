package com.gym.feature.home.data

// ── Domain Model ─────────────────────────────────────────────────────────────

data class WorkoutVideo(
    val id: String,
    val title: String,
    val youtubeUrl: String,               // full YouTube URL
    val description: String = "",
    val durationMinutes: Int = 0,
    val level: String = "Beginner"        // Beginner / Intermediate / Advanced
) {
    /** Parsed YouTube video ID, null if URL is not a YouTube link */
    val videoId: String? get() = extractYouTubeVideoId(youtubeUrl)

    /** High-quality thumbnail URL (no API key needed) */
    val thumbnailUrl: String?
        get() = videoId?.let { youtubeThumbnailUrl(it) }
}

// ── Utilities ─────────────────────────────────────────────────────────────────

private val YOUTUBE_ID_PATTERNS = listOf(
    Regex("""(?:youtube\.com/watch\?v=|youtu\.be/)([a-zA-Z0-9_-]{11})"""),
    Regex("""youtube\.com/embed/([a-zA-Z0-9_-]{11})"""),
    Regex("""youtube\.com/shorts/([a-zA-Z0-9_-]{11})""")
)

/**
 * Extract 11-char YouTube video ID from any YouTube URL.
 * Returns null if URL is not recognized as a YouTube link.
 */
fun extractYouTubeVideoId(url: String): String? =
    YOUTUBE_ID_PATTERNS.firstNotNullOfOrNull { it.find(url)?.groupValues?.getOrNull(1) }

/**
 * Build a direct YouTube thumbnail URL — no API key required.
 * Uses maxresdefault (1280×720); caller may provide fallback to hqdefault on 404.
 */
fun youtubeThumbnailUrl(videoId: String, quality: YoutubeThumbnailQuality = YoutubeThumbnailQuality.MAX_RES): String =
    "https://img.youtube.com/vi/$videoId/${quality.filename}"

enum class YoutubeThumbnailQuality(val filename: String) {
    MAX_RES("maxresdefault.jpg"),   // 1280×720 — may 404 for old videos
    HIGH("hqdefault.jpg"),          // 480×360  — always exists
    MEDIUM("mqdefault.jpg"),        // 320×180
    DEFAULT("default.jpg")          // 120×90
}
