# Báo cáo Code Review — gym_project Android

**Ngày review:** 2026-04-04  
**Reviewer:** Antigravity AI  
**Plan liên quan:** post-signup-navigation (IN_PROGRESS)

---

## Phạm vi (Scope)

| File được review | Số dòng |
|---|---|
| `feature_home/HomeScreen.kt` | 366 |
| `feature_home/HomeViewModel.kt` | 125 |
| `feature_home/categories/HomeCategoryGrid.kt` | 109 |
| `feature_auth/AuthViewModel.kt` | 139 |
| `feature_auth/data/AuthRepository.kt` | 184 |
| `feature_auth/setup/SetupViewModel.kt` | 108 |
| `feature_workout/WorkoutViewModel.kt` | 73 |
| `app/navigation/AppNavHost.kt` | 158 |
| `app/navigation/Screen.kt` | 15 |
| `feature_auth/login/LoginScreen.kt` | 248 |

**Tổng cộng:** ~1.525 dòng được phân tích

---

## Đánh giá tổng quan

Codebase có nền tảng kiến trúc tốt — áp dụng đúng Clean Architecture (UI → ViewModel → Repository → Firebase/Room), dùng StateFlow đúng chuẩn, và hệ design system (`AppColors`, `AppSpacing`, `AppTypography`, `AppShape`) được áp dụng nhất quán ở layer Auth. Tuy nhiên, `HomeViewModel` và `HomeScreen` còn một số vi phạm chuẩn cần chú ý.

---

## Vấn đề Critical 🔴

### 1. `HomeViewModel` gọi Firebase trực tiếp — vi phạm kiến trúc

**File:** `HomeViewModel.kt` dòng 57–58  
**Vấn đề:** `HomeViewModel` tự khởi tạo `FirebaseAuth.getInstance()` và `FirebaseDatabase.getInstance()` thay vì thông qua Repository.

```kotlin
// ❌ Vi phạm — ViewModel gọi Firebase trực tiếp
private val auth = FirebaseAuth.getInstance()
private val database = FirebaseDatabase.getInstance()
```

**Kiến trúc đúng:** `HomeViewModel` phải nhận `AuthRepository` (hoặc `HomeRepository`) qua Hilt DI injection, không được tự khởi tạo Firebase SDK.

**Mức độ:** CRITICAL — vi phạm quy tắc kiến trúc được ghi rõ trong `system-architecture.md` và `code-standards.md` (layer boundary).

---

### 2. Domain models đặt sai vị trí trong `HomeViewModel.kt`

**File:** `HomeViewModel.kt` dòng 18–38  
**Vấn đề:** `RecommendedRoutine`, `ArticleTip`, `WeeklyChallenge` được khai báo trong file ViewModel — nhưng đây là domain models, phải nằm trong module `:domain`.

```kotlin
// ❌ Domain model nằm trong ViewModel file — sai kiến trúc
data class RecommendedRoutine(val id: String, val title: String, ...)
data class ArticleTip(val id: String, val title: String, ...)
data class WeeklyChallenge(val title: String, ...)
```

**Mức độ:** CRITICAL — vi phạm module boundary. Nếu màn hình khác cần dùng các model này, sẽ bị phụ thuộc chéo giữa feature modules.

**Ghi chú:** Comment `// local to home feature, YAGNI` trong code là lý do hợp lý ở giai đoạn đầu, nhưng cần có ticket để di chuyển lên `:domain` trước Phase 06.

---

## Vấn đề High Priority 🟠

### 3. Hardcode màu hex trong `HomeScreen.kt` và `HomeCategoryGrid.kt`

**Vi phạm `code-standards.md`:** "Yêu cầu KHÔNG hardcode màu hex ngẫu nhiên — thay vì đó trỏ về chuẩn `AppColors.*`"

Các màu hardcode tìm thấy:
```kotlin
// HomeScreen.kt — RecommendedCard (dòng 203–204)
listOf(Color(0xFF1B5E20), Color(0xFF4CAF50)),  // ❌ Green gradient
listOf(Color(0xFF4A148C), Color(0xFF7B1FA2))   // ❌ Purple gradient

// HomeScreen.kt — ArticleCard (dòng 338)
val bgColors = listOf(Color(0xFF3E2723), Color(0xFF1A237E))  // ❌

// HomeCategoryGrid.kt (dòng 36–37)
CategoryItem("Nutrition", ..., Color(0xFFFF8A65)),  // ❌ Orange
CategoryItem("Community", ..., Color(0xFF4FC3F7))   // ❌ Light blue
```

**Giải pháp đề xuất:** Thêm token màu vào `AppColors.kt`:
```kotlin
val GradientGreenDark  = Color(0xFF1B5E20)
val GradientGreenLight = Color(0xFF4CAF50)
val GradientPurpleDark = Color(0xFF4A148C)
val GradientPurpleLight = Color(0xFF7B1FA2)
val AccentOrange       = Color(0xFFFF8A65)  // Nutrition
val AccentBlue         = Color(0xFF4FC3F7)  // Community
```

---

### 4. Hardcode giá trị `dp` trong HomeScreen thay vì dùng `AppSpacing`

**Vi phạm `code-standards.md`:** "Yêu cầu KHÔNG hardcode tham số kích thước dp/sp"

```kotlin
Spacer(modifier = Modifier.height(100.dp))   // dòng 105
Arrangement.spacedBy(4.dp)                    // dòng 141
Modifier.size(36.dp)                          // dòng 159
Modifier.width(160.dp).height(180.dp)         // dòng 210–211
RoundedCornerShape(12.dp)                     // nên dùng AppShape.Medium
```

---

### 5. Routing Login → luôn đến Setup, không kiểm tra `isSetupCompleted`

**File:** `AppNavHost.kt` dòng 57–59
```kotlin
// ❌ Luôn đi Setup, bất kể đã done hay chưa
onLoginSuccess = {
    navController.navigate(Screen.Setup.route) { ... }
}
```

Plan `post-signup-navigation` đã có Phase 02 (Smart Splash routing), nhưng vấn đề này cũng cần fix tại Login flow.

---

### 6. `SetupViewModel` gọi `FirebaseAuth.getInstance()` trực tiếp

**File:** `SetupViewModel.kt` dòng 70
```kotlin
val uid = FirebaseAuth.getInstance().currentUser?.uid  // ❌
// Nên dùng:
val uid = repository.currentUser?.uid  // ✅
```

---

## Vấn đề Medium Priority 🟡

### 7. `onSeeAll` callbacks bị bỏ trống `{}`

```kotlin
RecommendedSection(routines = ..., onSeeAll = {})  // dòng 85
ArticlesSection(articles = ..., onSeeAll = {})      // dòng 100
```
Người dùng bấm "See All ▷" mà không có phản hồi — cần thêm TODO hoặc disable nút.

---

### 8. Index-based callback trong `HomeCategoryGrid` dễ lỗi

```kotlin
val callbacks = listOf(onWorkoutClick, onProgressClick, onNutritionClick, onCommunityClick)
categories.forEachIndexed { index, item -> CategoryButton(..., onClick = callbacks[index]) }
```
Thứ tự phải giữ đồng bộ thủ công — dễ bug nếu thêm category. Nên gắn callback vào `CategoryItem`.

---

### 9. Data mock trong `HomeViewModel` không được đánh dấu rõ ràng

Static routines/articles không có cờ feature flag hay `@Deprecated` — dễ quên replace khi Phase 06 kết nối dữ liệu thật.

---

## Vấn đề Low Priority 🟢

### 10. `GOOGLE_WEB_CLIENT_ID` hardcode trong `LoginScreen.kt`

Nên đặt trong `BuildConfig` hoặc `strings.xml` để dễ quản lý theo môi trường (dev/staging/prod).

### 11. UI string tiếng Anh/tiếng Việt lẫn lộn

`HomeScreen.kt` dùng tiếng Anh, `LoginScreen.kt` vừa Anh vừa Việt — nên thống nhất hoặc dùng `strings.xml`.

---

## Điểm tốt ✅

| Điểm tích cực | Chi tiết |
|---|---|
| **AuthRepository error mapping** | Cover nhiều Firebase error code, message thân thiện tiếng Việt |
| **AuthViewModel layer boundary** | Không import Compose, không gọi Firebase trực tiếp |
| **"Touched pattern" đúng chuẩn** | Validation chỉ hiện lỗi sau khi user touch field |
| **StateFlow nhất quán** | Tất cả ViewModel dùng `StateFlow<UiState>` + `collectAsState()` |
| **Navigation routes tập trung** | `Screen.kt` là single source of truth |
| **popUpTo đúng chỗ** | Splash/Onboarding/Login clear backstack đúng cách |
| **WorkoutViewModel MVI pattern** | `WorkoutContract` + `BaseViewModel<State, Event, Effect>` — testable |
| **Design system nhất quán ở Auth** | `LoginScreen` dùng `AppColors.*`, `AppSpacing.*` đúng chuẩn |
| **Google Sign-In cancellation** | `GetCredentialCancellationException` không hiện lỗi — UX đúng |

---

## Đề xuất hành động ưu tiên

**Làm trước Phase 06:**
1. 🔴 Tạo `HomeRepository` + inject qua Hilt thay vì gọi Firebase trực tiếp trong `HomeViewModel`
2. 🔴 Di chuyển `RecommendedRoutine`, `ArticleTip`, `WeeklyChallenge` sang module `:domain`
3. 🟠 Thêm màu token vào `AppColors.kt` — xóa hardcode hex trong Home feature
4. 🟠 Fix routing Login → kiểm tra `isSetupCompleted` trước khi đến Setup
5. 🟠 Thay `FirebaseAuth.getInstance()` trong `SetupViewModel` bằng `repository.currentUser?.uid`

**Backlog:**
6. 🟡 Disable / thêm TODO cho nút "See All" đang không hoạt động
7. 🟢 Move `GOOGLE_WEB_CLIENT_ID` vào `BuildConfig`
8. 🟢 Thống nhất ngôn ngữ UI hoặc dùng `strings.xml`
9. 🟢 Refactor `HomeCategoryGrid` gắn callback vào `CategoryItem` data class
