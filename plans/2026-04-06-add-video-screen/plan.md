# Implementation Plan: Add Video Screen

**Date:** 2026-04-06  
**Created By:** AI Assistant  
**Status:** Completed ✅  

**Complexity:** Low  
**Estimated Effort:** ~2 hours  

## Overview
The goal is to design a feature allowing authorized users (or admins) to add new video records to Firebase and show immediate success/failure notifications to the user interface. Per request, we are providing two structural approaches (Dedicated Screen vs Overlay Dialog) for review.

## Architecture Approaches

### Approach A: Dedicated Full-Screen (`AddVideoScreen`)
A new top-level composable screen added to `AppNavHost` or `MainScreen`'s inner `NavHost`.
- **Pros:** 
  - Complete separation of concerns: It has its own `AddVideoViewModel`, independent states, and does not bloat `HomeScreen`.
  - More real estate: Easier to add complex forms (e.g. multi-select for target ages/goals, uploading thumbnails).
  - Clean error handling with its own `SnackbarHost`.
- **Cons:**
  - Requires navigating away from the Home view.
  - Slower UX for bulk-adding videos.

### Approach B: Overlay Dialog / Modal Bottom Sheet (Current Setup)
An `AddVideoDialog` displayed on top of the `HomeScreen`, managed by `HomeViewModel`.
- **Pros:**
  - Extremely fast UX: User stays on the Home page and can immediately see the new video appear behind the dialog.
  - Less context switching.
- **Cons:**
  - `HomeViewModel` becomes bloated with form-handling states (loading, errors, success events).
  - Constrained UI space for complex tags/categories.

### 🏆 Recommended Approach
**Approach A (Dedicated Screen)** is highly recommended if we are about to add detailed categorizations (Age buckets, BMI mapping, Goal targets) as outlined in the Video Recommendation Engine plan. The form will get too complex for a simple pop-up dialog.

## Implementation Phases

### [Phase 1: Dedicated View Model & State](./phase-01-add-video-viewmodel.md)
- Create `AddVideoViewModel` to manage form state and Firebase upload logic.
- Implement UiEvents (Kotlin Channels/SharedFlow) to trigger one-shot Snackbar events (Success/Failure).
- **Status**: ✅ DONE — 2026-04-06

### [Phase 2: AddVideoScreen UI & Navigation](./phase-02-add-video-ui.md)
- Design the `AddVideoScreen` with multiselect chips (Ages, Goals).
- Add `SnackbarHost` directly in the Scaffold for the screen.
- Wire navigation from the FAB in `MainScreen` to navigate to `Screen.AddVideo.route`.
- **Status**: ✅ DONE — 2026-04-06

---
*Please review the two approaches above. If you approve the recommendation for **Approach A**, we can proceed with execution.*
