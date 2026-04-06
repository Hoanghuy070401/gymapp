# Phase 2: Add Video Screen UI & Navigation

## Overview
**Date:** 2026-04-06  
**Description:** Build the `AddVideoScreen` composable and wire it to the `AppNavHost`, removing the temporary dialog from `MainScreen`.

## Requirements
- Render input forms: URL, Title, Duration, Level.
- Render advanced tags (Ages, Goals). Theo quyết định Interview, người dùng **BẰT BUỘC** phải chọn các tag này, không cho vượt qua nếu thiếu.
- Cài đặt `BackHandler`: Nếu người dùng bấm Back khi đang có dữ liệu draft, bắn Warning Dialog xác nhận "Bạn có chắc muốn thoát?". Ngược lại, dữ liệu tự auto-save draft trong ViewModel (Option A kết hợp C).
- Scaffold must include a `SnackbarHost` to display success/failure notifications.
- Collect `UiEvent` from `AddVideoViewModel` to trigger the Snackbar. If success, navigate `popBackStack()`.

## Related code files
- `AddVideoScreen.kt` (New)
- `MainScreen.kt` (Remove dialog, change FAB onClick to navigate to AddVideoScreen)
- `AppNavHost.kt` (or MainScreen inner NavHost, depending on where the route lives)

## Implementation Steps
1. Create `AddVideoScreen.kt`. Observe ViewModel state and `LaunchedEffect` for UI events.
2. In `LaunchedEffect(true)`, collect `viewModel.uiEvent`. On `Success`, show Snackbar and do `navController.popBackStack()`. On `Error`, show Snackbar.
3. Remove `AddVideoDialog.kt` and its bloated state from `HomeViewModel.kt`.
4. Register the new route in the navigation graph.

## Next steps
Review options in `plan.md` and approve execution.
