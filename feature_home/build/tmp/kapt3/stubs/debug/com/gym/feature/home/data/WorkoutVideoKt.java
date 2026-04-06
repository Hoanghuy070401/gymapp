package com.gym.feature.home.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u001a\u0018\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"YOUTUBE_ID_PATTERNS", "", "Lkotlin/text/Regex;", "extractYouTubeVideoId", "", "url", "youtubeThumbnailUrl", "videoId", "quality", "Lcom/gym/feature/home/data/YoutubeThumbnailQuality;", "feature_home_debug"})
public final class WorkoutVideoKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.text.Regex> YOUTUBE_ID_PATTERNS = null;
    
    /**
     * Extract 11-char YouTube video ID from any YouTube URL.
     * Returns null if URL is not recognized as a YouTube link.
     */
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String extractYouTubeVideoId(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return null;
    }
    
    /**
     * Build a direct YouTube thumbnail URL — no API key required.
     * Uses maxresdefault (1280×720); caller may provide fallback to hqdefault on 404.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String youtubeThumbnailUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String videoId, @org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.YoutubeThumbnailQuality quality) {
        return null;
    }
}