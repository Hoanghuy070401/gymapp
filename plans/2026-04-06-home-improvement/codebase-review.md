# Codebase Review — FitBody Android Home Screen

**Date:** 2026-04-06  
**Branch:** source_ui  
**Scope:** 63 .kt files, ~4,200 lines  

---

## Tổng quan

Codebase có kiến trúc MVVM tốt, Hilt DI đúng chuẩn, auth flow hoàn chỉnh. Vấn đề chủ yếu ở tầng **presentation** của `feature_home` — chưa khớp với design spec, HomeScreen sắp vượt file size limit, một số tech debt nhỏ.

---

## Phát hiện theo mức độ

### 🔴 Critical

**Không có critical issue.** App không có security vulnerability hay crash risk sau các fix trước.

---

### 🟠 High Priority

#### H1 — `HomeScreen` dùng `Column + verticalScroll` thay `LazyColumn`

`GymScaffold(scrollable = true)` wrapper dùng `Column + verticalScrollState` → render **toàn bộ content cùng lúc** (eager composition). Khi có nhiều video cards, recommended sections → **jank, memory waste, ANR risk** trên low-end devices.

```kotlin
// ❌ Hiện tại — eager render tất cả
GymScaffold(scrollable = true) {
    HomeHeaderSection(...)
    HomeCategoryGrid(...)
    RecommendedSection(...)   // 4 cards
    WorkoutVideosSection(...) // 4 video cards + AsyncImage
    WeeklyChallengeSection(...)
    ArticlesSection(...)
}

// ✅ Target — lazy render
LazyColumn {
    item { HomeHeaderSection(...) }
    item { HomeCategoryGrid(...) }
    item { RecommendedSection(...) }  // nội bộ → LazyRow
    item { WorkoutVideosSection(...) }
    item { WeeklyChallengeSection(...) }
    item { ArticlesSection(...) }
}
```

#### H2 — Design spec không khớp: `RecommendedCard` dùng gradient thay ảnh thật

Design yêu cầu card với real gym photos + play button + star + duration/calories. Hiện tại chỉ có gradient màu tím. `WorkoutVideoSection` có đúng layout nhưng `RecommendedSection` thì không.

→ **Fix:** Map `RecommendedRoutine` với YouTube video seed data, dùng `VideoCard` component cho cả hai section.

#### H3 — Không có Bottom Navigation Bar

Design có bottom nav (Home | Favorites | ? | Support). Hiện chỉ có full-screen content không có nav bar.

---

### 🟡 Medium Priority

#### M1 — `VideoRepository` không dùng constructor DI

```kotlin
// ❌ Not testable
@Singleton
class VideoRepository @Inject constructor() {
    private val database = FirebaseDatabase.getInstance() // internal
}

// ✅ Inject qua DI module
@Singleton
class VideoRepository @Inject constructor(
    private val database: FirebaseDatabase
)
```

#### M2 — `WorkoutVideo` model đặt sai layer

`WorkoutVideo` nằm ở `feature_home/data/` — vi phạm Clean Architecture. Domain models phải ở `domain/model/`. Hiện tại `feature_workout` module không thể access `WorkoutVideo` (cross-feature coupling forbidden).

#### M3 — `HomeScreen.kt` 501 lines — vượt limit

Code-standards giới hạn 500 lines/file. Cần extract:
- `WorkoutVideosSection.kt`
- `RecommendedSection.kt`  
- `ArticleSection.kt`

---

### 🟢 Low Priority

#### L1 — Hardcoded hex colors (8 instances)

Tìm thấy trong `VideoDetailScreen.kt` và `HomeScreen.kt`:
```kotlin
Color(0xFF1B5E20), Color(0xFF69F0AE)  // level colors
Color(0xFF4A148C), Color(0xFF7B1FA2)  // gradient
Color(0xFF3E2723), Color(0xFF1A237E)  // article bg
```
→ Thêm vào `AppColors.kt`: `LevelBeginner`, `LevelIntermediate`, `LevelAdvanced`

#### L2 — `qualityOptions` recreated mỗi recomposition

`VideoDetailScreen.kt` L58: `val qualityOptions = listOf(...)` → thêm `remember { }`.

#### L3 — `Speed` icon import unused

`VideoDetailScreen.kt`: `import androidx.compose.material.icons.filled.Speed` unused.

#### L4 — Firebase RTDB security rules chưa có cho `workoutVideos` node

Node `workoutVideos` đang public read theo default rules. Cần thêm:
```json
"workoutVideos": { ".read": "auth != null", ".write": false }
```

---

## Điểm tốt ✅

- ✅ Auth flow hoàn chỉnh (login → verify → setup → home)
- ✅ Hilt DI đúng pattern, scoping đúng  
- ✅ `StateFlow<UiState>` pattern nhất quán
- ✅ Navigation lambda callbacks (không truyền NavController vào feature screens)
- ✅ Firebase timeout + seed fallback (fix vừa done)
- ✅ GymLogger thay raw Log
- ✅ `DisposableEffect` + player lifecycle đúng
- ✅ Google Sign-In via Credential Manager API (modern approach)
- ✅ Email verification flow đúng
- ✅ Version catalog (`libs.versions.toml`) đầy đủ

---

## Improvement Plan (Proposed)

| # | Việc cần làm | Priority | Effort |
|---|-------------|----------|--------|
| 1 | Refactor HomeScreen → LazyColumn | 🟠 High | 2h |
| 2 | Implement Bottom Navigation Bar | 🟠 High | 3h |
| 3 | Upgrade RecommendedCard → dùng ảnh thật (VideoCard pattern) | 🟠 High | 2h |
| 4 | Fix VideoRepository DI | 🟡 Med | 30m |
| 5 | Extract HomeScreen sub-sections vào files riêng | 🟡 Med | 1h |
| 6 | Add level colors vào AppColors | 🟢 Low | 15m |
| 7 | Fix qualityOptions + remove Speed import | 🟢 Low | 5m |
| 8 | Firebase RTDB security rules cho workoutVideos | 🟢 Low | 10m |

---

## Unresolved Questions

1. Bottom nav items: Home + Favorites + ? + Support — item thứ 3 là gì?
2. `WorkoutVideo` di chuyển sang `:domain` module hay giữ ở `feature_home`? (Nếu chỉ home dùng → YAGNI → giữ nguyên)
3. RecommendedRoutine có muốn map với YouTube video thật hay là static content riêng?
