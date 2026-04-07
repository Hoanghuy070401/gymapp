# Phase 2: Filtering & Auto-Tagging Engine

[Parent Plan](./plan.md) | [Recommendation Engine Data Model](../2026-04-06-video-recommendation-engine/phase-01-data-model.md)

## Overview
**Date:** 2026-04-07  
**Created By:** AI Assistant  
**Description:** Implement algorithmic mapping to align WGER exercises with FitBody's personalized physiological demographic buckets, filter out broken videos, and push to Firebase.  
**Priority:** High  
**Status:** Pending  
**Review Status:** Draft  

## Requirements
- Silent rejection of YouTube videos where `status.embeddable == false`.
- Intelligent mapping logic based on keyword detection in exercise names/body parts.
- Push clean formatting (`WorkoutVideo` node structure) to Firebase Realtime Database.

## Implementation Steps
1. **Develop Filter Pipeline**:
   ```kotlin
   val safeVideos = youtubeResponses.filter { it.status.embeddable }
   ```
2. **Develop Tagging Algorithm**:
   - `targetAges`:
     - If exercise involves heavy joint load (e.g. "Deadlift", "Squat 1RM"): Exclude "12-17" and "50+". Assign "18-25", "26-35".
     - If exercise is "Mobility", "Stretching", or "Yoga": Assign "50+", "36-50", "All".
   - `targetGoals`:
     - Based on WGER `category` (e.g. Abs, Arms = Muscle Building; Cardio = Lose Weight).
   - `targetBMIs`:
     - Default to `["All"]`. Omit heavy bodyweight loads (e.g. "Pull-up") for "Obese" unless modified.
3. **Persist to Firebase**:
   - For each sanitized and tagged video, call `VideoRepository.addWorkoutVideo()`.
   - Ensure the generated ID maps correctly to the DB root structure.

## Success Criteria
- Running the Importer loop results in 100% playable videos appearing in the RTDB.
- Each video has the demographic `tags` field correctly pre-populated, integrating seamlessly with the Video Recommendation Engine.

## Related code files
- `BulkImporterService.kt` (New)
- `VideoRepository.kt` (Existing)

## Security Considerations
- The API Keys for YouTube must NOT be hardcoded in plain sight if the app is distributed. If the importer is run directly on device (Admin role), ensure `local.properties` holds the API key safely.
