# Phase 1: Dedicated View Model & State (Approach A)

## Overview
**Date:** 2026-04-06  
**Description:** Decouple video addition logic from `HomeViewModel` into a dedicated `AddVideoViewModel`.

## Requirements
- Provide strict validation for YouTube URLs.
- Manage form states (Title, Duration, Goals, Ages).
- Trigger a UI Event (e.g., `AddVideoUiEvent.ShowSnackbar(message)`) strictly once per action to prevent duplicate snackbars on recomposition.

## Architecture
- **StateFlow**: To hold form fields.
- **Channel<AddVideoUiEvent>**: To send one-time success/failure triggers to the Compose UI.

```kotlin
sealed class AddVideoUiEvent {
    data class Success(val message: String) : AddVideoUiEvent()
    data class Error(val message: String) : AddVideoUiEvent()
}
```

## Related code files
- `AddVideoViewModel.kt` (New)
- `VideoRepository.kt`

## Next steps
Once state management is reliable, proceed to build the UI that consumes these states.
