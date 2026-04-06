# Phase 02 — Workout Module & Search

**Plan:** [plan.md](plan.md)
**Parallelization:** ✅ Chạy song song với Phase 03, 04, 05 (sau Phase 01)
**Priority:** High
**Status:** Todo

## Tổng quan

Triển khai đầy đủ module Workout (tab 7.1 trong Top Menu) và màn hình Search (6.3). Module này tận dụng infrastructure `feature_workout` đã có sẵn.

## Tính năng cần triển khai

**7.1 Workout (Top Menu tab):**
- 7.1.1 Preset Routines → filter: Beginner / Intermediate / Advanced
- 7.1.2 Make your own routine → tạo lịch tập tùy chỉnh

**6.3 Search:**
- Filter: All / Workout / Nutrition
- Search bar với debounce
- Kết quả workout cards

**8. Recommended Training Sessions** (section trên Home):
- Dữ liệu tĩnh ban đầu (static), sau nối Firebase

## Kiến trúc

```
feature_workout/
├── presentation/
│   ├── WorkoutScreen.kt          (cập nhật — Tab container)
│   ├── WorkoutViewModel.kt       (cập nhật — thêm states)
│   ├── WorkoutContract.kt        (cập nhật — events/states)
│   ├── preset/
│   │   ├── PresetRoutinesScreen.kt   ← NEW
│   │   └── RoutineCard.kt            ← NEW
│   ├── custom/
│   │   └── CustomRoutineScreen.kt    ← NEW
│   └── search/
│       ├── SearchScreen.kt           ← NEW
│       └── SearchViewModel.kt        ← NEW
└── domain/ (sử dụng từ :domain module)
    └── GetWorkoutsUseCase
```

## File Ownership (Phase 02 sở hữu độc quyền)

- `feature_workout/src/main/java/com/gym/feature/workout/presentation/*` — tất cả files
- `feature_workout/src/main/java/com/gym/feature/workout/presentation/preset/*` ← NEW
- `feature_workout/src/main/java/com/gym/feature/workout/presentation/custom/*` ← NEW
- `feature_workout/src/main/java/com/gym/feature/workout/presentation/search/*` ← NEW

## Các bước triển khai

### Bước 1: Cập nhật WorkoutContract
```kotlin
data class WorkoutState(
    val isLoading: Boolean = false,
    val presetRoutines: List<Routine> = emptyList(),
    val filteredRoutines: List<Routine> = emptyList(),
    val selectedLevel: DifficultyLevel = DifficultyLevel.ALL,
    val error: String? = null
)

enum class DifficultyLevel { ALL, BEGINNER, INTERMEDIATE, ADVANCED }
```

### Bước 2: PresetRoutinesScreen
- Horizontal chip filter: Beginner | Intermediate | Advanced
- LazyVerticalGrid (2 cột) hiển thị RoutineCard
- RoutineCard: gradient bg, tên bài, thời gian, level badge

### Bước 3: CustomRoutineScreen
- Form thêm tên routine, chọn nhóm cơ, thêm exercises
- Lưu local vào Room (WorkoutDao)

### Bước 4: SearchScreen
- SearchBar với `KeyboardOptions.imeAction = ImeAction.Search`
- Filter chips: All | Workout | Nutrition
- Kết quả hiển thị dạng list cards
- Debounce 300ms tránh query spam

## Data Models cần thêm vào domain

```kotlin
// domain/model/Routine.kt
data class Routine(
    val id: String,
    val name: String,
    val durationMinutes: Int,
    val level: DifficultyLevel,
    val exerciseCount: Int,
    val gradientStart: Long, // Color hex
    val gradientEnd: Long
)
```

## Todo List

- [ ] Cập nhật `WorkoutContract.kt` với states mới
- [ ] Cập nhật `WorkoutViewModel.kt` — thêm filter logic
- [ ] Tạo `PresetRoutinesScreen.kt`
- [ ] Tạo `RoutineCard.kt`
- [ ] Tạo `CustomRoutineScreen.kt`
- [ ] Tạo `SearchScreen.kt` + `SearchViewModel.kt`
- [ ] Thêm `Routine` model vào domain
- [ ] Seed data tĩnh 6 preset routines (3 Beginner, 2 Intermediate, 1 Advanced)

## Success Criteria

- ✅ Filter chip đổi level → list routine refresh
- ✅ Search trả kết quả trong 300ms debounce
- ✅ CustomRoutine lưu vào Room
- ✅ Không conflict với Phase 03–05

## Conflict Prevention

Phase này KHÔNG sửa `Screen.kt`, `AppNavHost.kt`, `HomeScreen.kt`, hay bất kỳ file nào của `feature_home`, `feature_auth`.
