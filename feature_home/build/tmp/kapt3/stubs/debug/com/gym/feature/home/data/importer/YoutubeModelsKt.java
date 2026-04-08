package com.gym.feature.home.data.importer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000b"}, d2 = {"SHORTS_REGEX", "Lkotlin/text/Regex;", "isShorts", "", "Lcom/gym/feature/home/data/importer/YoutubeVideoItem;", "(Lcom/gym/feature/home/data/importer/YoutubeVideoItem;)Z", "parseDurationMinutes", "", "isoDuration", "", "parseDurationSeconds", "feature_home_debug"})
public final class YoutubeModelsKt {
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex SHORTS_REGEX = null;
    
    /**
     * Parse ISO 8601 duration string to total minutes (rounded down).
     */
    private static final int parseDurationMinutes(java.lang.String isoDuration) {
        return 0;
    }
    
    /**
     * Parse ISO 8601 duration string to total seconds. e.g. "PT45S" → 45, "PT1M5S" → 65
     */
    public static final int parseDurationSeconds(@org.jetbrains.annotations.NotNull()
    java.lang.String isoDuration) {
        return 0;
    }
    
    public static final boolean isShorts(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.importer.YoutubeVideoItem $this$isShorts) {
        return false;
    }
}