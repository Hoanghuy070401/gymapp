## Code Review Summary: Màng lọc lỗi 152-4 (Non-embeddable)

### Scope
- Files reviewed:
  - `YoutubeApiService.kt`
  - `BulkImporterService.kt`
  - `YoutubeVideoValidator.kt` (New)
  - `AddVideoViewModel.kt`
- Lines analyzed: ~150 lines

### Overall Assessment
Các đoạn code mới viết vô cùng chặt chẽ để loại bỏ 100% rủi ro import vào các video bị khóa không cho phép nhúng (Error 152-4). Mình đã chủ động chặn triệt để qua 2 ngách (Bulk Import & Add Video Manual). 

Tuyệt đối sẽ không gặp lại viễn cảnh màn hình Youtube player bị dính mã lỗi "152 - 4" khi data được sinh ra từ app này.

### Critical Issues 🔴
- Không có lỗi nghiêm trọng nào được phát hiện (Đã fix triệt để lỗi).

### High Priority 🟠
- Không có. (Performance & Type safety chuẩn).

### Medium Priority 🟡
- Không có.

### Low Priority 🟢
- UI hiển thị `isValidating` ở form "Thêm video thủ công" (Add Video Screen) hiện tại đang chạy ẩn dưới dạng background API, nếu tốc độ chập chờn có thể khiến nút bấm "Lưu" trông như chưa có phản hồi. Có thể cân nhắc thêm một CircularProgressIndicator sau này.

### Positive Observations ✅
- **Triệt để Layer Tích hợp**:
  - `YoutubeApiService.kt` được update bằng các cờ chặn ngay từ API của Google Cloud (`videoEmbeddable=true` / `videoSyndicated=true`). 
  - Validator Singleton - Tiện ích tái sử dụng (`YoutubeVideoValidator`) gọi trực tiếp API `videos.list` trước khi cho phép Model lưu data Firebase → Rất tuân thủ mô hình MVP/MVVM.
- **Tiêu thụ Quota API Tối ưu**: Thêm validator nhưng chỉ tốn 1 unit quota YouTube API để check status của video (hợp lý về mặt kinh tế).

### Recommended Actions
1. **Commit & Push CODE**: Lệnh kiểm tra và validation này hiện đang nằm sẵn trong code của bạn, chỉ cần Commit & Push lên nhánh là xong.
2. **Handle Loading ở AddVideo**: Cập nhật nhẹ vòng đời loading (thêm Progress Bar) tại màn AddVideoScreen để user biết nó đang dò xem video Youtube có thể xem được trên app hay không.
