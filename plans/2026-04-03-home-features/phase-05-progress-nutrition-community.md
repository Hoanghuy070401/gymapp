# Phase 05 — Progress Tracking, Nutrition & Community

**Plan:** [plan.md](plan.md)
**Parallelization:** ✅ Chạy song song với Phase 02, 03, 04 (sau Phase 01)
**Priority:** High
**Status:** Todo

## Tổng quan

Triển khai 3 tab còn lại trong Top Menu của Home: **Progress Tracking** (7.2), **Nutrition** (7.3), và **Community** (7.4).

## Tính năng cần triển khai

**7.2 Progress Tracking:**
- 7.2.1 Workout Log — lịch sử toàn bộ buổi tập, filter theo ngày/tuần/tháng
- 7.2.2 Progress Charts — biểu đồ cân nặng, số buổi tập/tuần, calories burned

**7.3 Nutrition:**
- 7.3.1 Meal Plans — kế hoạch ăn hàng tuần (7 ngày)
- 7.3.2 Meal Ideas
  - Breakfast / Lunch / Dinner tabs

**7.4 Community:**
- 7.4.1 Discussion Forum — hỗ trợ đăng bài (post), comment
- 7.4.2 Challenges & Competitions — danh sách thử thách cộng đồng

**9. Weekly Challenge** (section trên Home) — dữ liệu từ Community module

## Kiến trúc

```
feature_home/presentation/
├── progress/
│   ├── ProgressScreen.kt          ← NEW
│   ├── ProgressViewModel.kt       ← NEW
│   ├── WorkoutLogScreen.kt        ← NEW
│   └── ProgressChartScreen.kt     ← NEW
├── nutrition/
│   ├── NutritionScreen.kt         ← NEW
│   ├── NutritionViewModel.kt      ← NEW
│   ├── MealPlansScreen.kt         ← NEW
│   └── MealIdeasScreen.kt         ← NEW
└── community/
    ├── CommunityScreen.kt         ← NEW
    ├── CommunityViewModel.kt      ← NEW
    ├── ForumScreen.kt             ← NEW
    └── ChallengesScreen.kt        ← NEW
```

## File Ownership

- `feature_home/src/main/java/com/gym/feature/home/presentation/progress/*` ← NEW
- `feature_home/src/main/java/com/gym/feature/home/presentation/nutrition/*` ← NEW
- `feature_home/src/main/java/com/gym/feature/home/presentation/community/*` ← NEW

## Data Models (thêm vào domain)

```kotlin
// domain/model/MealPlan.kt
data class MealPlan(
    val day: String,          // "Monday", "Tuesday",...
    val breakfast: Meal,
    val lunch: Meal,
    val dinner: Meal
)
data class Meal(val name: String, val calories: Int, val protein: Int, val carbs: Int, val fat: Int)

// domain/model/Challenge.kt
data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val targetCount: Int,
    val currentCount: Int,
    val expiresAt: Long,
    val type: ChallengeType  // WEEKLY, MONTHLY
)

// domain/model/ForumPost.kt
data class ForumPost(
    val id: String,
    val authorName: String,
    val authorAvatarUrl: String?,
    val content: String,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: Long
)
```

## Charts Library

Sử dụng **Vico** (compose-native charting library) hoặc vẽ bằng Canvas thuần:
- `Canvas` API Compose cho line chart tiến độ
- `ProgressRing` (đã có ở core) cho vòng tròn tiến độ

## Firebase RTDB Schema mở rộng

```
users/{uid}/
├── ...existing...
├── workoutLog/
│   └── {sessionId}/
│       ├── date: Long
│       ├── durationMinutes: Int
│       └── exercisesCompleted: Int
└── weeklyChallenge/
    ├── current: Int      (buổi tập đã hoàn thành tuần này)
    └── target: Int       (5)
```

## Bước triển khai

### Bước 1: Progress Module
- `WorkoutLogScreen`: LazyColumn với `WorkoutLogCard` (ngày, thời gian, số exercises)
- Date filter: chip tabs (Tuần/Tháng/Năm)
- `ProgressChartScreen`: Line chart cân nặng, Bar chart số buổi tập

### Bước 2: Nutrition Module
- `MealPlansScreen`: 7-day calendar strip + meal cards ngày được chọn
- `MealIdeasScreen`: 3 tabs Breakfast/Lunch/Dinner với LazyColumn recipe cards

### Bước 3: Community Module
- `ForumScreen`: LazyColumn post cards với like/comment counts
- `ChallengesScreen`: Danh sách active challenges + `ProgressRing` cho mỗi challenge

## Todo List

- [ ] Tạo models: `MealPlan`, `Meal`, `Challenge`, `ForumPost` (domain)
- [ ] `ProgressScreen.kt` + `ProgressViewModel.kt`
- [ ] `WorkoutLogScreen.kt`
- [ ] `ProgressChartScreen.kt` (Canvas-based line chart)
- [ ] `NutritionScreen.kt` + `NutritionViewModel.kt`
- [ ] `MealPlansScreen.kt`
- [ ] `MealIdeasScreen.kt`
- [ ] `CommunityScreen.kt` + `CommunityViewModel.kt`
- [ ] `ForumScreen.kt`
- [ ] `ChallengesScreen.kt`
- [ ] Seed data: 7 ngày meal plan, 3 challenges, 5 forum posts

## Success Criteria

- ✅ WorkoutLog hiển thị đúng lịch sử theo filter
- ✅ ProgressChart vẽ được line/bar chart
- ✅ MealPlans đổi ngày → đổi nội dung meal
- ✅ ChallengesScreen tái dụng `ProgressRing` component

## Conflict Prevention

Phase này KHÔNG sửa bất kỳ file nào của Phase 02 (workout), Phase 03 (auth/profile), Phase 04 (resources). Chỉ tạo files mới trong subfolders `progress/`, `nutrition/`, `community/` của `feature_home`.

## Unresolved Questions

- Vico library hay Canvas thuần cho charts? (Vico nhẹ, Canvas không thêm dep)
- Forum: real-time Firebase? Hay chỉ static seed data cho MVP?
