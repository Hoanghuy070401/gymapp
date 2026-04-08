# Implementation Plan: In-App Language Switcher (EN-VI)

**Mục tiêu (Objective):** Đồng bộ hóa trải nghiệm ngôn ngữ của người dùng. UI hiện tại dùng Hardcoded Vietnamese, trong khi API content dùng English. Cần một cơ chế đổi ngôn ngữ song song và mượt mà cho UI layer và Data layer.

---

## 1. Localize Hardcoded UI (Phần giao diện tĩnh)
Cả hai hướng tiếp cận đều phải dùng chung chuẩn nội địa hóa (Localization) của Jetpack Compose và Android:
- Chuyển toàn bộ các text cứng (VD: `"Chi Tiết Bài Tập"`) vào `res/values/strings.xml` (Tiếng Anh) và `res/values-vi/strings.xml` (Tiếng Việt).
- Thử nghiệm thư viện `androidx.appcompat.app.AppCompatDelegate` hoặc ViewModel Custom State để cho phép user đổi ngôn ngữ **ngay lập tức trong App (In-App Switcher)** tại màn hình Profile mà không cần khởi động lại toàn bộ điện thoại.

---

## 2. Dịch thuật Động (API Data Layer)
Do API WGER/ExerciseDB mặc định trả về Tiếng Anh, đây là 2 hướng giải quyết:

### Approach A: Google ML Kit On-Device Translation
Sử dụng thư viện `com.google.mlkit:translate` tích hợp trực tiếp vào Android.
- **Cách hoạt động:** Khi người dùng chọn ngôn ngữ tiếng Việt (hoặc tự động phát hiện ngôn ngữ máy), App sẽ chạy ngầm tải Model dịch thuật `En-Vi` (khoảng ~30MB). Mỗi khi gọi API ExerciseDB về, ViewModel sẽ ghim dữ liệu thô vào ML Kit qua Coroutines để dịch `name` và `instructions` trước khi nhả lên UI.
- **Chấm điểm tính khả thi (Trade-offs):**
  - **Pros (Ưu điểm):** Không cần duy trì máy chủ hay DB để lưu bản dịch. Nó hoạt động hoàn toàn offline sau khi đã download model, hoàn toàn tự động cho mọi API mới trong tương lai.
  - **Cons (Nhược điểm):** Độ trễ load UI tăng nhẹ (mất 200ms để dịch chuỗi). **Đặc biệt nghiêm trọng:** Thuật toán dịch tự động kiểu Google có thể dịch các từ vựng Gym chuyên ngành rất "ngô nghê". Ví dụ: *Chuyển "Skull Crushers" (Gập tay sau đầu) thành "Kẻ nghiền sọ"*, hoặc *"Dumbbell Fly" thành "Tạ đòn bay"*.

### Approach B: Server-Side Pre-Translation via Firebase (RECOMMENDED)
Chuyển đổi chiến lược không lấy API trực tiếp từ xa nữa. Admin App sẽ kéo toàn bộ 1300 bài tập trên ExerciseDB về một `Raw JSON`.
- **Cách hoạt động:** Setup một Script python / Node.js nhẹ để dịch toàn bộ 1300 exercises qua OpenAI API/DeepL API một lần duy nhất với các prompt chỉnh sửa riêng biệt cho Gym. Upload tập JSON đó lên **Firebase Realtime Database/Firestore** của dự án.
- **App sẽ hoạt động như thế nào:** App truy xuất danh sách bài tập thẳng từ Firebase (giống Bulk Importer) có chứa hai cột ngôn ngữ `vi_name`, `en_name`. 
- **Chấm điểm tính khả thi (Trade-offs):**
  - **Pros (Ưu điểm):** Tốc độ load siêu nhanh do không phải dịch realtime. Hoàn toàn kiểm soát bằng tay, Admin có thể sửa những chữ dịch sai/lủng củng. Giảm thiểu chi phí phụ thuộc vào RapidAPI key (Không lo Quota).
  - **Cons (Nhược điểm):** Phải làm thêm việc cấu hình 1 file script riêng + Quản lý Firebase Database tốn công hơn tí. Mất tính năng "cập nhật trực tiếp từ ExerciseDB mỗi ngày".

---

## 3. Recommended Approach (Khuyến nghị)
**Tôi khuyến nghị chọn Approach B.**
Lý do: Trong các cấu trúc App Fitness/Sức khỏe, thuật ngữ giải phẫu học và tên bài tập CỰC KỲ dễ dịch sai khiến người dùng hiểu lầm và tập sai tư thế, dẫn đến chấn thương do ngôn từ máy móc. Kiểm soát bản dịch trên Database tĩnh trên Firebase hoặc File cục bộ (Local Assets JSON) là cách an toàn và tối ưu nhất cho thiết kế ứng dụng Chuyên nghiệp (Production).

---

## Các Câu Hỏi Tồn Đọng (Unresolved Questions)
1. Bạn thiên về **Approach A (Dịch tự động chớp nhoáng)** hay **Approach B (Dịch trước một lần cho sạch sẽ - độ chính xác 100%)**?
2. Cho phần đổi ngôn ngữ App, bạn có đồng ý dùng cơ cấu Localized Compose mặc định `stringResource(R.string.key)` để triển khai không? Lượng công việc sẽ tốn khoảng 30 phút để bóc tách hết toàn bộ chữ trên UI cứng ra file strings XML.
