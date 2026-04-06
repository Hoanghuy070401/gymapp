# FitBody Android 🏋️

Ứng dụng fitness Android được xây dựng với Jetpack Compose và Firebase, thiết kế theo phong cách "Kinetic Editorial".

## Tech Stack

| Layer | Công nghệ |
|-------|-----------|
| Ngôn ngữ | Kotlin 1.9.22 |
| UI | Jetpack Compose 1.6.0 + Material3 |
| Kiến trúc | MVVM + Clean Architecture + Multi-module |
| DI | Hilt 2.51.1 |
| Auth | Firebase Auth (Email/Password + Google Sign-In) |
| Database | Firebase Realtime Database + Room (local) |
| Min SDK | 26 (Android 8.0) | Compile SDK: 34 |

## Cấu trúc Module

```
app/              → Entry point, navigation graph, DI config
core/             → Design system (AppColors, GymScaffold, GymButton, GymTextField...)
domain/           → Business logic: models, use cases, repository interfaces
data/             → Room database + repository implementations
feature_auth/     → Login, SignUp, Email Verification, Forgot Password, Setup Wizard (8 bước)
feature_onboarding/ → Onboarding slides (3 màn hình)
feature_home/     → Home Dashboard (đang phát triển)
feature_workout/  → Workout tracking (đang phát triển)
docs/             → Tài liệu dự án
plans/            → Feature implementation plans
```

## User Flow

```
Splash → Onboarding → Login/SignUp → Email Verification → Setup Wizard (8 bước) → Home
                            └→ [User cũ đã setup] ─────────────────────────────────→ Home
                            └→ Forgot Password → Set Password → Set Fingerprint → Login
```

## Getting Started

1. Clone repo
2. Thêm `google-services.json` vào thư mục `app/`
3. Firebase Console → bật **Email/Password** và **Google Sign-In**
4. Cập nhật `GOOGLE_WEB_CLIENT_ID` trong `LoginScreen.kt`
5. Thiết lập Firebase Realtime Database Security Rules:
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
6. Build: `./gradlew assembleDebug` hoặc Android Studio

## Tài liệu dự án (Documentation)

| Tài liệu | Mô tả |
|----------|-------|
| [Tổng quan dự án](docs/project-overview-pdr.md) | Product requirements, tech stack, user flow |
| [Kiến trúc hệ thống](docs/system-architecture.md) | Module structure, navigation, Firebase schema, MVVM pattern |
| [Tóm tắt codebase](docs/codebase-summary.md) | Chi tiết từng file theo module |
| [Tiêu chuẩn code](docs/code-standards.md) | Naming, architecture rules, Compose patterns, error handling |
| [Hướng dẫn thiết kế](docs/design-guidelines.md) | Kinetic Editorial design system, tokens, component specs |
| [Lộ trình phát triển](docs/project-roadmap.md) | Phase 1 (done), Phase 2-4 (planned), tech debt |

## Development Status

- ✅ **Phase 1** — Auth, Onboarding, Setup Wizard: **HOÀN THÀNH**
- 🚧 **Phase 2** — Home Dashboard, Workout Core: **ĐANG PHÁT TRIỂN**
- 📋 **Phase 3** — Offline/Sync: Planned
- 📋 **Phase 4** — Advanced/Social: Planned
