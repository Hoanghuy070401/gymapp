# Tiêu chuẩn viết Code (Code Standards)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

---

## Nguyên tắc chung (Principles)

**YAGNI — KISS — DRY**: Không implement tính năng chưa cần. Giữ code đơn giản. Không lặp code.

---

## Quy ước đặt tên (Naming Conventions)

| Thành phần | Convention | Ví dụ |
|-----------|-----------|-------|
| Class / Object | PascalCase | `AuthRepository`, `FormValidator` |
| Function / Method | camelCase | `validateEmail()`, `saveProfileImage()` |
| Composable function | PascalCase | `LoginScreen()`, `FillProfileStep()` |
| File | PascalCase (.kt) | `LoginScreen.kt`, `AuthRepository.kt` |
| Package | lowercase | `com.gym.feature.auth` |
| Constant | UPPER_CASE | `GOOGLE_WEB_CLIENT_ID`, `TAG` |
| StateFlow variable | `_state` (private), `state` (public) | `_state`, `state` |

---

## Giới hạn kích thước file (File Size Limits)

| Unit | Limit |
|------|-------|
| 1 function/method | ≤ 50 lines |
| 1 class | ≤ 300 lines |
| 1 file | ≤ 500 lines |
| 1 ViewModel | ≤ 5 use cases |

> Nếu vượt giới hạn → split thành file nhỏ hơn, extract utility functions, dùng composition.

---

## Kiến trúc (Architecture Rules)

### Layer boundaries
```
UI (Screen) → ViewModel → Repository → Firebase/Room
```
- Screen KHÔNG trực tiếp gọi Repository.
- ViewModel KHÔNG import Compose/UI classes.
- Feature modules KHÔNG phụ thuộc nhau.
- `HomeViewModel` cần migrate từ direct Firebase call → Repository pattern.

### State Management
```kotlin
// ✅ Dùng StateFlow<UiState> trong ViewModel
private val _state = MutableStateFlow(HomeState())
val state: StateFlow<HomeState> = _state.asStateFlow()

// ✅ UiState là data class với các field chuẩn
data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userName: String = ""
)

// ✅ Collect tại Screen
val uiState by viewModel.state.collectAsState()
```

### Navigation
```kotlin
// ✅ Tất cả routes định nghĩa trong Screen.kt
sealed class Screen(val route: String) {
    object Home : Screen("home")
}

// ✅ Dùng popUpTo để clear back-stack
navController.navigate(Screen.Home.route) {
    popUpTo(Screen.Login.route) { inclusive = true }
}

// ✅ Truyền lambda callback, không truyền NavController vào feature screens
LoginScreen(onLoginSuccess = { navController.navigate(...) })
```

---

## Compose Rules

### ✅ LÀM (Do)
```kotlin
// ✅ Stateless composables với callbacks
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
)

// ✅ "Touched pattern" cho form validation
var emailTouched by remember { mutableStateOf(false) }
val emailError = if (emailTouched) FormValidator.validateEmail(email) else null

// ✅ LaunchedEffect cho one-time side effects
LaunchedEffect(uiState.successUser) {
    if (uiState.successUser != null) onLoginSuccess()
}

// ✅ Dùng AppColors, AppSpacing, AppTypography
Text(text = "Hello", color = AppColors.ElectricLime, style = AppTypography.headlineLarge)
Spacer(modifier = Modifier.height(AppSpacing.Large))
```

### ❌ KHÔNG LÀM (Don't)
```kotlin
// ❌ Business logic trong Composables
// ❌ Hardcode hex colors → dùng AppColors.*
// ❌ Hardcode dp/sp sizes → dùng AppSpacing.*
// ❌ Truyền NavController vào feature screens
// ❌ Import Compose trong ViewModel
```

---

## Error Handling

### Repository layer
```kotlin
// ✅ Trả sealed class AuthResult
sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
    data object PasswordResetSent : AuthResult()
}

// ✅ Map Firebase errors → tiếng Việt
private fun mapFirebaseError(e: Exception): String { ... }
```

### ViewModel layer
```kotlin
// ✅ Cập nhật UiState error field
catch (e: Exception) {
    _state.update { it.copy(isLoading = false, error = "Lỗi thân thiện tiếng Việt") }
}
```

### Screen layer
```kotlin
// ✅ Hiển thị error từ UiState
uiState.error?.let { error ->
    Text(text = error, color = AppColors.Error, style = AppTypography.labelMedium)
}
```

---

## Firebase Security

- Validate tất cả inputs client-side TRƯỚC KHI gọi Firebase API.
- Error codes Firebase → map sang tiếng Việt qua `AuthRepository.mapFirebaseError()`.
- RTDB paths: `users/{uid}` với rules `auth.uid == $uid`.
- Không log sensitive data (passwords, tokens) — `GymLogger` safe với user data.

---

## Dependency Management

- Tất cả versions quản lý trong `libs.versions.toml` (Version Catalog).
- Feature modules dùng `implementation(platform(libs.firebase.bom))` cho Firebase.
- KHÔNG hardcode version strings trong `build.gradle.kts`.

---

## Logging

```kotlin
// Dùng GymLogger (wrapper của Android Log)
GymLogger.d(TAG, "loginWithEmail: $email")    // Debug
GymLogger.i(TAG, "login success uid=$uid")     // Info
GymLogger.w(TAG, "blank credentials")          // Warning
GymLogger.e(TAG, e, "login failed for $email") // Error

// TAG là companion object constant
companion object { private const val TAG = "AuthRepository" }
```

---

## Checklist trước PR

- [ ] Firebase raw error messages KHÔNG bắn ra UI cho user.
- [ ] Tất cả inputs validate qua `FormValidator` trước network calls.
- [ ] Screens dùng `GymScaffold` làm root container.
- [ ] Colors dùng `AppColors.*`, sizes dùng `AppSpacing.*`.
- [ ] Routes định nghĩa trong `Screen.kt`.
- [ ] Không commit `google-services.json` nếu có credential thật hoặc `.env`.
- [ ] Run lint trước commit.

---

## Quản lý tài liệu & kế hoạch

- **`docs/`**: Single Source of Truth cho PDR, architecture, code standards, roadmap. Update khi có thay đổi cấu trúc lớn.
- **`plans/`**: Feature plans dạng markdown (VD: `plans/2026-04-03-home-features/plan.md`). AI agents đọc để làm việc theo tiến độ.
