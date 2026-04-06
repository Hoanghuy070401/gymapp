# FitBody Android 🏋️

Ứng dụng fitness Android được xây dựng với Jetpack Compose và Firebase, thiết kế theo phong cách "Kinetic Editorial".

## Ngôn ngữ và Công nghệ (Tech Stack)

- **Ngôn ngữ**: Kotlin 1.9.22
- **UI**: Jetpack Compose 1.6.0 + Material3
- **Kiến trúc**: MVVM + Clean Architecture + Multi-module
- **DI (Dependency Injection)**: Hilt 2.51.1
- **Xác thực (Auth)**: Firebase Auth (Email/Password + Google Sign-In qua Credential Manager)
- **Cơ sở dữ liệu (Database)**: Firebase Realtime Database + Room (local)
- **Min SDK**: 26 | **Compile SDK**: 34

## Cấu trúc Module

```
app/             → Entry point, navigation, cấu hình DI (Dependency Injection)
core/            → Design system (AppColors, GymScaffold, GymButton, GymTextField...)
domain/          → Business logic: model, use case, repository interface
data/            → Cài đặt (implementation) của Room và repository
feature_auth/    → Đăng nhập (Login), Đăng ký (SignUp), Quên mật khẩu, Setup Wizard (8 bước)
feature_onboarding/ → Các màn hình hướng dẫn ban đầu (Onboarding slides)
feature_home/    → Trang chủ dashboard (đang phát triển)
feature_workout/ → Theo dõi bài tập (đang phát triển)
docs/            → Tài liệu dự án, architecture, code standards
plans/           → Kế hoạch phát triển tính năng (Feature plans)
```

## Luồng người dùng (User Flow)

```
Splash → Onboarding → Login/SignUp → Setup Wizard (8 bước) → Home
                           └→ Forgot Password → Set Password → Set Fingerprint
```

## Hướng dẫn cài đặt (Getting Started)

1. Clone repo
2. Thêm file `google-services.json` vào thư mục `app/`
3. Vào Firebase Console → bật **Email/Password** và **Google Sign-in**
4. Cập nhật `GOOGLE_WEB_CLIENT_ID` trong `LoginScreen.kt` bằng Web Client ID từ Firebase
5. Thiết lập Security Rules cho Firebase Realtime Database:
```json
{
  "rules": {
    "users": {
      "$uid": {
        // Người dùng chỉ được đọc và lưu data vào đúng nhánh có tên là UID của họ
        ".read": "auth != null && auth.uid == $uid",
        ".write": "auth != null"
      }
    }
  }
}
```
6. Chạy lệnh `./gradlew assembleDebug` hoặc build qua Android Studio.

## Tài liệu dự án (Documentation)

| Tài liệu (Doc) | Mô tả |
|-----|-------------|
| [Tổng quan dự án](docs/project-overview-pdr.md) | Yêu cầu sản phẩm (Product requirements) và công nghệ sử dụng |
| [Kiến trúc hệ thống](docs/system-architecture.md) | Cấu trúc module, điều hướng (navigation), luồng dữ liệu Firebase |
| [Tóm tắt codebase](docs/codebase-summary.md) | Phân tích chi tiết từng file trong các module |
| [Tiêu chuẩn code](docs/code-standards.md) | Quy tắc đặt tên, chuẩn kiến trúc, các mẫu thiết kế Compose (Compose patterns) |
| [Hướng dẫn thiết kế](docs/design-guidelines.md) | Hệ thống thiết kế (Design system) Kinetic Editorial |
| [Lộ trình phát triển](docs/project-roadmap.md) | Các tính năng đã hoàn thành, các giai đoạn tiếp theo, nợ kỹ thuật (tech debt) |# gymapp
