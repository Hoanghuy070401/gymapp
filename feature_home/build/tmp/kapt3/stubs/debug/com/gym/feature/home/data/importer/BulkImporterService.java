package com.gym.feature.home.data.importer;

/**
 * Orchestrates: WGER exercise page → YouTube search → embeddable filter → auto-tag → Firebase.
 *
 * Flow:
 * 1. fetchExercisePreviews()  — load WGER list (no YouTube quota used)
 * 2. User selects exercises in UI
 * 3. runImportSelected()      — YouTube search + sanitize + Firebase for selected
 *
 * Designed for ADMIN-ONLY use. Never call from user-facing flows.
 * YouTube quota: each exercise costs ~101 units (100 search + 1 videos.list).
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0004\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0002,-B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J4\u0010\u0017\u001a&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n0\u00182\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J(\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010 \u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J<\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u001e\u0010$\u001a\u001a\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020&0%H\u0082@\u00a2\u0006\u0002\u0010\'J>\u0010(\u001a\u00020\"2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001d0*2 \b\u0002\u0010$\u001a\u001a\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020&0%H\u0086@\u00a2\u0006\u0002\u0010+R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0011\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006."}, d2 = {"Lcom/gym/feature/home/data/importer/BulkImporterService;", "", "videoRepository", "Lcom/gym/feature/home/data/VideoRepository;", "(Lcom/gym/feature/home/data/VideoRepository;)V", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "cachedExercises", "", "Lcom/gym/feature/home/data/importer/WgerExerciseInfo;", "wgerApi", "Lcom/gym/feature/home/data/importer/WgerApiService;", "getWgerApi", "()Lcom/gym/feature/home/data/importer/WgerApiService;", "wgerApi$delegate", "Lkotlin/Lazy;", "youtubeApi", "Lcom/gym/feature/home/data/importer/YoutubeApiService;", "getYoutubeApi", "()Lcom/gym/feature/home/data/importer/YoutubeApiService;", "youtubeApi$delegate", "autoTag", "Lkotlin/Triple;", "exercise", "fetchExercisePreviews", "Lcom/gym/feature/home/data/importer/BulkImporterService$ExercisePreview;", "batchSize", "", "offset", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "inferLevel", "runImportInternal", "Lcom/gym/feature/home/data/importer/ImportResult;", "exercises", "onProgress", "Lkotlin/Function3;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runImportSelected", "selectedIds", "", "(Ljava/util/Set;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "ExercisePreview", "feature_home_debug"})
public final class BulkImporterService {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.VideoRepository videoRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy wgerApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy youtubeApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.gym.feature.home.data.importer.WgerExerciseInfo> cachedExercises;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "BulkImporterService";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.importer.BulkImporterService.Companion Companion = null;
    
    @javax.inject.Inject()
    public BulkImporterService(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.VideoRepository videoRepository) {
        super();
    }
    
    private final com.gym.feature.home.data.importer.WgerApiService getWgerApi() {
        return null;
    }
    
    private final com.gym.feature.home.data.importer.YoutubeApiService getYoutubeApi() {
        return null;
    }
    
    private final java.lang.String getApiKey() {
        return null;
    }
    
    /**
     * Load a page of exercises from WGER.
     * Results are cached so [runImportSelected] can reference full objects.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchExercisePreviews(int batchSize, int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview>> $completion) {
        return null;
    }
    
    /**
     * Import only the exercises whose IDs are in [selectedIds].
     * Uses the cached WGER results from the last [fetchExercisePreviews] call.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object runImportSelected(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Integer> selectedIds, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.home.data.importer.ImportResult> $completion) {
        return null;
    }
    
    private final java.lang.Object runImportInternal(java.util.List<com.gym.feature.home.data.importer.WgerExerciseInfo> exercises, kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onProgress, kotlin.coroutines.Continuation<? super com.gym.feature.home.data.importer.ImportResult> $completion) {
        return null;
    }
    
    private final kotlin.Triple<java.util.List<java.lang.String>, java.util.List<java.lang.String>, java.util.List<java.lang.String>> autoTag(com.gym.feature.home.data.importer.WgerExerciseInfo exercise) {
        return null;
    }
    
    private final java.lang.String inferLevel(com.gym.feature.home.data.importer.WgerExerciseInfo exercise) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/home/data/importer/BulkImporterService$Companion;", "", "()V", "TAG", "", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    /**
     * Lightweight exercise info shown in the selection list.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/gym/feature/home/data/importer/BulkImporterService$ExercisePreview;", "", "id", "", "name", "", "category", "muscles", "level", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCategory", "()Ljava/lang/String;", "getId", "()I", "getLevel", "getMuscles", "getName", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "feature_home_debug"})
    public static final class ExercisePreview {
        private final int id = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String category = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String muscles = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String level = null;
        
        public ExercisePreview(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        java.lang.String category, @org.jetbrains.annotations.NotNull()
        java.lang.String muscles, @org.jetbrains.annotations.NotNull()
        java.lang.String level) {
            super();
        }
        
        public final int getId() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCategory() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMuscles() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLevel() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview copy(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        java.lang.String category, @org.jetbrains.annotations.NotNull()
        java.lang.String muscles, @org.jetbrains.annotations.NotNull()
        java.lang.String level) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}