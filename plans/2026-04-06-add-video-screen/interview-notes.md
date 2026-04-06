# Interview Notes: Add Video Screen Refinement
**Date**: 2026-04-06
**Mode**: ux/technical
**Created By:** AI
**Input**: plans/2026-04-06-add-video-screen/plan.md

## Key Insights

### Phân quyền (Authorization)
- Cần mở quyền truy cập (Option C) ở thời điểm hiện tại để developer dễ dàng seed dữ liệu.
- Trong quá trình phát triển các chức năng sâu hơn về sau, sẽ tiến hành khóa (Option B - Admin role) nút bấm này để chặn user bình thường.

### Validation (Độ sạch của dữ liệu)
- Cần áp dụng Strict Validation (Option A).
- Yêu cầu người dùng / admin BẮT BUỘC chọn các trường dữ liệu quan trọng như: Age target, Goal target, Level.
- Không áp dụng fallback (mặc định) nếu bỏ trống, vì nó sẽ ảnh hưởng tiêu cực tới Data Structure của Recommendation Engine về sau.

### Edge Cases (Xử lý trải nghiệm thao tác nhầm)
- User chọn cả Option A (Warning Dialog) và Option C (Auto-Save Draft).
- Thiết kế: Hệ thống sẽ tự động lưu nháp (draft caching) trong ViewModel khi nhập dở (Option C). Tuy nhiên, nếu user nhấn Back để thoát ra, vẫn sẽ chủ động báo một UI Warning Dialog (Option A) để nhắc họ. Sẽ có tác dụng kép: Vừa nhắc nhở an toàn, vừa không bị mất công gõ lại.

## Decisions Made
- **Approach:** Tiến hành theo **Hướng A** (Dedicated Screen với AddVideoViewModel và AddVideoScreen).
- **Validation Rule:** Tất cả tags bắt buộc > 0 (Dữ liệu không rác).
- **UX Rule:** Có Warning Dialog Back + Draft Caching.

## Recommendations
- Trong view model sẽ cần biến state `draftForm` và một `BackHandler` composable ở UI.
