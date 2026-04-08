package com.gym.feature.home.presentation.video

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.gym.core.ui.theme.AppColors
import com.gym.core.ui.theme.AppTypography
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
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

    // State tracks whether the YouTube Player threw an internal error (like 152)
    var hasError by remember(videoId) { mutableStateOf(false) }

    // Keep a stable reference to YouTubePlayerView across recompositions
    val playerView = remember { YouTubePlayerView(context) }

    DisposableEffect(videoId) {
        // Register as lifecycle observer so player pauses in background
        lifecycleOwner.lifecycle.addObserver(playerView)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                hasError = false
                if (autoPlay) {
                    youTubePlayer.loadVideo(videoId, startSeconds)
                } else {
                    youTubePlayer.cueVideo(videoId, startSeconds)
                }
                onPlayerReady(youTubePlayer)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                // If anything fails inside the iframe (Error 150, 152, API error, etc.)
                hasError = true
            }
        })

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(playerView)
            playerView.release()
        }
    }

    Box(modifier = modifier) {
        // The underlying WebView iframe
        AndroidView(
            factory = { playerView },
            modifier = Modifier.fillMaxSize()
        )

        // The fallback overlay covers the broken iframe if there's an error
        AnimatedVisibility(
            visible = hasError,
            enter = fadeIn(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VideocamOff,
                        contentDescription = "Video Error",
                        tint = AppColors.OnSurfaceVariant,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Lỗi bảo mật từ YouTube (152)",
                        style = AppTypography.titleSmall,
                        color = AppColors.OnSurface
                    )
                    Text(
                        text = "Để xem video này, vui lòng mở ứng dụng YouTube.",
                        style = AppTypography.bodySmall,
                        color = AppColors.OnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=$videoId")
                            )
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC0000))
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Xem trên YouTube", style = AppTypography.labelLarge)
                    }
                }
            }
        }
    }
}
