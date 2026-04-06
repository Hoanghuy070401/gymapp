package com.gym.feature.home.presentation.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a?\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0015\b\u0002\u0010\u0004\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005\u00a2\u0006\u0002\b\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0003H\u0003\u001a\u001e\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0012"}, d2 = {"ActionChip", "", "label", "", "icon", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "selected", "", "onClick", "LevelBadge", "level", "MetaBadge", "text", "VideoDetailScreen", "video", "Lcom/gym/feature/home/data/WorkoutVideo;", "onBack", "feature_home_debug"})
public final class VideoDetailScreenKt {
    
    /**
     * Full-screen video detail screen.
     *
     * Layout:
     * - TopBar (back + title)
     * - YouTubePlayer (16:9)
     * - Action row: Quality selector | Subtitle toggle
     * - Scrollable content: title, badges, description
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoDetailScreen(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.WorkoutVideo video, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ActionChip(java.lang.String label, kotlin.jvm.functions.Function0<kotlin.Unit> icon, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LevelBadge(java.lang.String level) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MetaBadge(java.lang.String text) {
    }
}