package com.gym.feature.home.presentation.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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
@Composable
fun YouTubePlayerComposable(
    videoId: String,
    startSeconds: Float = 0f,
    autoPlay: Boolean = true,
    onPlayerReady: (YouTubePlayer) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Keep a stable reference to YouTubePlayerView across recompositions
    val playerView = remember { YouTubePlayerView(context) }

    DisposableEffect(videoId) {
        // Register as lifecycle observer so player pauses in background
        lifecycleOwner.lifecycle.addObserver(playerView)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (autoPlay) {
                    youTubePlayer.loadVideo(videoId, startSeconds)
                } else {
                    youTubePlayer.cueVideo(videoId, startSeconds)
                }
                onPlayerReady(youTubePlayer)
            }
        })

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(playerView)
            playerView.release()
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = modifier
    )
}
