# Interview Notes: Lọc YouTube Shorts & Tích hợp Nguồn Video Bài tập Mới
**Date**: 2026-04-08
**Mode**: Product & Technical
**Created By:** Nguyen Huy
**Input**: Lời thoại phỏng vấn (@interview)

## Key Insights

### Lọc & Loại bỏ YouTube Shorts khỏi Bộ tải Bulk Import
- Vấn đề: Tỷ lệ cao YouTube Shorts phát sinh lỗi báo khóa 152 do DRM + Không khớp Layout 16:9 ngang làm xấu giao diện.
- Giải pháp: Áp dụng Lọc Kép (Option C). Cả tính năng Bulk Import lẫn tính năng Add Video thủ công sẽ bỏ qua/thả lỗi bất kỳ video nào có chứa chữ `#shorts` (hoặc `#Shorts`) trong `title` / `description` HOẶC có thời lượng thực tế dưới 60 giây.

### Tiêu chí chọn nguồn Video Miễn phí thay thế
- Vì YouTube liên tục gây khó dễ với tính năng WebView embedded, hệ thống cần nguồn media gốc tự động khác.
- Yêu cầu: Miễn phí, chất lượng, ít chết link.
- Quyết định (C & A): App sẽ khai thác các **Public APIs (Ví dụ: ExerciseDB API)** để cào hàng ngàn hình minh họa động `.gif` dạng lập lặp (looping) bóc tách đúng phần cơ bắp đang tập luyện, kết hợp cùng các Public MP4 pool nếu có.

### Trải nghiệm UX & Cấu trúc Dữ liệu Đa nguồn (Multi-source)
- Việc pha trộn Video Youtube Full-HD 15-phút dài thoòng và ảnh GIF 3-giây sẽ làm nát cấu trúc luồng của `VideoDetailScreen`.
- Quyết định chốt hạ (Option C): **Tách App làm 2 khu vực độc lập**:
  - **Khu 1: Video Tập Luyện (Workout Videos)** - Đi tiếp với hệ cấu trúc cũ, dùng IFramePlayer, chiếu bài tập theo dạng Follow-along (tập theo thời gian thực với HLV).
  - **Khu 2: Giáo Trình Bài Tập (Exercise Library)** - Khu vực tập trung tra cứu bách khoa toàn thư. Dùng Native Image Loader / ExoPlayer để chiếu các file `.gif` / `.mp4` ngắn minh họa tư thế lặp lại.

## Decisions Made

- Lọc cứng YouTube Shorts (< 60s hoặc tag shorts) lúc import.
- Không đả động đến UX hiện tại của `VideoDetailScreen`. Thay vào đó, mở ra module hệ thống hoàn toàn mới là **Exercise Library**.

## Recommendations

- Phase 1 ngay lập tức: Cập nhật luồng Code của `BulkImporterService` và `YoutubeVideoValidator` để hoàn chỉnh bộ lọc chống Shorts, đảm bảo Data đang nạp luôn có chất lượng cao nhất.
- Phase 2: Áp dụng AI Research để thiết kế Data Model và kết nối với mạng lưới ExerciseDB Rest API chuyên tra cứu cơ bắp.
