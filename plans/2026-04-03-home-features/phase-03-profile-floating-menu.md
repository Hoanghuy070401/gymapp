# Phase 03 — Profile & Floating Menu

**Plan:** [plan.md](plan.md)
**Parallelization:** ✅ Chạy song song với Phase 02, 04, 05 (sau Phase 01)
**Priority:** High
**Status:** Todo

## Tổng quan

Triển khai Floating Action Menu (6.) với các chức năng Profile (6.1) và Notifications (6.2). Tận dụng `AuthRepository` và `ProfileImageStorage` đã có ở `feature_auth`.

## Tính năng cần triển khai

**6.1 Profile:**
- 6.1.1 Edit Profile — chỉnh họ tên, ảnh đại diện, goal
- 6.1.2 Favorites — xem danh sách mục yêu thích (delegate về feature_home Phase 04)
- 6.1.3 Notification — cài đặt thông báo
- 6.1.4 Setting — cài đặt app
- 6.1.5 Help — liên kết đến Support
- 6.1.6 Log Out — xác nhận + clear auth state

**6.2 Notifications:**
- 6.2.1 Workout Reminders — bật/tắt, đặt giờ nhắc
- 6.2.2 System Notifications — cài đặt hệ thống

**Floating Action Button (FAB):** icon menu, expand animation khi tap

## Kiến trúc

```
feature_auth/presentation/profile/
├── ProfileScreen.kt              ← NEW
├── ProfileViewModel.kt           ← NEW
├── EditProfileScreen.kt          ← NEW
└── NotificationSettingsScreen.kt ← NEW

core/designsystem/component/
└── FloatingMenu.kt               ← NEW (GymFloatingMenu composable)
```

## File Ownership

- `feature_auth/src/main/java/com/gym/feature/auth/presentation/profile/*` ← NEW (toàn bộ)
- `core/src/main/java/com/gym/core/designsystem/component/FloatingMenu.kt` ← NEW

## Kiến trúc Floating Menu

```
GymFloatingMenu (composable, đặt trong HomeScreen)
├── State: isExpanded: Boolean
├── AnimatedVisibility → menu items (Profile, Notifications, Search)
└── FAB trigger (ElectricLime circle, "+" icon)
```

Animation: `expandVertically` + `fadeIn` từ góc dưới phải.

## ProfileViewModel

```kotlin
data class ProfileState(
    val isLoading: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val avatarPath: String? = null,
    val goal: FitnessGoal = FitnessGoal.LOSE_WEIGHT,
    val error: String? = null
)

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileImageStorage: ProfileImageStorage
) : ViewModel()
```

## Firebase Realtime Database Update

Thêm node vào `users/{uid}`:
```
users/{uid}/
├── ...existing...
├── workoutReminderEnabled: Boolean
├── workoutReminderTime: String  ("07:00")
└── systemNotificationsEnabled: Boolean
```

## Các bước triển khai

### Bước 1: `FloatingMenu.kt` (core)
- FAB CircleButton ElectricLime
- Expanded state: 3 menu items chạy từ dưới lên
- Tap ngoài vùng → collapse
- Sử dụng `AnimatedVisibility` + `spring` animation

### Bước 2: `ProfileScreen.kt`
- Header với avatar lớn (120dp) + tên user + email
- List menu items theo thiết kế
- "Log Out" button → `AlertDialog` xác nhận → `authRepository.signOut()` → navigate Login

### Bước 3: `EditProfileScreen.kt`
- GymTextField cho name
- Avatar picker (tái dụng `ProfileImageStorage`)
- Goal selector chips
- Save → update Firebase RTDB

### Bước 4: `NotificationSettingsScreen.kt`
- Switch toggle cho Workout Reminders
- Time picker khi bật reminder
- Switch cho System Notifications

## Todo List

- [ ] Tạo `FloatingMenu.kt` trong core
- [ ] Tạo `ProfileScreen.kt`
- [ ] Tạo `ProfileViewModel.kt`
- [ ] Tạo `EditProfileScreen.kt`
- [ ] Tạo `NotificationSettingsScreen.kt`
- [ ] Thêm notification fields vào Firebase schema
- [ ] Tích hợp Log Out flow với auth state clear

## Success Criteria

- ✅ FAB expand/collapse animation mượt
- ✅ Edit Profile lưu được lên Firebase RTDB
- ✅ Log Out → navigate về Login, clear back-stack
- ✅ Notification toggles persist qua app restart

## Conflict Prevention

Phase này KHÔNG sửa `Screen.kt`, `AppNavHost.kt`, `SetupSteps.kt`, `SetupViewModel.kt`. Chỉ tạo file MỚI trong `feature_auth/presentation/profile/` và `core/designsystem/component/FloatingMenu.kt`.
