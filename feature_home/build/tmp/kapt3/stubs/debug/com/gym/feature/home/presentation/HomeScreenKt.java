package com.gym.feature.home.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a$\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a)\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0011\u0010\u000b\u001a\r\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\fH\u0003\u001a:\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u0098\u0001\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001eH\u0007\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\"H\u0003\u001aF\u0010#\u001a\u00020\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00062\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\"0\u00062\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u001e\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u001f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u001e\u0010)\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u001f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u0010\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020,H\u0003\u001a8\u0010-\u001a\u00020\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00062\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0001\u00a8\u0006."}, d2 = {"ArticleCard", "", "article", "Lcom/gym/feature/home/presentation/ArticleTip;", "ArticlesSection", "articles", "", "onSeeAll", "Lkotlin/Function0;", "HeaderIconButton", "onClick", "content", "Landroidx/compose/runtime/Composable;", "HomeHeaderSection", "userName", "", "onSearchClick", "onNotificationsClick", "onProfileClick", "HomeScreen", "viewModel", "Lcom/gym/feature/home/presentation/HomeViewModel;", "onNavigateToSearch", "onNavigateToNotifications", "onNavigateToProfile", "onNavigateToWorkout", "onNavigateToProgress", "onNavigateToNutrition", "onNavigateToCommunity", "onNavigateToVideo", "Lkotlin/Function1;", "Lcom/gym/feature/home/data/WorkoutVideo;", "RecommendedGradientCard", "routine", "Lcom/gym/feature/home/presentation/RecommendedRoutine;", "RecommendedSection", "videos", "routines", "onVideoClick", "RecommendedVideoCard", "video", "VideoCard", "WeeklyChallengeSection", "challenge", "Lcom/gym/feature/home/presentation/WeeklyChallenge;", "WorkoutVideosSection", "feature_home_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.presentation.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSearch, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToWorkout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNutrition, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCommunity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.gym.feature.home.data.WorkoutVideo, kotlin.Unit> onNavigateToVideo) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HomeHeaderSection(java.lang.String userName, kotlin.jvm.functions.Function0<kotlin.Unit> onSearchClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNotificationsClick, kotlin.jvm.functions.Function0<kotlin.Unit> onProfileClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HeaderIconButton(kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RecommendedSection(java.util.List<com.gym.feature.home.data.WorkoutVideo> videos, java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> routines, kotlin.jvm.functions.Function1<? super com.gym.feature.home.data.WorkoutVideo, kotlin.Unit> onVideoClick, kotlin.jvm.functions.Function0<kotlin.Unit> onSeeAll) {
    }
    
    /**
     * Recommended card with real YouTube thumbnail — matches design spec
     */
    @androidx.compose.runtime.Composable()
    private static final void RecommendedVideoCard(com.gym.feature.home.data.WorkoutVideo video, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * Fallback gradient card when no YouTube videos available
     */
    @androidx.compose.runtime.Composable()
    private static final void RecommendedGradientCard(com.gym.feature.home.presentation.RecommendedRoutine routine) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WorkoutVideosSection(@org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.feature.home.data.WorkoutVideo> videos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.gym.feature.home.data.WorkoutVideo, kotlin.Unit> onVideoClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSeeAll) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VideoCard(com.gym.feature.home.data.WorkoutVideo video, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WeeklyChallengeSection(com.gym.feature.home.presentation.WeeklyChallenge challenge) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ArticlesSection(java.util.List<com.gym.feature.home.presentation.ArticleTip> articles, kotlin.jvm.functions.Function0<kotlin.Unit> onSeeAll) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ArticleCard(com.gym.feature.home.presentation.ArticleTip article) {
    }
}