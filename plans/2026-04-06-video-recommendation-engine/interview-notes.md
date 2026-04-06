# Interview Notes: Data Model Refinement
**Date**: 2026-04-06
**Mode**: technical/product
**Created By:** AI
**Input**: plans/2026-04-06-video-recommendation-engine/plan.md

## Key Insights

### Phân nhóm Tuổi & Mục tiêu (Age & Goal Buckets)
- Quyết định loại bỏ cụm từ chung chung (Children, Adults, Seniors). Thay vào đó, áp dụng khắt khe bảng tiêu chuẩn độ tuổi chia thành 5 bucket nhằm hỗ trợ an toàn và định dạng luyện tập chính xác:
  - `12-17`: Tránh tạ nặng, focus form, phát triển nền + chiều cao. Thể lực: Nhẹ-TB.
  - `18-25`: Core + posture, tăng cơ, cải thiện vóc dáng. Thể lực: Trung-Cao.
  - `26-35`: Giảm volume, tăng recovery, giữ form + giảm mỡ. Thể lực: Trung bình.
  - `36-50`: Bắt buộc mobility, sức khoẻ + chống thoái hoá. Thể lực: Nhẹ-TB.
  - `50+`: Tránh bài áp lực khớp, khớp + tim mạch. Thể lực: Nhẹ.

### Liên kết với Lựa chọn Mục tiêu (Goal) ở Onboarding
- Quyết định chọn **Option B (Kết hợp / Matching)**.
- Người dùng vẫn giữ quyền tự do lựa chọn mục tiêu cá nhân trên UI lúc thiết lập (Gain Weight, Lose Weight...).
- Thuật toán Gợi ý sẽ sử dụng cơ chế MATCH KÉP: Video phải *khớp với Age Bucket của User* (1 sự bắt buộc chặt chẽ), đồng thời sẽ ưu tiên sắp xếp lên đỉnh những bài *khớp đúng với Goal mà User chủ động chọn* ở Onboarding. Sự kết hợp này mang tính linh hoạt, đảm bảo thỏa mãn mong muốn user mà ko phạm vào giới hạn an toàn y khoa.

## Decisions Made
- [Decision 1]: Sửa lại bucket targetAges trong Data Model thành: `12-17`, `18-25`, `26-35`, `36-50`, `50+`.
- [Decision 2]: Phase 2 Profile Logic sẽ tính toán theo 5 bucket mới.
- [Decision 3]: Giữ nguyên cơ chế cộng điểm (ranking boost) đối với Goal của người dùng.
