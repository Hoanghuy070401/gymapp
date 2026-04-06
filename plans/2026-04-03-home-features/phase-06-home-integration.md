# Phase 06 — Home Dashboard Integration

**Plan:** [plan.md](plan.md)
**Parallelization:** ⚠️ Chỉ bắt đầu SAU KHI Phase 02–05 hoàn thành
**Priority:** High
**Status:** IN_PROGRESS

## Tổng quan

Kết nối toàn bộ `HomeScreen.kt` với dữ liệu thực từ Firebase + Room, tích hợp Home Header (với các nút Search, Notifications, Profile), Category Grid Menu, và các sections Home (Recommendations, Weekly Challenge, Articles & Tips).

## Tính năng cần hoàn thiện

**HomeScreen refactor:**
- Header: Greeting text, tên user thực từ Firebase Auth. Thêm action icons: Search, Notifications, Profile avatar.
- Category Grid Menu: 4 nút mở sang màn hình mới: Workout | Progress Tracking | Nutrition | Community. KHÔNG DÙNG TabRow/HorizontalPager.
- Section "Recommendations": Hiển thị routine với thumbnail, title, time, calories.
- Section "Weekly Challenge": sync với Firebase
- Section "Articles & Tips": lấy bài mới nhất từ ArticleRepository

**HomeViewModel:**
- Tổng hợp data từ nhiều sources

## Kiến trúc

```
feature_home/presentation/
├── HomeScreen.kt          ← CẬP NHẬT (Phase 06 sở hữu)
├── HomeViewModel.kt       ← NEW
└── categories/
    └── HomeCategoryGrid.kt ← NEW (Grid các nút điều hướng)
```

## File Ownership

- `feature_home/src/main/java/com/gym/feature/home/presentation/HomeScreen.kt` ← CẬP NHẬT
- `feature_home/src/main/java/com/gym/feature/home/presentation/HomeViewModel.kt` ← NEW
- `feature_home/src/main/java/com/gym/feature/home/presentation/categories/HomeCategoryGrid.kt` ← NEW

## HomeViewModel

```kotlin
data class HomeState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val avatarPath: String? = null,
    val weeklyWorkoutsCompleted: Int = 0,
    val weeklyWorkoutsTarget: Int = 5,
    val recommendedRoutines: List<Routine> = emptyList(),
    val recentArticles: List<Article> = emptyList(),
    val activeChallenge: Challenge? = null,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val workoutRepository: WorkoutRepository,
    private val articleRepository: ArticleRepository,
    private val database: FirebaseDatabase
) : ViewModel()
```

## Home Category Grid

Sử dụng `Row` hoặc `LazyVerticalGrid`/`FlowRow` để hiển thị 4 nút chức năng chính yếu dạng icon trên grid:
```
Item 1: Workout  → Navigate to WorkoutScreen (từ Phase 02)
Item 2: Progress Tracking → Navigate to ProgressScreen (từ Phase 05)
Item 3: Nutrition → Navigate to NutritionScreen (từ Phase 05)
Item 4: Community → Navigate to CommunityScreen (từ Phase 05)
```

Click vào các item sẽ gọi lambda navEvent lên AppNavHost thay vì vuốt bằng HorizontalPager.

## Firebase Data Connections

```kotlin
// Trong HomeViewModel.init
viewModelScope.launch {
    // Load user data
    val uid = auth.currentUser?.uid ?: return@launch
    database.getReference("users/$uid").get().await().let { snap ->
        _state.update { it.copy(
            userName = snap.child("fullName").value as? String ?: "",
            weeklyWorkoutsCompleted = (snap.child("weeklyChallenge/current").value as? Long)?.toInt() ?: 0
        ) }
    }
}
```

## Bước triển khai

### Bước 1: Tạo `HomeViewModel.kt`
- Collect data từ Firebase + local repositories
- Expose `StateFlow<HomeState>`

### Bước 2: Tạo `HomeCategoryGrid.kt`
- Giao diện grid chứa 4 nút có hình icon và text (Workout, Progress Tracking, Nutrition, Community)
- Click handler trigger navigation callback.

### Bước 3: Refactor `HomeScreen.kt`
- Nhận `viewModel: HomeViewModel` qua `hiltViewModel()`
- `headerSection`: Greeting text ("Hi, userName", "It's time..."), đồng thời góc trên bên phải thêm cụm action icons: `Search`, `Notifications`, `Profile avatar`.
- Đưa `HomeCategoryGrid` vào ngay dưới Header.
- `RecommendedWorkoutsSection`, `WeeklyChallengeSection`, `ArticlesSection` cần styling giống với thiết kế (thêm tag time 12 Mins, 120 Kcal, nút See All).
- BỎ `GymFloatingMenu` khỏi HomeScreen vì đã dời các tính năng lên header.

### Bước 4: Kết nối `AppNavHost.kt`
- Truyền navigate lambdas từ AppNavHost vào HomeScreen. Cần bao gồm callbacks cho icon Header (Profile, Search, Notification) và Category Grid.
- Bottom Nav: `NavigationBar` Material3 4 items

## Todo List

- [ ] Tạo `HomeViewModel.kt` với full state
- [ ] Tạo `HomeCategoryGrid.kt`
- [ ] Refactor `HomeScreen.kt` kết nối viewModel, update Header
- [ ] Loại bỏ `GymFloatingMenu` ở trang Home (thay bằng icon)
- [ ] Kết nối Bottom Nav trong AppNavHost
- [ ] Xử lý navigate mượt mà giữa các mục Grid và Header Items
- [ ] Xử lý loading skeleton khi data đang load

## Success Criteria

- ✅ Header hiển thị đúng các icon và greeting
- ✅ Chuyển màn hình qua các danh mục thay vì vuốt Pager
- ✅ Weekly Challenge progress đồng bộ Firebase
- ✅ Styling các section chuẩn xác theo giao diện

## Risk Assessment

| Rủi ro | Mức độ | Giảm thiểu |
|--------|--------|------------|
| Cấu trúc navigation deep do từ bỏ Pager | Cao | Thống nhất AppNavHost, không lạm dụng back stack |
| Firebase data latency | Trung bình | Skeleton loading state |
| Conflicting state từ nhiều ViewModels | Trung bình | Single source of truth qua `HomeViewModel` |
