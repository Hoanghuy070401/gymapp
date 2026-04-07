package com.gym.feature.home.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0\tH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J{\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010(\u001a\u00020\u00032\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0006H\u00d6\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0018R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001a\u00a8\u0006-"}, d2 = {"Lcom/gym/feature/home/presentation/HomeState;", "", "isLoading", "", "isRefreshing", "userName", "", "avatarPath", "recommendedRoutines", "", "Lcom/gym/feature/home/presentation/RecommendedRoutine;", "workoutVideos", "Lcom/gym/feature/home/data/WorkoutVideo;", "recentArticles", "Lcom/gym/feature/home/presentation/ArticleTip;", "activeChallenge", "Lcom/gym/feature/home/presentation/WeeklyChallenge;", "error", "(ZZLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/gym/feature/home/presentation/WeeklyChallenge;Ljava/lang/String;)V", "getActiveChallenge", "()Lcom/gym/feature/home/presentation/WeeklyChallenge;", "getAvatarPath", "()Ljava/lang/String;", "getError", "()Z", "getRecentArticles", "()Ljava/util/List;", "getRecommendedRoutines", "getUserName", "getWorkoutVideos", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "feature_home_debug"})
public final class HomeState {
    private final boolean isLoading = false;
    private final boolean isRefreshing = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String avatarPath = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> recommendedRoutines = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.gym.feature.home.data.WorkoutVideo> workoutVideos = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.gym.feature.home.presentation.ArticleTip> recentArticles = null;
    @org.jetbrains.annotations.Nullable()
    private final com.gym.feature.home.presentation.WeeklyChallenge activeChallenge = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public HomeState(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.Nullable()
    java.lang.String avatarPath, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> recommendedRoutines, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.data.WorkoutVideo> workoutVideos, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.presentation.ArticleTip> recentArticles, @org.jetbrains.annotations.Nullable()
    com.gym.feature.home.presentation.WeeklyChallenge activeChallenge, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isRefreshing() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAvatarPath() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> getRecommendedRoutines() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.data.WorkoutVideo> getWorkoutVideos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.presentation.ArticleTip> getRecentArticles() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gym.feature.home.presentation.WeeklyChallenge getActiveChallenge() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public HomeState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.data.WorkoutVideo> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.feature.home.presentation.ArticleTip> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gym.feature.home.presentation.WeeklyChallenge component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gym.feature.home.presentation.HomeState copy(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.Nullable()
    java.lang.String avatarPath, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> recommendedRoutines, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.data.WorkoutVideo> workoutVideos, @org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.presentation.ArticleTip> recentArticles, @org.jetbrains.annotations.Nullable()
    com.gym.feature.home.presentation.WeeklyChallenge activeChallenge, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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