# Phase 2: Profile Logic & Calculations

## Overview
**Date:** 2026-04-06  
**Description:** Analyze raw user profile data and calculate actionable demographic attributes (Age bucket, BMI class).

## Requirements
To recommend the correct videos, the app needs to translate the raw `UserProfile` data into queryable buckets.

1. **Calculate Age:**
   - Raw data: `"dateOfBirth": "01/01/2000"` (String format `dd/MM/yyyy`).
   - Parse this date against the current date to find integer age.
   - Assign Age Bucket theo chuẩn Y khoa huấn luyện: 
     - `12-17`: 12 <= age <= 17
     - `18-25`: 18 <= age <= 25
     - `26-35`: 26 <= age <= 35
     - `36-50`: 36 <= age <= 50
     - `50+`: age > 50
     - (Nếu user < 12 tuổi thì gán mặc định vào 12-17 hoặc báo lỗi tuỳ policy).

2. **Calculate BMI:**
   - Formula: `weightKg / (heightM * heightM)` (Note: height is in cm, so divide by 100).
   - Assign BMI Bucket:
     - `Underweight`: < 18.5
     - `Normal`: 18.5 - 24.9
     - `Overweight`: 25 - 29.9
     - `Obese`: >= 30

## Related code files
- `UserActivityLevel.kt` / User profile data structures
- `HomeViewModel.kt` (Where profile data is fetched)
- Future: A new `ProfileUtils.kt` or `RecommendationEngine.kt` to house pure mathematical logic.

## Implementation Steps
1. Create a `ProfileRecommenderUtils` helper class to encapsulate date parsing, age, and BMI logic.
2. In `HomeViewModel` (or `SetupViewModel` or a dedicated `ProfileRepository`), calculate these values right after fetching user profile from `users/{uid}`.
3. Map `activityLevel` ("Advance") to the Video `level` ("Advanced"). Ensure casing/spelling lines up.

## Next steps
Once profile buckets and targets are derived, proceed to Phase 3: Video Filtering & Recommendation Engine to tie videos to the user.
