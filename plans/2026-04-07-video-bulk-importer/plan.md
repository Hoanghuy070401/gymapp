# Implementation Plan: Video Bulk Importer (WGER + YouTube API)

**Date:** 2026-04-07  
**Created By:** AI Assistant  
**Status:** Draft | Pending Review  
**Complexity:** Medium  
**Estimated Effort:** ~3 hours  

## Overview
This plan implements a "Hybrid Data Importer" to solve the quota constraints and terms of service limitations of ExerciseDB and YouTube's Data API.
It allows administrators to fetch base exercise data from the WGER API, search for tutorial videos using the YouTube Data API v3, silently filter out any videos where `embeddable == false`, and automatically apply demographic tags (Ages, BMIs, Levels, Goals) before saving the sanitized data to Firebase Realtime Database.

## Phasing Strategy

### [Phase 1: API Integration & Data Retrieval](./phase-01-api-integration.md)
- Set up WGER REST API client to fetch base exercises (name, target muscles).
- Set up YouTube Data API v3 client (`videos.list` and `search.list`) to find HD video tutorials matching WGER exercises.
- **Status:** DONE

### [Phase 2: Filtering & Auto-Tagging Engine](./phase-02-filtering-tagging.md)
- Implement `status.embeddable == true` filter to SILENTLY drop dead/restricted videos without throwing exceptions.
- Build the mapping function to apply the clinical recommendation criteria (e.g. "12-17", "50+") from the Video Recommendation Engine to the fetched videos based on parsed muscle groups/keywords.
- Save the final `WorkoutVideo` payload to Firebase.
- **Status:** Pending

## Success Criteria
- The database is populated automatically with safe-to-play videos.
- "Error 152" on the client side is virtually eliminated since non-embeddable videos are caught at insertion.
- Uploaded videos strictly adhere to the `tags` structure defined in the `2026-04-06-video-recommendation-engine` plan.
