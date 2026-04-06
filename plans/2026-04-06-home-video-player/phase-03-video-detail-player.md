# Phase 03 — Video Detail Screen + Player

**Parent:** [plan.md](./plan.md)  
**Depends on:** Phase 01, 02  
**Priority:** P1  
**Status:** ⬜ Todo

---

## Overview

Xây dựng `VideoDetailScreen` với YouTube player đầy đủ tính năng: play/pause, seek, quality selector, subtitle toggle, description.

---

## Key Insights

- `androidyoutubeplayer` IFrame wrapper — cung cấp `YouTubePlayer` object với:
  - `loadVideo(videoId, startSeconds)`, `play()`, `pause()`, `seekTo(seconds)`
  - `addYouTubePlayerListener` → callbacks: `onCurrentSecond`, `onVideoDuration`, `onStateChange`
- **Quality:** IFrame API `setPlaybackQuality()` — tuy nhiên YouTube phía client tự điều chỉnh; UI quality selector là informational + gọi JS bridge
- **Subtitles:** Toggle CC via `cc_load_policy` trong playerVars initialization hoặc JS injection
  ```javascript
  player.setOption("captions", "track", {"languageName": "Vietnamese"})
  ```
- `DisposableEffect` để release player khi screen rời composition

---

## Requirements

- `VideoDetailScreen(videoId: String, title: String, description: String)`
- Player full-width (16:9 ratio = `fillMaxWidth().aspectRatio(16f/9f)`)
- Controls: play/pause (tap on video), seek bar (built-in player controls)
- Quality sheet: Bottom sheet với options 360p / 480p / 720p / 1080p
- Subtitle toggle: icon button → bật/tắt CC
- Description section below player: title, level badge, duration, full description text
- Back button: top-left with `navController.popBackStack()`

---

## Architecture

```
feature_home/presentation/video/
├── VideoDetailScreen.kt      # Main composable
├── VideoPlayerComposable.kt  # AndroidView wrapper for YouTubePlayerView
└── VideoQualitySheet.kt      # Bottom sheet quality selector
```

### VideoDetailScreen Layout
```
Column {
  TopBar(title, onBack)
  YoutubePlayerComposable(videoId)  // 16:9, fullWidth
  Row { QualityButton, SubtitleToggleButton }
  VideoInfoSection(title, level, duration, description)
}
```

### YouTubePlayerComposable
```kotlin
@Composable
fun YouTubePlayerComposable(
    videoId: String,
    modifier: Modifier = Modifier,
    onPlayerReady: (YouTubePlayer) -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(videoId) {
        val playerView = YouTubePlayerView(context)
        // addObserver, addListener, loadVideo
        onDispose { playerView.release() }
    }
    AndroidView(factory = { playerView }, modifier = modifier)
}
```

### Quality Sheet
```kotlin
@Composable
fun VideoQualitySheet(
    currentQuality: String,
    onQualitySelected: (String) -> Unit,
    onDismiss: () -> Unit
)
// Options: "auto", "hd1080", "hd720", "large" (480p), "medium" (360p)
// Calls: youTubePlayer.setPlaybackQuality(quality) via stored reference
```

### Subtitle Toggle
```kotlin
var subtitlesEnabled by remember { mutableStateOf(false) }
IconButton(onClick = {
    subtitlesEnabled = !subtitlesEnabled
    // Inject JS: player.setOption(...) via WebView JS bridge
    // Note: YouTubePlayerView exposes webview -- limited access
    // Alternative: re-load with different playerVars
})
```

---

## Related Code Files

- `feature_home/build.gradle.kts` — `androidyoutubeplayer` dep
- `Screen.kt` — add `VideoDetail` route
- `AppNavHost.kt` — add `VideoDetailScreen` composable
- `HomeScreen.kt` — `onNavigateToVideo` callback

---

## Implementation Steps

1. Create `video/` package under `presentation/`
2. Implement `YouTubePlayerComposable` with `AndroidView` + `DisposableEffect`
3. Implement `VideoDetailScreen`:
   - Top bar: back arrow + title (ellipsis overflow)
   - Player: `YouTubePlayerComposable`, 16:9 ratio
   - Action row: quality icon + subtitle CC icon
   - Scroll content: title, badges (level, duration), divider, description text
4. Implement `VideoQualitySheet` (ModalBottomSheet)
5. Implement subtitle toggle via playerVars or JS bridge
6. Add `VideoDetailViewModel` (optional — could be stateless if data passed as args)
7. Handle `onBackPressed` while player is fullscreen

---

## Navigation Arguments

```kotlin
// Screen.kt
data object VideoDetail : Screen("video_detail/{videoId}") {
    const val ARG_VIDEO_ID = "videoId"
    fun route(videoId: String) = "video_detail/$videoId"
}

// AppNavHost.kt
composable(
    route = Screen.VideoDetail.route,
    arguments = listOf(navArgument("videoId") { type = NavType.StringType })
) { backStackEntry ->
    val videoId = backStackEntry.arguments?.getString("videoId") ?: return@composable
    VideoDetailScreen(videoId = videoId, onBack = { navController.popBackStack() })
}
```

---

## Todo

- [ ] Create `video/` package
- [ ] `YouTubePlayerComposable` with lifecycle mgmt
- [ ] `VideoDetailScreen` full layout
- [ ] `VideoQualitySheet` bottom sheet
- [ ] Subtitle CC toggle
- [ ] Memory leak test: rotate screen, background app

---

## Success Criteria

- Player loads correct YouTube video by ID
- Play/pause, seek bar functional
- Quality sheet opens with options, selection applied
- CC button toggles subtitles
- Back returns to Home without crash
- No memory leak on exit

---

## Risk Assessment

| Risk | Impact | Mitigation |
|------|--------|-----------|
| IFrame subtitle API unreliable | Medium | Provide "Open in YouTube" fallback button |
| Quality setter may be no-op (YouTube auto) | Low | Show as preference hint, not guarantee |
| AndroidView recomposition leak | High | Use `key(videoId)` + `DisposableEffect` properly |
| WebView crash on older API 26 | Low | minSdk=26, library tested on API 16+ |

---

## Security Considerations

- Video IDs are validated via regex before passing to player (prevent XSS via malformed IDs)
- No API keys stored client-side
- HTTPS-only thumbnail URLs

---

## Next Steps → Phase 04
