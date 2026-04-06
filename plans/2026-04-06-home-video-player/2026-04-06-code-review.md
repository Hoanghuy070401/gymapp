# Code Review: Home Screen + YouTube Video Player

**Date:** 2026-04-06  
**Reviewer:** AI Code Review  
**Plan:** `plans/2026-04-06-home-video-player/`  
**Branch:** source_ui  

---

## Scope

| File | Lines | Role |
|------|-------|------|
| `feature_home/data/WorkoutVideo.kt` | 49 | Domain model + URL utilities |
| `feature_home/data/VideoRepository.kt` | 93 | Firebase data layer |
| `feature_home/presentation/HomeScreen.kt` | 490 | Main home UI (upgraded) |
| `feature_home/presentation/HomeViewModel.kt` | 151 | State management |
| `feature_home/presentation/video/YouTubePlayerComposable.kt` | 67 | Player wrapper |
| `feature_home/presentation/video/VideoDetailScreen.kt` | 322 | Detail screen + player |
| `app/navigation/Screen.kt` | 20 | Route definitions |
| `app/navigation/AppNavHost.kt` | ~265 | Navigation wiring |
| `gradle/libs.versions.toml` | 83 | Dependency catalog |
| `feature_home/build.gradle.kts` | 64 | Module deps |

**Lines analyzed:** ~1,604

---

## Overall Assessment

Implementation chắc chẽ, đúng kiến trúc MVVM. Player lifecycle được quản lý tốt. Một số vấn đề nhỏ về hardcoded colors và tiềm năng listener accumulation cần chú ý.

---

## Critical Issues 🔴

**None.** Không có lỗ hổng bảo mật, không có architectural violation nghiêm trọng.

---

## High Priority 🟠

### 🟠 H1 — Potential Listener Accumulation in `YouTubePlayerComposable`

**File:** `YouTubePlayerComposable.kt` lines 41–60

**Issue:** `remember { YouTubePlayerView(context) }` tạo ra 1 instance ổn định nhưng `DisposableEffect(videoId)` sẽ add listener mới mỗi lần `videoId` thay đổi mà không remove listener cũ (library không expose `removeListener`). Hiện tại safe vì `VideoDetailScreen` giữ nguyên `videoId`, nhưng nếu reuse composable với dynamic ID sẽ gây duplicate callbacks.

**Fix:**
```kotlin
// Thay DisposableEffect(videoId) → DisposableEffect(Unit)
// Xử lý video load dynamic bằng cách gọi youTubePlayer.loadVideo() từ external controller
DisposableEffect(Unit) {
    lifecycleOwner.lifecycle.addObserver(playerView)
    playerView.addYouTubePlayerListener(...)
    onDispose {
        lifecycleOwner.lifecycle.removeObserver(playerView)
        playerView.release()
    }
}
```

### 🟠 H2 — `HomeScreen.kt` sát giới hạn 500 lines

**Current:** ~490 lines — code-standards giới hạn 500 lines/file. Thêm bất kỳ feature mới nào sẽ vượt.

**Fix:** Extract `VideoCard` + `WorkoutVideosSection` → `WorkoutVideosSection.kt` riêng.

### 🟠 H3 — `VideoRepository` inject `FirebaseDatabase` trực tiếp thay qua DI

**Issue:** Dùng `FirebaseDatabase.getInstance()` internal — không testable và không nhất quán với `AuthRepository` pattern.

**Fix:**
```kotlin
@Singleton
class VideoRepository @Inject constructor(
    private val database: FirebaseDatabase  // inject from DI module
) { ... }
```

---

## Medium Priority 🟡

### 🟡 M1 — Hardcoded hex colors (code-standards violation)

Tìm thấy hardcoded colors trong:
- `VideoDetailScreen.kt` L288–290: level badge colors (6 raw hex)
- `HomeScreen.kt` L220–221, L472: gradient colors (5 raw hex)

**Fix:** Thêm vào `AppColors.kt`:
```kotlin
val LevelBeginner = Color(0xFF1B5E20)
val LevelBeginnerText = Color(0xFF69F0AE)
val LevelIntermediate = Color(0xFFE65100)
val LevelIntermediateText = Color(0xFFFFCC02)
val LevelAdvanced = Color(0xFF4A148C)
val LevelAdvancedText = Color(0xFFEA80FC)
```

### 🟡 M2 — Subtitle CC toggle không có actual effect

**File:** `VideoDetailScreen.kt` L192–204. Toggle chỉ flip `subtitlesEnabled` state, không gọi YouTube IFrame API để thực sự bật CC. UI nói "CC: Bật" nhưng subtitle không hiện.

**Fix (short-term):** Add Toast hoặc snackbar: _"Tải lại video để áp dụng subtitle"_
**Fix (long-term):** Reload player với IFrame playerVar `cc_load_policy=1`.

### 🟡 M3 — VideoDetail silent return khi `video == null`

**File:** `AppNavHost.kt` — nếu `homeViewModel.state.value.workoutVideos` chưa load xong khi navigate, `videos.find { it.id == videoId }` trả null → `return@composable` silently, screen trắng.

**Fix:** Hiển thị loading state hoặc error message thay vì silent return.

### 🟡 M4 — `qualityOptions` tạo mới mỗi recomposition

**File:** `VideoDetailScreen.kt` L58–64

**Fix:**
```kotlin
val qualityOptions = remember { listOf("auto" to "Auto", ...) }
```

---

## Low Priority 🟢

### 🟢 L1 — Unused import `Speed` icon
`VideoDetailScreen.kt` L15: `import androidx.compose.material.icons.filled.Speed` — unused → lint warning.

### 🟢 L2 — `lineHeight = 22.sp` hardcoded
`VideoDetailScreen.kt` L248 — nên dùng AppTypography override hoặc constant.

### 🟢 L3 — TopBar padding dùng raw `8.dp` thay `AppSpacing`
`VideoDetailScreen.kt` L132 — nhỏ nhưng không nhất quán.

### 🟢 L4 — Firebase `workoutVideos` node chưa có security rules
`VideoRepository` reads `workoutVideos` nhưng RTDB rules hiện chỉ cover `users/{uid}`. Node này cần:
```json
"workoutVideos": { ".read": "auth != null", ".write": false }
```

### 🟢 L5 — `WorkoutVideo.thumbnailUrl` recomputed mỗi lần access
`get() = videoId?.let { ... }` — nên eager init vì `WorkoutVideo` là immutable.

---

## Positive Observations ✅

- ✅ **Lifecycle management** — `DisposableEffect` + `removeObserver` + `release()` đúng chuẩn, chống leak
- ✅ **No API key** — thumbnail URL không cần key, zero dependency
- ✅ **Firebase fallback** — seed data graceful khi node trống, không crash
- ✅ **Architecture correct** — Screen → ViewModel → Repository → Firebase đúng boundaries
- ✅ **Navigation pattern** — lambda callbacks, không truyền `NavController` vào feature screen
- ✅ **GymLogger used** — không dùng raw `Log.d()`
- ✅ **VideoId nullability** — null-check `video.videoId ?: return` trước khi init player
- ✅ **Quality sheet UX** — comment rõ ràng về IFrame quality limitation
- ✅ **File sizes** — `VideoDetailScreen.kt` 322 lines, `VideoRepository.kt` 93 lines — đều trong limit

---

## Recommended Actions (Priority Order)

| # | Priority | Action | File |
|---|----------|--------|------|
| 1 | 🟠 High | `DisposableEffect(videoId)` → `DisposableEffect(Unit)` | `YouTubePlayerComposable.kt` |
| 2 | 🟠 High | Inject `FirebaseDatabase` vào `VideoRepository` constructor | `VideoRepository.kt` |
| 3 | 🟠 High | Extract `WorkoutVideosSection.kt` riêng (HomeScreen sắp vượt 500 lines) | `HomeScreen.kt` |
| 4 | 🟡 Med | Loading/error state khi `video == null` trong VideoDetail | `AppNavHost.kt` |
| 5 | 🟡 Med | Level badge colors → `AppColors.*` | `AppColors.kt` + `VideoDetailScreen.kt` |
| 6 | 🟡 Med | `qualityOptions` → `remember { }` | `VideoDetailScreen.kt` |
| 7 | 🟢 Low | Remove `Speed` import | `VideoDetailScreen.kt` |
| 8 | 🟢 Low | Thêm RTDB security rules cho `workoutVideos` | Firebase Console |

---

## Unresolved Questions

1. `setPlaybackQuality()` có hoạt động nhất quán trên các YouTube client versions không? Cần test thực tế.
2. `workoutVideos` Firebase node — admin seeding strategy? Firebase Console hay Admin SDK?
3. CC subtitle thực tế dùng `cc_load_policy=1` playerVar — khi nào triển khai?
