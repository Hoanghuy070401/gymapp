# Implementation Plan: Home Screen + YouTube Video Player

**Date:** 2026-04-06  
**Branch:** source_ui  
**Status:** ✅ Implemented — Pending User Approval  
**Complexity:** High  
**Estimated Effort:** 3–4 days

---

## Overview

Nâng cấp màn Home theo mockup + thêm luồng video YouTube end-to-end:
- Home hiển thị video cards với thumbnail YouTube (không cần API key)
- Click → `VideoDetailScreen` với player đầy đủ (seek, quality, subtitle toggle)
- Player dùng `androidyoutubeplayer` (IFrame API wrapper) via `AndroidView`

---

## Phases

| # | Phase | Status | File |
|---|-------|--------|------|
| 1 | Dependencies & Data Layer | ✅ DONE | [phase-01](./phase-01-dependencies-data.md) |
| 2 | Home Screen UI Upgrade | ✅ DONE | [phase-02](./phase-02-home-ui.md) |
| 3 | Video Detail Screen + Player | ✅ DONE | [phase-03-video-detail-player.md](./phase-03-video-detail-player.md) |
| 4 | Navigation Wiring | ✅ DONE | [phase-04](./phase-04-navigation.md) |

---

## Architecture Overview

```
feature_home/
├── data/
│   └── VideoRepository.kt        # YouTube oEmbed fetch, Firebase videos
├── presentation/
│   ├── HomeScreen.kt              # Upgraded with VideoCard section
│   ├── HomeViewModel.kt           # + workoutVideos state
│   └── video/
│       ├── VideoDetailScreen.kt   # Full-screen player + description
│       └── VideoPlayerComposable.kt  # AndroidView YouTube wrapper
```

```
Screen.kt → add: VideoDetail route with videoId arg
AppNavHost.kt → wire HomeScreen.onNavigateToVideo → VideoDetailScreen
```

---

## Key Dependencies to Add

```toml
# feature_home/build.gradle.kts
androidyoutubeplayer = "com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0"
coil-compose = "io.coil-kt:coil-compose:2.7.0"
```

---

## Mockup Reference

Per provided mockup (image):
- Header: `Hi, {name}` + search/bell/profile icons
- Category grid: Workout | Progress Tracking | Nutrition | Community  
- **Recommendations** section: horizontal scroll, 160×180dp cards WITH video thumbnail + play button overlay
- **Weekly Challenge**: full-width card with image
- **Articles & Tips**: horizontal scroll cards with thumbnail images

---

## Success Criteria

- [ ] All Home sections match mockup design
- [ ] YouTube URL → thumbnail shown in card (no API key)
- [ ] Click card → VideoDetailScreen opens
- [ ] Player: play/pause, seek bar functional
- [ ] Quality selector bottom sheet (360p/480p/720p/1080p)
- [ ] Subtitle toggle button
- [ ] Player pauses when app goes background
- [ ] No memory leaks (DisposableEffect cleanup)
