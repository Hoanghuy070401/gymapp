# Phase 04 — Navigation Wiring

**Parent:** [plan.md](./plan.md)  
**Depends on:** Phase 01, 02, 03  
**Priority:** P2  
**Status:** ⬜ Todo

---

## Overview

Kết nối `VideoDetailScreen` vào navigation graph, wire callbacks từ `HomeScreen`, xử lý deep link.

---

## Requirements

- Thêm `Screen.VideoDetail` vào `Screen.kt`
- Thêm `composable(VideoDetail)` vào `AppNavHost.kt`
- Update `HomeScreen` signature: thêm `onNavigateToVideo: (String) -> Unit`
- Update `AppNavHost`: pass lambda `{ videoId -> navController.navigate(Screen.VideoDetail.route(videoId)) }`

---

## Implementation Steps

1. **`Screen.kt`** — add:
   ```kotlin
   data object VideoDetail : Screen("video_detail/{videoId}") {
       const val ARG_VIDEO_ID = "videoId"
       fun route(videoId: String) = "video_detail/$videoId"
   }
   ```

2. **`AppNavHost.kt`** — add composable:
   ```kotlin
   composable(
       route = Screen.VideoDetail.route,
       arguments = listOf(navArgument(Screen.VideoDetail.ARG_VIDEO_ID) { 
           type = NavType.StringType 
       })
   ) { backStackEntry ->
       val videoId = backStackEntry.arguments?.getString(Screen.VideoDetail.ARG_VIDEO_ID) ?: return@composable
       VideoDetailScreen(
           videoId = videoId,
           onBack = { navController.popBackStack() }
       )
   }
   ```

3. **`AppNavHost.kt`** — update `HomeScreen` call:
   ```kotlin
   composable(Screen.Home.route) {
       HomeScreen(
           viewModel = hiltViewModel(),
           // ... existing callbacks ...
           onNavigateToVideo = { videoId ->
               navController.navigate(Screen.VideoDetail.route(videoId))
           }
       )
   }
   ```

4. **`HomeScreen.kt`** — add param:
   ```kotlin
   fun HomeScreen(
       ...,
       onNavigateToVideo: (String) -> Unit = {}
   )
   ```
   Pass to `WorkoutVideosSection(onVideoClick = onNavigateToVideo)`

---

## Todo

- [ ] Update Screen.kt
- [ ] Update AppNavHost.kt (add VideoDetail composable)
- [ ] Update AppNavHost.kt (update HomeScreen call)  
- [ ] Update HomeScreen.kt parameter
- [ ] Add NavType import in AppNavHost

---

## Success Criteria

- Navigate Home → VideoDetail via card click ✅
- VideoDetail back → Home ✅
- No crash on rapid back-and-forth navigation ✅
- Player releases resources on pop ✅
