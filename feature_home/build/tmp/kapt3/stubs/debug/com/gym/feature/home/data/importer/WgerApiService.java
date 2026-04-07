package com.gym.feature.home.data.importer;

/**
 * Retrofit interface targeting wger.de public REST API.
 * Base URL: https://wger.de/api/v2/
 *
 * No auth required for public exercise data.
 * language=2 → English only
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u0007\u001a\u00020\u00052\b\b\u0003\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/gym/feature/home/data/importer/WgerApiService;", "", "getExercises", "Lcom/gym/feature/home/data/importer/WgerExerciseListResponse;", "language", "", "limit", "offset", "format", "", "(IIILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature_home_debug"})
public abstract interface WgerApiService {
    
    /**
     * Fetch a page of exercise information including muscles, category, and translations.
     * @param language 2 = English
     * @param limit    items per page (max 100 per WGER docs)
     * @param offset   pagination offset
     * @param format   always "json"
     */
    @retrofit2.http.GET(value = "exerciseinfo/")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getExercises(@retrofit2.http.Query(value = "language")
    int language, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @retrofit2.http.Query(value = "format")
    @org.jetbrains.annotations.NotNull()
    java.lang.String format, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.home.data.importer.WgerExerciseListResponse> $completion);
    
    /**
     * Retrofit interface targeting wger.de public REST API.
     * Base URL: https://wger.de/api/v2/
     *
     * No auth required for public exercise data.
     * language=2 → English only
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}