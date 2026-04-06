# Implementation Plan: Video Recommendation Engine

**Date:** 2026-04-06  
**Created By:** AI Assistant  
**Status:** Draft  
**Complexity:** Medium  
**Estimated Effort:** ~4 hours  

## Overview
Based on user profile data collected during setup (height, weight, dateOfBirth, gender, goal, activityLevel), we need to design a structured data schema and logic engine to recommend personalized workout videos. The system must also correctly suggest specific videos for the **Elderly (Người lớn tuổi)** and **Children (Trẻ em)**.

## Phases

### [Phase 1: Data Model & Firebase Node Design](./phase-01-data-model.md)
- Design the `tags` sub-node within `workoutVideos/{id}`.
- Update `WorkoutVideo` domain class to support target tags.
- Update `AddVideoDialog` and `VideoRepository` to include Age and Goal tags upon upload.
- **Status**: Pending

### [Phase 2: Profile Logic & BMI/Age Calculation](./phase-02-profile-logic.md)
- Implement Kotlin utility logic to calculate `Age` from `dateOfBirth` ("dd/MM/yyyy").
- Implement `BMI` calculation from `heightCm` and `weightKg`.
- Classify users into buckets: 
  - Age: "Children" (< 15), "Adults" (15-55), "Seniors" (> 55)
  - BMI: "Underweight", "Normal", "Overweight", "Obese"
- **Status**: Pending

### [Phase 3: Video Filtering & Recommendation Engine](./phase-03-recommendation-engine.md)
- Implement scoring/filtering logic in `HomeViewModel` (or `GetRecommendedVideosUseCase`).
- Rank videos by calculating a match score between User Profile buckets and Video Tags.
- Expose personalized `<WorkoutVideo>` lists for `HomeViewModel`.
- **Status**: Pending

---
*Please review this plan. Upon approval, we can start executing Phase 1.*
