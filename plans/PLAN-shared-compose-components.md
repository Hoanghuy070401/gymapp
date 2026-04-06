# Shared Compose UI Components

## Goal
Tạo bộ UI components dùng chung trong `core/designsystem/component/` để tất cả feature modules tái sử dụng, tránh code trùng lặp và đảm bảo nhất quán theo Kinetic Archive Design System.

## Phân tích Duplicate hiện tại

Sau khi review 3 screens (`HomeScreen`, `SetupScreen`, `WorkoutScreen`), phát hiện các pattern trùng lặp:

| Pattern | Xuất hiện tại | Sẽ trở thành |
|---------|--------------|--------------|
| Surface card (background + shape + padding) | Home, Setup, Workout | `GymCard` |
| Section header (title + SemiBold + OnSurface) | Home (5 lần) | `SectionHeader` |
| Stat card (value + label) | Home | `StatCard` |
| Progress ring (Canvas arc) | Home | `ProgressRing` |
| Loading spinner (fullscreen center) | Workout | `LoadingContent` |
| Empty state (center text) | Workout | `EmptyContent` |
| Avatar circle (gradient + initial) | Home | `GymAvatar` |
| Screen scaffold (surface + scroll + padding) | Home, Setup, Workout | `GymScaffold` |
| Bottom-heavy input (2px primary focus bar) | Chưa có, DESIGN.md yêu cầu | `GymTextField` |
| Primary/Ghost/Lime buttons | Chưa có, DESIGN.md yêu cầu | `GymButton` |

## Tasks

- [ ] **T1:** Tạo `GymCard.kt` → Surface card container (No-Line rule, tonal layering)
- [ ] **T2:** Tạo `SectionHeader.kt` → Title với optional trailing action
- [ ] **T3:** Tạo `StatCard.kt` → Value/label card (highlight variant)
- [ ] **T4:** Tạo `ProgressRing.kt` → Canvas progress ring (ElectricLime trên sunken track)
- [ ] **T5:** Tạo `LoadingContent.kt` + `EmptyContent.kt` → State handlers
- [ ] **T6:** Tạo `GymAvatar.kt` → Gradient circle + initial letter
- [ ] **T7:** Tạo `GymScaffold.kt` → Screen wrapper (surface background + safe area + scroll)
- [ ] **T8:** Tạo `GymTextField.kt` → Bottom-heavy input (DESIGN.md §5 Inputs)
- [ ] **T9:** Tạo `GymButton.kt` → 3 variants: Primary (purple), HighAlert (lime), Ghost (outline 20%)
- [ ] **T10:** Refactor `HomeScreen`, `WorkoutScreen`, `SetupScreen` → dùng shared components
- [ ] **T11:** Build & verify

## Chi tiết file

### Component Path: `core/src/main/java/com/gym/core/designsystem/component/`

#### [NEW] `GymCard.kt`
```kotlin
// Surface card: SurfaceContainerLow → Large corners → padding
// Params: modifier, surfaceColor, shape, content
```

#### [NEW] `SectionHeader.kt`
```kotlin
// Title text + optional trailing @Composable (e.g., "See All")
// Params: title, modifier, trailingContent
```

#### [NEW] `GymButton.kt`
```kotlin
// 3 sealed variants: Primary, HighAlert, Ghost
// Corner radius = xl (24dp), DESIGN.md §5
```

#### [NEW] `GymTextField.kt`
```kotlin
// Bottom-heavy: surface-container-highest bg + 2px primary bottom-bar on focus
// DESIGN.md §5 Inputs
```

#### [NEW] `ProgressRing.kt`
```kotlin
// Canvas arc, track = SurfaceContainerHigh, fill = ElectricLime
// Params: progress (0f..1f), size, strokeWidth, trackColor, progressColor
```

#### [NEW] `GymAvatar.kt`
```kotlin
// Circular gradient background (Primary → Lavender) + centered initial
// Params: initial, size, gradientColors
```

#### [NEW] `GymScaffold.kt`
```kotlin
// fillMaxSize + Surface bg + verticalScroll + safe padding
// Params: modifier, scrollable, content
```

#### [NEW] `LoadingContent.kt` + `EmptyContent.kt`
```kotlin
// Centered loading spinner (ElectricLime) / Empty message
```

#### [NEW] `StatCard.kt`
```kotlin
// Value + label, optional highlight (ElectricLime vs PrimaryKinetic)
```

## Verification

### Build Check
- Chạy build debug trên Android Studio: `./gradlew assembleDebug`
- Tất cả modules phải pass compilation

### Manual Check (yêu cầu user)
- Mở app trên emulator/device → xác nhận HomeScreen vẫn hiển thị đúng sau refactor
- Kiểm tra WorkoutScreen + SetupScreen vẫn render bình thường

## Done When
- [ ] 10 shared components trong `core/designsystem/component/`
- [ ] 3 screens đã refactor dùng shared components
- [ ] Build pass không lỗi
