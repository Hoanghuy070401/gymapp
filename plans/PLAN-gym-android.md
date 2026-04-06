# 🏋️ Gym Android App - Development Plan

## 1. Overview & Context
This project aims to build a modern, scaling-ready Gym/Fitness Android application following a "Kinetic Editorial" design system. The app will utilize Firebase Realtime Database as the remote source, explicitly combined with a robust Offline-first architecture (e.g. Room + Repository Pattern).

## 2. Project Type
**MOBILE** (Android / Kotlin 1.9.22 / Jetpack Compose 1.6.0)

## 3. Success Criteria
- ✅ App handles offline state gracefully with local DB caching (Room offline sync).
- ✅ UI strictly implements the Kinetic Archive design system (Kinetic Purple, Electric Lime, No 1px borders, Ghost borders).
- ✅ Clean Architecture + MVI pattern strictly separated (`core`, `domain`, `data`, `presentation` / feature modules).
- ✅ Golden Rules applied: UI=render state, State=ViewModel, Logic=UseCase, Data=Repository.

## 4. Tech Stack & Rationale
| Component | Technology | Rationale |
|-----------|------------|-----------|
| UI | Jetpack Compose | Required by `README.md`. Declarative UI pattern. |
| Architecture | Clean + MVI | Scalability and Unidirectional Data Flow. |
| Local DB | Room + Paging 3 | Required for Offline-first cache and list performance. |
| Remote DB | Firebase Realtime DB| Requested by user for cloud storage & sync. |
| DI | Hilt | Standard Android dependency injection. |
| Async | Coroutines + StateFlow| First-class Kotlin concurrency + reactive state. |

## 5. File Structure (Multi-module architecture)
```text
project_root/
 ├─ app/                     # Application module (entry point)
 ├─ core/                    # Tokens (AppColors, AppTypography, AppSpacing, BaseUI)
 ├─ domain/                  # Entities, UseCases (1 usecase/action), Repository interfaces
 ├─ data/                    # Firebase API, Room DB, Repositories Implementations
 ├─ feature_auth/            # User onboarding, Setup specs (Age, Gender, Goal)
 ├─ feature_home/            # Main Dashboard, Recommended Workouts, Weekly Challenge
 ├─ feature_workout/         # Preset/Custom Routines, Tracking, Progress Logs
 ├─ feature_nutrition/       # Meal Plans, Ideas (Breakfast, Lunch, Dinner)
 ├─ feature_discover/        # Articles, Tips, Community
 └─ libs.versions.toml       # Centralized dependencies
```

## 6. Task Breakdown

### TS-01: Initialize Core Architecture & Design System
- **Agent**: `mobile-developer`
- **Skills**: `mobile-design`, `clean-code`
- **Priority**: P0
- **Dependencies**: None
- **INPUT**: `README.md`, `DESIGN.md` (Colors: #896CFE, #E2F153; Fonts: Poppins, League Spartan).
- **OUTPUT**: `core/` module with Kinetic Archive design tokens. Base `libs.versions.toml`, MVI ViewModel base.
- **VERIFY**: Check `core/` module has no standard material colors, only Kinetic theme logic.

### TS-02: Base Data Layer & Offline-first Config
- **Agent**: `mobile-developer`
- **Skills**: `clean-code`, `database-design`
- **Priority**: P0
- **Dependencies**: TS-01
- **INPUT**: Firebase config.
- **OUTPUT**: `data/` and `domain/` structures. Room DB Setup + Firebase Remote Source. Offline-sync strategy implemented via Repository.
- **VERIFY**: Repository returns `Flow<State>` capable of serving cached local data when device goes offline.

### TS-03: Onboarding & Authentication Flow
- **Agent**: `mobile-developer`
- **Skills**: `mobile-design`
- **Priority**: P1
- **Dependencies**: TS-02
- **INPUT**: Mindmap "Login/Register", "Set Up" (Gender, Age, Weight, Height, Goal, Activity level).
- **OUTPUT**: `feature_auth` including splash, login, and profile config screens.
- **VERIFY**: User data is correctly cached locally in Room and uploaded to Firebase Realtime DB.

### TS-04: Home Dashboard & Navigation
- **Agent**: `mobile-developer`
- **Skills**: `mobile-design`
- **Priority**: P1
- **Dependencies**: TS-03
- **INPUT**: Mindmap Home & Discover nodes.
- **OUTPUT**: BottomNavigation, `feature_home` with dynamic Recommend Workouts and Weekly Challenge sections.
- **VERIFY**: UI adheres to "Tonal Layering" without 1px horizontal dividers. Uses `surface-container-high` shifts.

### TS-05: Workout Tracking & Progress Flow
- **Agent**: `mobile-developer`
- **Skills**: `clean-code`
- **Priority**: P1
- **Dependencies**: TS-04
- **INPUT**: Mindmap Workout nodes (Preset routines, custom, workout log, progress charts).
- **OUTPUT**: `feature_workout`.
- **VERIFY**: Ability to log a workout fully offline and sync it automatically when Firebase reconnects.

### TS-06: Nutrition, Reports & Community Flow
- **Agent**: `mobile-developer`
- **Skills**: `clean-code`
- **Priority**: P2
- **Dependencies**: TS-04
- **INPUT**: Mindmap Nutrition (Meals), Reports (BMI, Water, Calories), Community.
- **OUTPUT**: `feature_nutrition` and `feature_discover` components.
- **VERIFY**: Shimmer loadings are used for all remote fetch scenarios per `README.md`.

### TS-07: Final Review & Phase X Verification
- **Agent**: `mobile-developer`
- **Skills**: `performance-profiling`, `vulnerability-scanner`, `testing-patterns`
- **Priority**: P3
- **Dependencies**: All previously completed tasks
- **INPUT**: Complete Android codebase.
- **OUTPUT**: Code review, lint fixes, domain unit test coverage check.
- **VERIFY**: `checklist.py` (or Android equivalent `./gradlew check`) evaluates to Success without crashes.

---

## ✅ PHASE X COMPLETE
- [ ] Lint & Arch: Pending
- [ ] Security/Firebase Rules: Pending
- [ ] Build & Test: Pending
- Date: [TBD]
