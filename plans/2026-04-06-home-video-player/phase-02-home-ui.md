# Phase 02 — Home Screen UI Upgrade

**Parent:** [plan.md](./plan.md)  
**Depends on:** Phase 01  
**Priority:** P1  
**Status:** ⬜ Todo

---

## Overview

Nâng cấp `HomeScreen.kt` theo mockup: thêm section **Workout Videos** với thumbnail YouTube, play button overlay, đúng style design system hiện tại.

---

## Key Insights

- `RecommendedCard` hiện dùng gradient — thay bằng thumbnail thật (Coil `AsyncImage`)
- Video section mới: horizontal scroll, card 160×180dp, thumbnail + play button overlay + title + meta
- `ArticleCard` cũng cần thumbnail thật (imageUrl field)
- Weekly Challenge card: thêm ảnh minh hoạ (hardcode hoặc URL)
- Design: dark overlay + play ▶ button trên thumbnail = "YouTube card" feel

---

## Requirements

- Thêm `videoUrl: String?` vào `RecommendedRoutine` hoặc tạo `WorkoutVideo` section riêng
- `VideoCard` composable: thumbnail (AsyncImage) + dark gradient overlay + ▶ button + title + duration/kcal
- `HomeScreen`: thêm `WorkoutVideosSection` ngay sau `RecommendedSection`
- Callback `onNavigateToVideo(videoId: String)` vào `HomeScreen`

---

## Architecture

```kotlin
// New composable in HomeScreen.kt
@Composable
private fun WorkoutVideosSection(
    videos: List<WorkoutVideo>,
    onVideoClick: (videoId: String) -> Unit,
    onSeeAll: () -> Unit
)

@Composable
private fun VideoCard(
    video: WorkoutVideo,
    onClick: () -> Unit
)
// Uses Coil AsyncImage for thumbnail
// Play button overlay (▶ in circle)
// Title + duration chips at bottom
```

---

## Related Code Files

- `feature_home/.../HomeScreen.kt` — add `WorkoutVideosSection`
- `feature_home/.../HomeViewModel.kt` — expose `workoutVideos: List<WorkoutVideo>`
- `feature_home/build.gradle.kts` — Coil dep (Phase 01)

---

## Implementation Steps

1. Add `onNavigateToVideo: (String) -> Unit = {}` param to `HomeScreen`
2. Update `HomeState` to include `workoutVideos: List<WorkoutVideo>`  
3. Create `VideoCard` composable:
   ```kotlin
   Box(160dp × 180dp) {
       AsyncImage(thumbnailUrl)  // fills card
       // dark bottom gradient overlay
       Box(gradient) {
           Text(title), MetaChips(duration, kcal)
           PlayButton()  // center circle with ▶
       }
   }
   ```
4. Create `WorkoutVideosSection` with `SectionHeader("Workout Videos", "See All")`
5. Add section to `HomeScreen` between Recommended and Weekly Challenge
6. Wire `onClick = { onNavigateToVideo(video.videoId) }`
7. Update `ArticleCard` to support optional thumbnail URL (AsyncImage with fallback gradient)

---

## Visual Design Spec

```
VideoCard (160×180dp):
┌────────────────────┐
│  [thumbnail image] │  ← AsyncImage, ContentScale.Crop
│                    │
│  ▶ (center circle) │  ← 40dp circle, white fill, ▶ icon
│──────────────────── │  ← dark gradient overlay bottom 60dp
│  Squat Exercise    │  ← bodyMedium Bold white
│  ▶12 Min  🔥120kcal│  ← labelSmall white 70%
└────────────────────┘
```

---

## Todo

- [ ] Add `workoutVideos` to `HomeState`
- [ ] Implement `VideoCard` composable
- [ ] Implement `WorkoutVideosSection`
- [ ] Add section to `HomeScreen` scroll
- [ ] Add `onNavigateToVideo` callback
- [ ] Update `ArticleCard` with optional thumbnail

---

## Success Criteria

- Home screen hiển thị video thumbnails từ YouTube (real images)
- Play button overlay visible trên mỗi card
- Click card → triggers `onNavigateToVideo(videoId)` callback

---

## Next Steps → Phase 03
