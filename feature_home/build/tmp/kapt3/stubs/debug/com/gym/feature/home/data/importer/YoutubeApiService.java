package com.gym.feature.home.data.importer;

/**
 * Retrofit interface targeting YouTube Data API v3.
 * Base URL: https://www.googleapis.com/youtube/v3/
 *
 * Rate note: search costs 100 quota units; videos.list costs 1 unit.
 * Use sparingly — only call from admin/manual import flows.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ@\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\u00052\b\b\u0003\u0010\f\u001a\u00020\u00052\b\b\u0003\u0010\r\u001a\u00020\u000e2\b\b\u0003\u0010\u0006\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/gym/feature/home/data/importer/YoutubeApiService;", "", "getVideoDetails", "Lcom/gym/feature/home/data/importer/YoutubeVideosResponse;", "ids", "", "part", "key", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchVideos", "Lcom/gym/feature/home/data/importer/YoutubeSearchResponse;", "query", "type", "maxResults", "", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature_home_debug"})
public abstract interface YoutubeApiService {
    
    /**
     * Search for videos matching a query.
     * @param query       e.g. "Barbell Squat tutorial form"
     * @param type        always "video"
     * @param maxResults  keep low (1–3) to minimize quota burn
     * @param key         YouTube Data API v3 key from BuildConfig.YOUTUBE_API_KEY
     */
    @retrofit2.http.GET(value = "search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchVideos(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @retrofit2.http.Query(value = "type")
    @org.jetbrains.annotations.NotNull()
    java.lang.String type, @retrofit2.http.Query(value = "maxResults")
    int maxResults, @retrofit2.http.Query(value = "part")
    @org.jetbrains.annotations.NotNull()
    java.lang.String part, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.home.data.importer.YoutubeSearchResponse> $completion);
    
    /**
     * Fetch full detail (snippet, contentDetails, status) for a list of video IDs.
     * This is where we check status.embeddable.
     * @param ids  comma-separated video IDs (e.g. "abc123,def456")
     * @param key  YouTube Data API v3 key
     */
    @retrofit2.http.GET(value = "videos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVideoDetails(@retrofit2.http.Query(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String ids, @retrofit2.http.Query(value = "part")
    @org.jetbrains.annotations.NotNull()
    java.lang.String part, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.home.data.importer.YoutubeVideosResponse> $completion);
    
    /**
     * Retrofit interface targeting YouTube Data API v3.
     * Base URL: https://www.googleapis.com/youtube/v3/
     *
     * Rate note: search costs 100 quota units; videos.list costs 1 unit.
     * Use sparingly — only call from admin/manual import flows.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}