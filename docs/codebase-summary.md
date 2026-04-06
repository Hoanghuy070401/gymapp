# Tóm tắt cấu trúc Codebase (Codebase Summary)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

## Phân tích các Module

### Module `app` — Vỏ bọc ứng dụng (Application Shell)
| File | Chức năng (Role) |
|------|-----------------|
| `GymApplication.kt` | Entry point khởi chạy `@HiltAndroidApp` |
| `MainActivity.kt` | Single-Activity host; `enableEdgeToEdge()` + full-screen Compose |
| `AppNavHost.kt` | Navigation graph trung tâm; kiểm tra `isSetupCompleted` để route Login → Setup hay Home |
| `Screen.kt` | Sealed class định nghĩa tất cả route constants |
| `SplashScreen.kt` | Hiển thị 1.5s rồi navigate sang Onboarding |

**Key pattern:** `AppNavHost` đọc `isSetupCompleted` từ `AuthRepository` qua `LaunchedEffect` để quyết định route sau Login thành công.

---

### Module `core` — Design System & Base Classes
| File | Chức năng |
|------|-----------|
| `AppColors.kt` | ElectricLime `#C9FF4F`, TonalLavender, Surface `#0E0E0E`, SurfaceContainerHigh, PrimaryKinetic, OnSurface, OnSurfaceVariant, Error |
| `AppTypography.kt` | Material3 text styles (Display → Label) theo Android Roboto |
| `AppSpacing.kt` | ScreenHorizontal=20dp, Medium=12dp, Large=24dp, ExtraLarge=32dp |
| `AppShape.kt` | Small/Medium/Large corner radius |
| `GymScaffold.kt` | Root container; nền Surface đen, top padding 48dp, optional `scrollable` |
| `GymButton.kt` | GHOST (outline ElectricLime) + PRIMARY (fill ElectricLime) variants |
| `GymTextField.kt` | Input hỗ trợ error state, password visibility, keyboard types |
| `GymAvatar.kt` | Avatar tròn 120dp; gradient default hoặc user image với edit badge |
| `GymCard.kt` | Card component với tuỳ chọn `containerColor` |
| `StepProgressBar.kt` | Thanh tiến trình theo bước (dùng trong Setup Wizard) |
| `ProgressRing.kt` | Vòng tròn tiến trình |
| `StatCard.kt` | Thẻ hiển thị số liệu thống kê |
| `SectionHeader.kt` | Tiêu đề section với optional trailing content |
| `StateContent.kt` | Quản lý UI Loading/Error/Empty/Success states |
| `BaseViewModel.kt` | Base class chung cho ViewModel |
| `GymLogger.kt` | Logging wrapper (TAG-based, wraps Android Log) |

---

### Module `domain` — Business Logic
| File | Chức năng |
|------|-----------|
| `WorkoutSession.kt` | Data model: id, name, exercises, durationMinutes, calories |
| `Exercise.kt` | Data model: id, name, sets, reps, weightKg |
| `WorkoutRepository.kt` | Interface: `getWorkouts()`, `saveWorkout()`, `syncWorkouts()` |
| `GetWorkoutsUseCase.kt` | UC truy xuất danh sách workout |
| `SaveWorkoutUseCase.kt` | UC lưu một workout session |
| `SyncWorkoutsUseCase.kt` | UC đồng bộ lên cloud |
| `Resource.kt` | `sealed class Resource<T>`: Loading, Success(data), Error(message) |

---

### Module `data` — Data Implementation
| File | Chức năng |
|------|-----------|
| `GymDatabase.kt` | Room database singleton (`@Database`) |
| `WorkoutDao.kt` | DAO: `@Insert`, `@Query` cho WorkoutEntity |
| `WorkoutEntity.kt` | Room entity ánh xạ bảng workout_sessions |
| `WorkoutRepositoryImpl.kt` | Implements `WorkoutRepository`; hiện dùng Room local-only (Firebase sync là TODO) |
| `DataModule.kt` | Hilt `@Module` cung cấp `GymDatabase`, `WorkoutDao`, `WorkoutRepository` |

---

### Module `feature_auth` — Authentication & Setup
| File | Chức năng |
|------|-----------|
| `AuthRepository.kt` | **SSoT** cho Firebase Auth + RTDB. Trả `AuthResult` (Success/Error/PasswordResetSent). Bao gồm: loginWithEmail, registerWithEmail (+ saveUserToDatabase + sendEmailVerification), loginWithGoogleCredential, sendPasswordReset, saveUserProfile, markSetupCompleted, checkSetupCompleted, changeEmailVerification, reloadAndCheckVerified, signOut. `mapFirebaseError()` chuyển lỗi sang tiếng Việt. |
| `ProfileImageStorage.kt` | Copy avatar URI → internal storage (`profile_image.jpg`). `saveProfileImage(uri)` + `getSavedImagePath()` |
| `AuthModule.kt` | Hilt module cung cấp `AuthRepository`, `ProfileImageStorage` |
| `AuthViewModel.kt` | Manages `AuthUiState` cho Login/SignUp/Google Sign-In. Shared qua NavBackStackEntry của parent |
| `FormValidator.kt` | Client-side validation: `validateEmail()`, `validatePassword()`, `validateFullName()` → trả `String?` error |
| `LoginScreen.kt` | UI Login: email/password fields, Google Sign-In, "touched" validation pattern, `LaunchedEffect` cho navigation |
| `SignUpScreen.kt` | UI SignUp: fullName/email/password/confirm, real-time validation |
| `EmailVerificationScreen.kt` | Chờ user verify email; auto-check mỗi 3s qua `reloadAndCheckVerified()`; nút resend |
| `ForgotPasswordScreen.kt` | Gửi password reset email |
| `SetPasswordScreen.kt` | UI đặt mật khẩu mới |
| `SetFingerprintScreen.kt` | UI biometric setup (UI only, BiometricPrompt chưa wired) |
| `SetupScreen.kt` | Container 8-bước Setup Wizard; dùng `StepProgressBar` + loading/error states |
| `SetupSteps.kt` | Composables từng bước: IntroStep, GenderStep, AgeStep, WeightStep, HeightStep, GoalStep, ActivityLevelStep, FillProfileStep |
| `SetupViewModel.kt` | Quản lý `SetupState`; `syncToFirebaseAndComplete()` gọi `saveUserProfile()` + `markSetupCompleted()` → navigate Home |
| `SetupStepsPreviews.kt` / `ScreenPreviews.kt` | `@Preview` composables cho development |

**SetupViewModel data flow:**
```
FillProfileStep (Step 8 - "Start" button)
  → SetupViewModel.syncToFirebaseAndComplete()
    → AuthRepository.saveUserProfile(uid, profileMap)
    → AuthRepository.markSetupCompleted(uid)
    → onSuccess() → navigate Screen.Home
```

---

### Module `feature_onboarding`
| File | Chức năng |
|------|-----------|
| `OnboardingScreen.kt` | 3-slide HorizontalPager với full-screen background photos, gradient overlay, ElectricLime page indicator dots, manual/auto swipe |

---

### Module `feature_home` — Home Dashboard
| File | Chức năng |
|------|-----------|
| `HomeScreen.kt` | Dashboard chính: Header (greeting + Search/Notifications/Profile icons), `HomeCategoryGrid`, `RecommendedSection` (horizontal scroll), `WeeklyChallengeSection` (GymCard), `ArticlesSection` (horizontal scroll) |
| `HomeViewModel.kt` | Quản lý `HomeState` (isLoading, userName, recommendedRoutines, recentArticles, activeChallenge, error). Hiện gọi Firebase trực tiếp — ⚠️ cần migrate sang Repository pattern |
| `HomeCategoryGrid.kt` | 2×2 grid với 4 nút: Workout, Progress Tracking, Nutrition, Community |

**Data models trong HomeViewModel:**
- `RecommendedRoutine(id, title, durationMinutes, calories, difficulty)`
- `WeeklyChallenge(id, title, description, current, target)`
- `ArticleTip(id, title, category)`

---

### Module `feature_workout`
| File | Chức năng |
|------|-----------|
| `WorkoutScreen.kt` | UI list/detail bài tập (scaffold sẵn, nội dung đang phát triển) |
| `WorkoutViewModel.kt` | Quản lý state workout (MVI pattern với WorkoutContract) |
| `WorkoutContract.kt` | MVI: `WorkoutEvent`, `WorkoutEffect`, `WorkoutState` |

---

## Dependency Graph (Dependencies)

```
app ──────────────────────────────────────────────────────┐
 ├── :core          (design system, base)                  │
 ├── :domain        (models, use cases, repo interfaces)   │
 ├── :data          (Room impl, depends on :domain)        │
 ├── :feature_auth  (depends on :core, :domain)            │
 ├── :feature_home  (depends on :core, :domain)            │
 ├── :feature_onboarding (depends on :core)                │
 └── :feature_workout (depends on :core, :domain)          │
```

> ⚠️ Features KHÔNG phụ thuộc nhau — chỉ connect qua `:core` và `:domain`

---

## Thư viện chính (Key Dependencies)

```toml
kotlin = "1.9.22"
compose = "1.6.0"
hilt = "2.51.1"
lifecycle = "2.7.0"
firebase-bom = "33.1.2"
credentials = "1.3.0"        # Google Credential Manager
googleid = "1.1.1"
play-services-auth = "21.2.0"
room = "2.6.1"
navigation-compose = "2.7.7"
coroutines = "1.7.3"
```

---

## Các vấn đề kỹ thuật còn tồn đọng (Tech Debt)

| Issue | Mức ưu tiên | Ghi chú |
|-------|-------------|---------|
| `HomeViewModel` gọi Firebase trực tiếp, chưa dùng Repository | High | Vi phạm Clean Architecture |
| `SetFingerprintScreen.kt` — UI only, BiometricPrompt chưa wired | Medium | Cần integrate `BiometricPrompt` API |
| Social auth buttons (Facebook icon) — UI only, VM not wired | Medium | Chưa tích hợp Facebook SDK |
| `WorkoutRepositoryImpl` — local-only, Firebase sync chưa có | Medium | `SyncWorkoutsUseCase` là TODO |
| `EmailVerificationScreen` — resend cooldown chưa có | Low | UX improvement |
