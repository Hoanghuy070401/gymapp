# Phase 1: Data Model & Firebase Node Design

## Overview
**Date:** 2026-04-06  
**Description:** Update Firebase Realtime Database schema and Kotlin models to support demographic targeted workout videos.

## Requirements
- Each video needs to declare who it is for: Target Ages, Target Goals, Target BMI (optional).
- Must explicitly support tags for "Seniors" (người lớn tuổi) and "Children" (trẻ em).

## Architecture: Firebase RTDB JSON Node Design

```json
"workoutVideos": {
  "-Oxxxxxx_autoId_xxxx": {
    "title": "Yoga phục hồi chức năng xương khớp",
    "youtubeUrl": "https://youtu.be/ugswyoxxi74",
    "description": "Cải thiện sự linh hoạt, giảm đau xương khớp.",
    "durationMinutes": 15,
    "level": "Beginner",
    "createdAt": 1712400000000,
    "tags": {
      "targetAges": ["50+"],                        // "12-17", "18-25", "26-35", "36-50", "50+", "All"
      "targetGoals": ["Get Fitter", "Flexibility"], // "Gain Weight", "Lose Weight", "Get Fitter", "Flexibility"
      "targetBMIs": ["All"]                         // "Underweight", "Normal", "Overweight", "Obese", "All"
    }
  },
  "-Oyyyyyy_autoId_yyyy": {
    "title": "Tập Core và Posture thanh niên",
    "youtubeUrl": "https://youtu.be/ugswyoxxi74",
    "description": "Cải thiện vóc dáng, phát triển cơ bắp.",
    "durationMinutes": 30,
    "level": "Intermediate",
    "createdAt": 1712400000000,
    "tags": {
      "targetAges": ["18-25", "26-35"],
      "targetGoals": ["Gain Weight", "Get Fitter"],
      "targetBMIs": ["Normal", "Underweight"]
    }
  }
}
```

## Related code files
- `WorkoutVideo.kt`
- `VideoRepository.kt`
- `AddVideoDialog.kt`

## Implementation Steps
1. Update `WorkoutVideo` data class to include a `tags` map or nested `VideoTags` class.
2. In `VideoRepository.addWorkoutVideo()`, accept lists of Target Ages and Target Goals.
3. Update `AddVideoDialog` UI to include multiselect filter chips for setting "Target Ages" (12-17, 18-25, 26-35, 36-50, 50+) and "Target Goals" khi upload video.

## Next steps
After completing Phase 1, proceed to Phase 2: Profile Logic.
