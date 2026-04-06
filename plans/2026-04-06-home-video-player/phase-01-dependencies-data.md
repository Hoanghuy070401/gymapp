# Phase 01 — Dependencies & Data Layer

**Parent:** [plan.md](./plan.md)  
**Priority:** P0 — Blocker for all other phases  
**Status:** ⬜ Todo

---

## Overview

Thêm thư viện YouTube player + Coil, định nghĩa data models cho video, tạo `VideoRepository`.

---

## Key Insights

- `androidyoutubeplayer:core:12.1.0` — IFrame wrapper, không cần API key, hỗ trợ lifecycle
- Thumbnail = `https://img.youtube.com/vi/{ID}/maxresdefault.jpg` — không cần network call metadata
- Coil (`io.coil-kt:coil-compose`) — load thumbnail async trong Compose
- Firebase structure: `workoutVideos/{id}` với fields: `title`, `youtubeUrl`, `description`, `duration`, `level`

---

## Requirements

- Thêm deps vào `feature_home/build.gradle.kts`
- Thêm `coil` vào `libs.versions.toml` (kiểm tra xem đã có chưa)
- Tạo `WorkoutVideo` domain model
- Tạo `VideoRepository` fetch từ Firebase `workoutVideos`
- Seed thử Firebase với 3–4 video mẫu (YouTube gym videos)

---

## Architecture

```kotlin
// Domain model
data class WorkoutVideo(
    val id: String,
    val title: String,
    val youtubeUrl: String,       // full URL e.g. https://youtube.com/watch?v=xxx
    val description: String = "",
    val durationMinutes: Int = 0,
    val level: String = "Beginner"  // Beginner / Intermediate / Advance
)

// Utility
fun extractYouTubeVideoId(url: String): String?
fun youtubeThumbnailUrl(videoId: String): String  // returns maxresdefault.jpg URL

// Repository
class VideoRepository @Inject constructor() {
    suspend fun getWorkoutVideos(): List<WorkoutVideo>  // reads Firebase workoutVideos node
}
```

---

## Related Code Files

- `feature_home/build.gradle.kts` — add deps
- `libs.versions.toml` — add coil, androidyoutubeplayer version aliases
- `feature_home/.../HomeViewModel.kt` — add `workoutVideos` state

---

## Implementation Steps

1. Check `libs.versions.toml` for existing Coil entry
2. Add to `libs.versions.toml`:
   ```toml
   [versions]
   coil = "2.7.0"
   androidYoutubePlayer = "12.1.0"
   
   [libraries]
   coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
   android-youtube-player = { group = "com.pierfrancescosoffritti.androidyoutubeplayer", name = "core", version.ref = "androidYoutubePlayer" }
   ```
3. Add to `feature_home/build.gradle.kts`:
   ```kotlin
   implementation(libs.coil.compose)
   implementation(libs.android.youtube.player)
   ```
4. Create `WorkoutVideo.kt` data class + utility functions
5. Create `VideoRepository.kt` with Firebase fetch
6. Update `HomeViewModel` to load `workoutVideos`
7. Seed Firebase with sample data (optional, can hardcode initially)

---

## Todo

- [ ] Verify Coil not already in libs.versions.toml
- [ ] Add versions to toml
- [ ] Add deps to build.gradle.kts
- [ ] Create WorkoutVideo model
- [ ] Create VideoRepository
- [ ] Update HomeState + HomeViewModel
- [ ] Add INTERNET permission check (should already be present for Firebase)

---

## Success Criteria

- `./gradlew :feature_home:assembleDebug` compiles without error
- `WorkoutVideo` list loads in `HomeViewModel.state.workoutVideos`

---

## Risk Assessment

| Risk | Mitigation |
|------|-----------|
| `androidyoutubeplayer` may have WebView issues on API 26 | minSdk=26, library supports API 17+, no issue |
| Coil version conflict | Pin to 2.7.0, check BOM compatibility |

---

## Next Steps → Phase 02
