# Phase 3: Video Filtering & Recommendation Engine

## Overview
**Date:** 2026-04-06  
**Description:** Implement local/Firebase querying to filter the `workoutVideos` catalog so that users see personalized recommendations.

## Requirements
- Create logic to compare User Demographics (from Phase 2) against Video Tags (from Phase 1).
- Rank videos by relevance.

## Implementation Logistics

Because Firebase RTDB querying relies on simple indexed fields and doesn't handle multi-field array containment well natively (e.g. `WHERE targetAges CONTAINS "Seniors" AND level == "Beginner"`), the easiest and most performant approach (for small to medium catalog sizes) is:

1. **Fetch Entire Catalog Locally** (Hoặc một lượng đủ lớn). 
2. **Local Scoring/Filtering Engine**:
   - *Filter Rule 1 (Data Integrity - Bắt buộc):* Bỏ qua hoàn toàn các video cũ không có data trong biến `tags`.
   - *Filter Rule 2 (Age Safety - Phủ quyết):* 
     - Lấy tập video thoả mãn quy tắc: `Video.targetAges` chứa `Tuổi của User` (hoặc chứa "All"). 
     - Nếu User là `Seniors` (Age > 55), chỉ lấy video có tag "Seniors" hoặc "All". Không được lẫn "Children" hoặc "Adults".
     
   - *Scoring Rule (Cộng điểm xếp hạng):* Bắt đầu score = 0 cho các video đã qua 2 vòng lọc trên.
     - Match `goal`: +5 điểm nếu `Video.targetGoals` chứa `User.goal`.
     - Match `activityLevel`: +3 điểm nếu `Video.level` == `User.activityLevel`.
   
3. **Sort by Score descending**, và ghi đè hoàn toàn mục `RecommendedSection` tại Home bằng top 5-10 video điểm cao nhất này để mang lại tính CÁ NHÂN HOÁ cao nhất.

## Related code files
- `HomeViewModel.kt`
- `VideoRepository.kt`
- `WorkoutVideo.kt`

## Next steps
Review the entire plan with the user. Upon sign-off, begin Phase 1 execution.
