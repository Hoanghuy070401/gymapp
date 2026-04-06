# Research: YouTube Player & Thumbnail — Android/Compose

## 1. YouTube Video Playback

### Official Position
- Google's official **YouTube Android Player API** → **deprecated**
- Cannot play YouTube URLs directly via ExoPlayer/Media3 (ToS violation + proprietary streaming protocol)

### Recommended Library
**`com.pierfrancescosoffritti.androidyoutubeplayer:core`**
- Wraps YouTube IFrame Player API
- Stable, actively maintained
- Compose integration via `AndroidView`
- Auto lifecycle management via `LifecycleObserver`
- Supports: seek bar, playback controls, mute/unmute

```kotlin
dependencies {
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:custom-ui:12.1.0") // optional
}
```

### Compose Integration Pattern
```kotlin
@Composable
fun YouTubePlayer(videoId: String, modifier: Modifier = Modifier) {
    val lifecycle = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycle.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
        },
        modifier = modifier
    )
}
```

### Quality & Subtitles
- **Quality:** IFrame API exposes `setPlaybackQuality()` — available via WebView JS bridge
- `androidyoutubeplayer` custom-ui module (addon) or WebViewClient JS injection
- **Subtitles:** Controlled via IFrame API `cc_load_policy` parameter in playerVars
- Native subtitle toggle via `YT.Player.loadVideoById({..., cc_load_policy: 1})`

## 2. Thumbnail Loading (no API key needed)

### Direct URL Pattern (Recommended)
No network request, no API key:
```
https://img.youtube.com/vi/{VIDEO_ID}/maxresdefault.jpg   // 1280x720 (may 404 for old videos)
https://img.youtube.com/vi/{VIDEO_ID}/hqdefault.jpg       // 480x360 (always exists)
https://img.youtube.com/vi/{VIDEO_ID}/mqdefault.jpg       // 320x180
```

### Fallback Strategy
```kotlin
fun youtubeThumbnailUrl(videoId: String) = 
    "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
// On 404/error → fallback to hqdefault.jpg using Coil's error() builder
```

### oEmbed (for metadata too)
`GET https://www.youtube.com/oembed?url=https://youtube.com/watch?v={ID}&format=json`
Returns: `thumbnail_url`, `title`, `author_name` — no API key

## 3. Video ID Extraction

```kotlin
fun extractYouTubeVideoId(url: String): String? {
    val patterns = listOf(
        Regex("""(?:youtube\.com/watch\?v=|youtu\.be/)([a-zA-Z0-9_-]{11})"""),
        Regex("""youtube\.com/embed/([a-zA-Z0-9_-]{11})""")
    )
    return patterns.firstNotNullOfOrNull { it.find(url)?.groupValues?.get(1) }
}
```

## 4. Image Loading Library

**Coil** (`io.coil-kt:coil-compose`) — already likely in project or easy to add.
```kotlin
AsyncImage(model = youtubeThumbnailUrl(videoId), contentDescription = null)
```

## 5. Decisions

| Decision | Choice | Reason |
|----------|--------|--------|
| Player lib | `androidyoutubeplayer` | Only legal YouTube option, Compose-compatible |
| Thumbnails | Direct img.youtube.com URL | Zero dependencies, no API key, instant |
| Image loading | Coil | Compose-native, coroutine-based |
| Quality UI | Custom bottom sheet | IFrame API exposes limited quality options |
| Subtitles | IFrame playerVars toggle | No native Compose API, must toggle via JS bridge |

## Unresolved Questions
- Does project already have Coil in `libs.versions.toml`?
- Does `feature_home` module need a `feature_video` submodule or a new `:feature_video` module?
- Firebase structure for `workoutVideos` collection?
