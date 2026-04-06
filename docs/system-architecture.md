# Kiến trúc hệ thống (System Architecture)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

## Cấu trúc Module (Module Structure)

```
gym_project/
├── app/              # Entry point, Navigation graph, DI setup
├── core/             # Design system, base classes, shared utilities
├── domain/           # Business logic: models, use cases, repo interfaces
├── data/             # Data sources: Room + Firebase implementations
├── feature_auth/     # Authentication + Setup Wizard (8 bước)
├── feature_onboarding/ # Onboarding slide screens
├── feature_home/     # Home Dashboard
└── feature_workout/  # Workout tracking feature
```

## Sơ đồ phụ thuộc (Dependency Graph)

```
app
 ├── :core
 ├── :domain
 ├── :data          → depends on :domain
 ├── :feature_auth  → depends on :core, :domain
 ├── :feature_home  → depends on :core, :domain
 ├── :feature_onboarding → depends on :core
 └── :feature_workout → depends on :core, :domain
```

> ⚠️ Feature modules TUYỆT ĐỐI không phụ thuộc nhau, chỉ kết nối qua `:core` và `:domain`.

---

## Kiến trúc điều hướng (Navigation Architecture)

```
AppNavHost (NavHostController)
├── Screen.Splash       → SplashScreen (1.5s → Onboarding)
├── Screen.Onboarding   → OnboardingScreen (3 slides)
├── Screen.Login        → LoginScreen (shared AuthViewModel)
├── Screen.SignUp       → SignUpScreen (shared AuthViewModel)
├── Screen.EmailVerification → EmailVerificationScreen
├── Screen.ForgotPassword → ForgotPasswordScreen
├── Screen.SetPassword  → SetPasswordScreen
├── Screen.SetFingerprint → SetFingerprintScreen
├── Screen.Setup        → SetupScreen (8-bước wizard)
└── Screen.Home         → HomeScreen
```

**Back-stack policy:**
- Dùng `popUpTo(...) { inclusive = true }` tại các transition points để loại bỏ back-stack về các màn hình đã hoàn thành (onboarding, login sau khi xác thực xong).

**Smart routing tại Login:**
```kotlin
// AppNavHost sau khi Login/SignUp thành công:
val setupDone = repository.checkSetupCompleted(uid)
if (setupDone) navigate(Screen.Home) else navigate(Screen.Setup)
```

---

## Luồng xác thực hoàn chỉnh (Complete Auth Flow)

```
LoginScreen / SignUpScreen
    └── AuthViewModel (Hilt, shared via NavBackStackEntry)
         └── AuthRepository (SSoT)
              ├── FirebaseAuth.getInstance()
              └── FirebaseDatabase.getInstance()

Luồng đăng ký mới:
  registerWithEmail()
    → createUserWithEmailAndPassword()
    → saveUserToDatabase(uid, name, email)  ← sets isSetupCompleted=false
    → sendEmailVerification()               ← best-effort
    → AuthResult.Success(user)
    → navigate EmailVerificationScreen

Luồng đăng nhập cũ:
  loginWithEmail() / loginWithGoogleCredential()
    → AuthResult.Success(user)
    → checkSetupCompleted(uid)
    → isSetupCompleted=true  → Home
    → isSetupCompleted=false → Setup
```

---

## Firebase Architecture

### Firebase Auth
- Email/Password authentication
- Google Sign-In qua Credential Manager API (`GoogleAuthProvider.getCredential`)
- Email verification (`sendEmailVerification`, `verifyBeforeUpdateEmail`)
- Password reset (`sendPasswordResetEmail`)

### Firebase Realtime Database Schema

```
users/
  {uid}/
    fullName:          String    ← set lúc register
    email:             String    ← set lúc register
    createdAt:         Long      ← timestamp milliseconds
    isSetupCompleted:  Boolean   ← false → true sau Setup Wizard
    gender:            String?   ← từ SetupStep 1
    age:               Int?      ← từ SetupStep 2
    weightKg:          Double?   ← từ SetupStep 3
    heightCm:          Int?      ← từ SetupStep 4
    goal:              String?   ← từ SetupStep 5
    activityLevel:     String?   ← từ SetupStep 6
    nickname:          String?   ← từ SetupStep 8 (FillProfile)
    dateOfBirth:       String?   ← DD/MM/YYYY
    mobileNumber:      String?
    avatarPath:        String?   ← internal storage path
```

**Security Rules:**
```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "auth != null && auth.uid == $uid",
        ".write": "auth != null"
      }
    }
  }
}
```

---

## Local Data Architecture (Room)

```
GymDatabase (Room)
  └── WorkoutDao
       └── WorkoutEntity (table: workout_sessions)

WorkoutRepositoryImpl : implements WorkoutRepository (domain)
  ├── WorkoutDao (local source)
  └── [TODO: Firebase sync via SyncWorkoutsUseCase]
```

**Note:** Local-first strategy — Room là primary source, cloud backup là future phase.

---

## Kiến trúc MVVM (MVVM Pattern)

```
UI Layer (Screens)
  ↓ collectAsState()
ViewModel Layer (StateFlow<UiState>)
  ↓ suspend fun / coroutinesScope
Repository Layer (AuthRepository, WorkoutRepositoryImpl)
  ↓ Firebase SDK / Room DAO
Data Sources (Firebase Auth, RTDB, Room)
```

**State management:**
- `StateFlow<UiState>` trong ViewModel (không dùng LiveData)
- UiState là `data class` với fields: `isLoading`, `error`, `data`
- Screens collect qua `collectAsState()`

---

## Design System (core/designsystem)

| Token | File | Value |
|-------|------|-------|
| Primary accent | `AppColors.ElectricLime` | `#C9FF4F` |
| Background | `AppColors.Surface` | `#0E0E0E` |
| Secondary | `AppColors.TonalLavender` | (purple tone) |
| Card bg | `AppColors.SurfaceContainerHigh` | (dark grey) |
| Error | `AppColors.Error` | (red tone) |
| Screen edge | `AppSpacing.ScreenHorizontal` | 20dp |
| Section gap | `AppSpacing.Large` | 24dp |
| Component gap | `AppSpacing.Medium` | 12dp |

### Shared Components
- `GymScaffold` — Root container (dark bg, 48dp top pad, optional scroll)
- `GymButton` — GHOST (outline) + PRIMARY (fill) variants
- `GymTextField` — Input với error state + password masking
- `GymAvatar` — Avatar tròn 120dp + gradient + edit badge
- `GymCard` — Card với customizable container color
- `SectionHeader` — Tiêu đề section + optional trailing
- `StepProgressBar` — Step indicator cho Setup Wizard
- `StateContent` — Loading/Error/Empty/Success UI states

---

## Hilt DI Setup

```
GymApplication (@HiltAndroidApp)
  └── MainActivity (@AndroidEntryPoint)
       └── AppNavHost
            ├── hiltViewModel() → AuthViewModel
            ├── hiltViewModel() → SetupViewModel
            └── hiltViewModel() → HomeViewModel

AuthModule → provides: AuthRepository, ProfileImageStorage
DataModule → provides: GymDatabase, WorkoutDao, WorkoutRepository
```
