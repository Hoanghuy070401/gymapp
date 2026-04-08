# Kế Hoạch Hiện Thực: Xóa Rác YouTube Shorts & Hệ Sinh Thái Thư Viện Bài Tập Đa Nguồn

Dựa trên phỏng vấn chiến lược với Founder, plan này giải quyết nỗi đau của hệ thống IFrame Youtube bấp bênh và mở ra chân trời minh họa bài tập độc lập (Exercise Library) bằng công nghệ Open API kết hợp Firebase.

## Hiện trạng (Status)
**Lý do ra đời:** Phần lớn video clip dạng dọc (#shorts) gặp lỗi DRM/152 và bóp méo giao diện UX theo chiều ngang. Để khắc phục, Youtube Shorts sẽ bị tẩy chay hoàn toàn khỏi module cũ, song song đó là xây dựng một màn hình mới gọi là "Giáo trình Bài tập" cung cấp các ảnh GIF/MP4 dạng Loop vô hạn bằng nguồn từ ExerciseDB API (Miễn phí).
**Tình trạng Plan:** `IN_PROGRESS`

## Thiết kế Màn hình / Modules
1. **Module Bộ Lọc (Youtube Filter Block)**: Ngăn chặn triệt để video tự tải vào App.
2. **Module Exercise Library**: Tab mới trên luồng điều hướng, dành cho tra cứu bài tập theo dạng GIF tĩnh/động nhỏ gọn.

## Các giai đoạn (Phases)

- [x] **Phase 1: Bọc lót bộ lọc Youtube (Filtering Out Shorts)** ✅ DONE 2026-04-08
  - ✅ Thêm `parseDurationSeconds()` + extension `isShorts` vào `YoutubeModels.kt`
  - ✅ Tích hợp Shorts filter vào `BulkImporterService` với counter `skippedShorts`
  - ✅ Tích hợp Shorts filter vào `YoutubeVideoValidator` với thông báo tiếng Việt
  
- [ ] **Phase 2: R&D Khai thác nguồn API Miễn Phí (ExerciseDB)**
  - Xin Key/Lấy thông tin từ RapidAPI cho bộ thư viện ExerciseDB hoặc chuẩn bị Firebase Backup.
  - Phác thảo Data Model mới `ExerciseInfo(sourceType, targetMuscle, gifUrl, instructions)`.

- [ ] **Phase 3: Dựng luồng hiển thị (UI)**
  - Tách giao diện làm 2 phân khu. Thiết kế thẻ bài tập động chạy tự động mà không cần IFrame Player.
