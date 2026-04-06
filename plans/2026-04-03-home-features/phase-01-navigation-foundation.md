# Phase 01 — Navigation Foundation & Bottom Navigation Bar

**Plan:** [plan.md](plan.md)
**Parallelization:** ⚠️ Phải hoàn thành TRƯỚC Phase 02–05
**Priority:** Critical
**Status:** Todo

## Tổng quan

Thiết lập nền tảng điều hướng cho toàn bộ Home: thêm routes mới vào `Screen.kt`, tích hợp `BottomNavigationBar` vào `MainActivity`, và cập nhật `AppNavHost` để hỗ trợ Bottom Nav graph.

## Yêu cầu (Requirements)

- Bottom Nav Bar với 4 tab: **Home | Resources | Favorite | Support**
- Bottom Nav tích hợp seamless với Jetpack Navigation Compose
- Mỗi tab có nested navigation graph riêng
- Bottom Nav ẩn khi ở màn hình Auth/Onboarding/Setup
- Tab icon sử dụng Material Icons Extended

## Kiến trúc (Architecture)

```
MainActivity
└── Scaffold
    ├── content → AppNavHost (NavHostController)
    └── bottomBar → BottomNavigationBar (ẩn khi route auth)

Screen.kt (thêm routes)
├── Screen.Home
├── Screen.Resources
├── Screen.Favorite
├── Screen.SupportHelp
│   ├── Screen.HelpCenter
│   └── Screen.OnlineSupport
├── Screen.WorkoutTab (nested trong Home)
├── Screen.ProgressTab
├── Screen.NutritionTab
└── Screen.CommunityTab
```

## File Ownership (Phase 01 sở hữu độc quyền)

- `app/src/main/java/com/gym/app/navigation/Screen.kt`
- `app/src/main/java/com/gym/app/navigation/AppNavHost.kt`
- `app/src/main/java/com/gym/app/MainActivity.kt`
- `app/src/main/java/com/gym/app/navigation/BottomNavigationBar.kt` ← **NEW**

## Các bước triển khai (Implementation Steps)

### Bước 1: Cập nhật `Screen.kt`
Thêm các routes mới:
```kotlin
data object Resources : Screen("resources")
data object Favorite : Screen("favorite")
data object SupportHelp : Screen("support_help")
data object HelpCenter : Screen("help_center")
data object OnlineSupport : Screen("online_support")
// Top menu tabs (nested)
data object WorkoutTab : Screen("workout_tab")
data object ProgressTab : Screen("progress_tab")
data object NutritionTab : Screen("nutrition_tab")
data object CommunityTab : Screen("community_tab")
// Sub-screens
data object PresetRoutines : Screen("preset_routines")
data object CustomRoutine : Screen("custom_routine")
data object WorkoutLog : Screen("workout_log")
data object ProgressCharts : Screen("progress_charts")
data object MealPlans : Screen("meal_plans")
data object MealIdeas : Screen("meal_ideas")
data object Forum : Screen("forum")
data object Challenges : Screen("challenges")
data object EditProfile : Screen("edit_profile")
data object NotificationSettings : Screen("notification_settings")
data object SearchScreen : Screen("search")
data object WorkoutVideos : Screen("workout_videos")
data object ArticleDetail : Screen("article_detail/{articleId}") {
    fun createRoute(id: String) = "article_detail/$id"
}
```

### Bước 2: Tạo `BottomNavigationBar.kt`
- 4 tab items với icon và label
- Highlight tab active bằng `ElectricLime`
- Tab inactive dùng `OnSurfaceVariant`
- Background: `SurfaceContainerHigh`

### Bước 3: Cập nhật `MainActivity.kt`
- Bọc bằng `Scaffold` với `bottomBar`
- Logic ẩn bottom bar khi `currentRoute` nằm trong danh sách auth routes

### Bước 4: Cập nhật `AppNavHost.kt`
- Thêm composable destinations cho tất cả routes mới (placeholder composables)
- Chuẩn bị sẵn lambdas để Phase 02–05 truyền content vào

## Todo List

- [ ] Cập nhật `Screen.kt` với đầy đủ routes
- [ ] Tạo `BottomNavigationBar.kt`
- [ ] Cập nhật `MainActivity.kt` dùng Scaffold
- [ ] Cập nhật `AppNavHost.kt` thêm destinations

## Success Criteria

- ✅ Bottom Nav hiển thị đúng 4 tab
- ✅ Chuyển tab không reset back-stack
- ✅ Bottom Nav ẩn ở màn hình Login/Onboarding/Setup
- ✅ Build không lỗi

## Conflict Prevention

Phase này SỞ HỮU ĐỘC QUYỀN `Screen.kt` và `AppNavHost.kt`. Các phase 02–05 chỉ tạo file mới trong module của mình, KHÔNG ĐƯỢC sửa các file này.

## Unresolved Questions

- Bottom Nav icon set: dùng Material Icons hay custom SVG?
- Tab "Favorite" scope: chỉ videos/articles hay cả workouts?
