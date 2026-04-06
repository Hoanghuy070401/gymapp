# Lộ trình phát triển (Project Roadmap)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

---

## ✅ Phase 1 — Auth, Onboarding & Setup (HOÀN THÀNH)

### Authentication
- [x] Email/Password login & registration
- [x] Google Sign-In via Credential Manager API
- [x] Email verification flow (auto-check 3s polling)
- [x] Forgot password / reset email
- [x] Set password screen
- [x] Set fingerprint screen (UI hoàn chỉnh)
- [x] Firebase error messages toàn diện → tiếng Việt
- [x] Client-side form validation (`FormValidator`)

### Onboarding
- [x] 3-slide `HorizontalPager` với full-screen background photos
- [x] ElectricLime page indicator dots
- [x] Manual swipe + auto-advance support

### Setup Wizard (8 bước)
- [x] `StepProgressBar` hiển thị tiến trình
- [x] Step 1: Intro screen (background image)
- [x] Step 2: Gender (pill selectors)
- [x] Step 3: Age (scroll pager)
- [x] Step 4: Weight in kg
- [x] Step 5: Height in cm
- [x] Step 6: Fitness goal (pill selectors)
- [x] Step 7: Activity level (pill selectors)
- [x] Step 8: Fill Profile (nickname, DOB, mobile, avatar upload)
- [x] Avatar upload → `ProfileImageStorage` (internal storage persistent)
- [x] Firebase RTDB sync: `saveUserProfile()` + `markSetupCompleted()`
- [x] Loading + error states trong `SetupScreen`

### Design System (core module)
- [x] AppColors (ElectricLime, TonalLavender, Surface, SurfaceContainerHigh...)
- [x] AppTypography, AppSpacing, AppShape
- [x] GymScaffold, GymButton, GymTextField, GymAvatar
- [x] GymCard, StatCard, SectionHeader, ProgressRing, StepProgressBar
- [x] StateContent (loading/error/empty/success states)
- [x] BaseViewModel, GymLogger

---

## 🚧 Phase 2 — Home Dashboard & Workout Core (ĐANG PHÁT TRIỂN)

### Home Dashboard
- [x] `HomeScreen` UI layout hoàn chỉnh
- [x] `HomeHeaderSection` (greeting, Search/Notifications/Profile)
- [x] `HomeCategoryGrid` (Workout, Progress, Nutrition, Community)
- [x] `RecommendedSection` (horizontal scroll, gradient cards)
- [x] `WeeklyChallengeSection` (progress display)
- [x] `ArticlesSection` (horizontal scroll)
- [x] `HomeViewModel` với HomeState (isLoading, userName, routines, articles, challenge)
- [ ] ⚠️ `HomeViewModel` migrate từ direct Firebase call → Repository pattern
- [ ] Home API: kết nối thực với Firebase data (playlists, challenges, articles)
- [ ] User profile data display (avatar từ storage)

### Workout Module
- [ ] Exercise library list (`WorkoutScreen` hoàn thiện)
- [ ] `WorkoutContract` MVI events wired vào `WorkoutViewModel`
- [ ] Search & filter exercises theo muscle group
- [ ] Workout session start flow
- [ ] Rest timer giữa sets
- [ ] Log sets/reps/weight
- [ ] Save workout session → Room

### Tech Debt từ Phase 1
- [ ] `SetFingerprintScreen` — wire `BiometricPrompt` API
- [ ] Social auth buttons (Facebook) — integrate Facebook SDK
- [ ] Email verification resend cooldown timer

---

## 📋 Phase 3 — Offline & Data Persistence

- [ ] Room database đầy đủ cho workout logs
- [ ] `WorkoutRepositoryImpl` kết nối Firebase Realtime Database
- [ ] `SyncWorkoutsUseCase` hoạt động thực
- [ ] Local-first sync strategy (offline → cloud backup khi online)
- [ ] Paging (`Paging3`) cho exercise library
- [ ] Room migration strategy

---

## 📋 Phase 4 — Advanced Features & Social

- [ ] BiometricPrompt integration đầy đủ
- [ ] Firebase FCM push notifications
- [ ] Facebook OAuth integration
- [ ] Progress charts (Timeline + Stats)
- [ ] Workout sharing (share card via Intent)
- [ ] Community features (activity feed, following)
- [ ] Nutrition tracking module
- [ ] Profile editing screen với avatar update

---

## Known Issues / Tech Debt

| Issue | Priority | Notes |
|-------|----------|-------|
| `HomeViewModel` direct Firebase call | 🔴 High | Vi phạm Clean Architecture. Cần HomeRepository |
| `SetFingerprintScreen` — UI only | 🟡 Medium | `BiometricPrompt` chưa wired |
| Facebook button — UI only | 🟡 Medium | Facebook SDK chưa integrate |
| `WorkoutRepositoryImpl` — local only | 🟡 Medium | Firebase sync là TODO |
| Email verification resend cooldown | 🟢 Low | UX improvement needed |
| Lint warning `lifecycle-viewmodel-ktx` | 🟢 Low | Stale Gradle cache; format valid |

---

## Ngày hoàn thành ước tính (Estimated Timeline)

| Phase | Estimated |
|-------|-----------|
| Phase 2 hoàn chỉnh | Q2 2026 |
| Phase 3 | Q3 2026 |
| Phase 4 | Q4 2026 |
