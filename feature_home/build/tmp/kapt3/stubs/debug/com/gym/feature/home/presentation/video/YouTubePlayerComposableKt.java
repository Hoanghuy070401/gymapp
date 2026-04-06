package com.gym.feature.home.presentation.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aD\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"YouTubePlayerComposable", "", "videoId", "", "startSeconds", "", "autoPlay", "", "onPlayerReady", "Lkotlin/Function1;", "Lcom/pierfrancescosoffritti/androidyoutubeplayer/core/player/YouTubePlayer;", "modifier", "Landroidx/compose/ui/Modifier;", "feature_home_debug"})
public final class YouTubePlayerComposableKt {
    
    /**
     * Reusable YouTube player composable backed by the IFrame API via AndroidView.
     *
     * Lifecycle is managed automatically: the [YouTubePlayerView] is added as a
     * [LifecycleObserver] so it pauses/resumes with the host Activity/Fragment.
     * [DisposableEffect] releases the WebView on composition leave to prevent leaks.
     *
     * @param videoId     11-char YouTube video ID (e.g. "Lvh7aZ6Txg0")
     * @param startSeconds Seek position on load (default 0)
     * @param autoPlay    Whether to start playback immediately (default true)
     * @param onPlayerReady Callback exposing [YouTubePlayer] for external control
     * @param modifier    Modifier for the player view (set height to maintain 16:9)
     */
    @androidx.compose.runtime.Composable()
    public static final void YouTubePlayerComposable(@org.jetbrains.annotations.NotNull()
    java.lang.String videoId, float startSeconds, boolean autoPlay, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer, kotlin.Unit> onPlayerReady, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}