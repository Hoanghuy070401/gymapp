# FitBody — Tổng quan dự án & Yêu cầu phát triển (PDR)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

## Tóm tắt sản phẩm (Product Summary)

**FitBody** là ứng dụng Android fitness theo phong cách editorial, giúp người dùng theo dõi lịch tập, quản lý bài tập và cá nhân hóa hành trình thể dục. App được xây dựng bằng Jetpack Compose với design system "Kinetic Editorial" độc quyền.

**Target users:** Người tập gym trình độ beginner → intermediate, độ tuổi 18–35, thị trường Việt Nam.

---

## Công nghệ sử dụng (Tech Stack)

| Layer | Công nghệ |
|-------|-----------|
| Ngôn ngữ | Kotlin 1.9.22 |
| UI | Jetpack Compose 1.6.0 + Material3 |
| Kiến trúc | MVVM + Clean Architecture + Multi-module |
| DI | Hilt 2.51.1 |
| Auth | Firebase Auth (Email/Password + Google Sign-In via Credential Manager) |
| Database | Firebase Realtime Database + Room (local) |
| Navigation | Jetpack Navigation Compose 2.7.7 |
| Async | Kotlin Coroutines 1.7.3 |
| Image | Coil (planned) |
| Min SDK | 26 (Android 8.0) |
| Compile SDK | 34 (Android 14) |

---

## Luồng người dùng (User Flow)

```
Splash (1.5s)
  └→ Onboarding (3 slides)
       └→ Login                   ← Email/Password hoặc Google Sign-In
            ├→ [Cũ, setup xong]  → Home Dashboard
            ├→ [Mới, Google]     → Setup Wizard (8 bước) → Home
            └→ [Mới, Email]      → Email Verification Screen
                                      └→ [Verified] → Setup Wizard → Home
            └→ Forgot Password → Set Password → Set Fingerprint → Login

       └→ Sign Up
            └→ Email Verification Screen
                 └→ [Verified] → Setup Wizard (8 bước) → Home
```

---

## Yêu cầu sản phẩm (Product Requirements)

### ✅ Phase 1 — Authentication & Onboarding (HOÀN THÀNH)

- [x] Onboarding: 3 slide với full-screen motivational backgrounds
- [x] Đăng ký Email/Password + Google Sign-In (Credential Manager)
- [x] Email verification flow (`EmailVerificationScreen` với auto-check 3s)
- [x] Quên mật khẩu / Đặt lại mật khẩu
- [x] Set Fingerprint screen (UI hoàn chỉnh, BiometricPrompt API chưa wired)
- [x] Setup Wizard 8 bước đầy đủ với `StepProgressBar`:
  - Step 1: Intro (background image)
  - Step 2: Gender (pill selectors)
  - Step 3: Age (scroll picker)
  - Step 4: Weight (kg slider)
  - Step 5: Height (cm picker)
  - Step 6: Goal (pill selectors)
  - Step 7: Activity Level (pill selectors)
  - Step 8: Fill Profile (nickname, DOB, mobile, avatar upload)
- [x] Upload & persist avatar (`ProfileImageStorage` → internal storage)
- [x] Firebase RTDB sync sau Setup: `saveUserProfile()` + `markSetupCompleted()`
- [x] Smart routing: `checkSetupCompleted()` → Home hoặc Setup
- [x] Form validation client-side (`FormValidator`) với error messages tiếng Việt
- [x] Firebase error mapping toàn diện sang tiếng Việt (`mapFirebaseError()`)
- [x] Design system hoàn chỉnh (AppColors, AppTypography, AppSpacing, GymScaffold, GymButton, GymTextField, v.v.)

### 🚧 Phase 2 — Home Dashboard & Core Features (ĐANG PHÁT TRIỂN)

- [x] Home Dashboard UI cơ bản:
  - [x] Header: greeting, Search/Notifications/Profile icon buttons
  - [x] `HomeCategoryGrid` (2×2): Workout, Progress, Nutrition, Community
  - [x] `RecommendedSection` horizontal scroll với gradient cards
  - [x] `WeeklyChallengeSection` với progress display
  - [x] `ArticlesSection` horizontal scroll
  - [x] Loading state (`CircularProgressIndicator`)
- [ ] `HomeViewModel` migrate sang Repository pattern (hiện gọi Firebase trực tiếp)
- [ ] Home data: kết nối thực với Firebase/Room data
- [ ] Workout Module:
  - [ ] Exercise library list (search + filter theo nhóm cơ)
  - [ ] Workout session start/timer
  - [ ] Rest timer giữa sets
  - [ ] Ghi log: sets/reps/weight
- [ ] Firebase cloud sync cho WorkoutRepositoryImpl
- [ ] `StateContent` component hoàn thiện và áp dụng

### 📋 Phase 3 — Offline & Data Persistence

- [ ] Room database đầy đủ cho workout logs (offline-first)
- [ ] Sync strategy: Local-first → Firebase cloud backup
- [ ] WorkoutRepositoryImpl kết nối Firebase
- [ ] Paging cho exercise library

### 📋 Phase 4 — Advanced & Social Features

- [ ] BiometricPrompt integration (`SetFingerprintScreen`)
- [ ] Push notifications (Firebase FCM)
- [ ] Social auth (Facebook SDK integration)
- [ ] Workout sharing
- [ ] Progress charts
- [ ] Community features

---

## Non-Functional Requirements

| Requirement | Spec |
|-------------|------|
| Min Android version | API 26 (Android 8.0) |
| Target | API 34 (Android 14) |
| Language | Kotlin only (no Java) |
| UI framework | Jetpack Compose only (no XML layouts) |
| Architecture | MVVM + Clean Architecture; feature modules không phụ thuộc nhau |
| Error messages | 100% tiếng Việt thân thiện với user |
| Security | Firebase rules: `auth.uid == $uid` per user node |

---

## Môi trường (Environment)

| Item | Value |
|------|-------|
| Firebase project | `gym-app-d97c5` |
| RTDB URL | `https://gym-app-d97c5-default-rtdb.asia-southeast1.firebasedatabase.app` |
| Auth methods | Email/Password, Google Sign-In |
| Config file | `app/google-services.json` |
