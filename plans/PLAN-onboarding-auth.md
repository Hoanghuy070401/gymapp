# Onboarding & Authentication Flow

## Goal
Xây dựng luồng màn hình ưu tiên trước khi người dùng vào Home Page (Pre-Home Flow), bao gồm các phân hệ: Launch (Splash), Onboarding, Authentication (Login, Sign Up, Facebook/Google), và User Setup (Wizard 8 bước).

## Tasks
- [ ] **Task 1: Launch & Routing Strategy**
  - Logic điều hướng tập trung (Navigation Graph):
    - Đã đăng nhập + đã setup profile -> `Home Page`.
    - Đã đăng nhập + chưa hoàn thành setup -> `Set Up Flow`.
    - Chưa đăng nhập -> `Onboarding` hoặc `Login`.
- [ ] **Task 2: Onboarding Flow (Mockup Hình 1)**
  - Xây dựng 4 màn hình Onboarding:
    - **2-A**: Splash/Intro ("Welcome to FitBody").
    - **2-B, 2-C, 2-D**: Sử dụng `HorizontalPager` với hình ảnh full screen, block màu tím (PrimaryKinetic) chứa nội dung, có nút Skip và Next/Get Started.
- [ ] **Task 3: Authentication (Login / Sign Up)**
  - Tích hợp Đăng nhập bằng Email/Password.
  - Tích hợp tính năng Social Login: **Google** và **Facebook**.
  - Tích hợp điều hướng sang "Forgot and Reset Password" (3.1).
- [ ] **Task 4: User Set Up Flow (Mockup Hình 2)**
  - Xây dựng layout dạng tiến trình gồm 8 màn hình (1 Intro + 7 Steps).
  - **4-A** Setup Intro: Màn hình giới thiệu "Consistency Is The Key...".
  - **4.1** Gender: 2 nút tròn lớn (Male/Female) đổi màu khi chọn (Electric Lime).
  - **4.2** Age: Thanh cuộn chọn số (Wheel Picker / Snapper) nằm ngang.
  - **4.3** Weight: Thước đo (Horizontal Ruler/Scale) + Toggle chọn KG/LB.
  - **4.4** Height: Thước đo (Vertical Ruler/Scale).
  - **4.5** Goal: Danh sách tuỳ chọn với nút Radio dạng tròn.
  - **4.6** Activity Level: Danh sách các nút dạng Pill (Beginner, Intermediate, Advance).
  - **4.7** Fill Profile: Form điền thông tin (Full name, Nickname, Email, Mobile) + Avatar Picker.
- [ ] **Task 5: Data Integration**
  - Sử dụng chung 1 `SetupViewModel` hứng dữ liệu từ chuỗi màn hình Setup.
  - Lưu trữ local state (StateFlow/SavedStateHandle) trong suốt quá trình setup.
  - Đồng bộ toàn bộ dữ liệu lên Firebase Realtime Database **CHỈ MỘT LẦN** tại step 4.7 (Fill Profile).
  - Cập nhật Profile Data -> set cờ `isSetupCompleted = true` -> Navigate sang `Home Page`.

## Verification
- [ ] UI giống mockup 100%, bảo đảm rule No-Line và dùng hệ màu Kinetic Archive.
- [ ] Kiểm tra Google/Facebook login hoạt động.
- [ ] Custom UI picker (Ruler, Wheel) vuốt trơn tru.
- [ ] Xác nhận data chỉ được push lên Firebase ở bước cuối cùng.
