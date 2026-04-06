package com.gym.feature.home.data;

/**
 * Fetches workout video data from Firebase Realtime Database.
 *
 * Firebase node: `workoutVideos/{id}`
 * Fields: title, youtubeUrl, description, durationMinutes, level
 *
 * Falls back to curated hardcoded list when Firebase is empty or unreachable.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086@\u00a2\u0006\u0002\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/gym/feature/home/data/VideoRepository;", "", "()V", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "getWorkoutVideos", "", "Lcom/gym/feature/home/data/WorkoutVideo;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "feature_home_debug"})
public final class VideoRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VideoRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.VideoRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public VideoRepository() {
        super();
    }
    
    /**
     * Load workout videos. Firebase first, fallback to hardcoded seed if empty.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWorkoutVideos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.feature.home.data.WorkoutVideo>> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/gym/feature/home/data/VideoRepository$Companion;", "", "()V", "TAG", "", "seedVideos", "", "Lcom/gym/feature/home/data/WorkoutVideo;", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Curated seed videos shown when Firebase is empty
         */
        private final java.util.List<com.gym.feature.home.data.WorkoutVideo> seedVideos() {
            return null;
        }
    }
}