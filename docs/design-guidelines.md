# Hướng dẫn thiết kế (Design Guidelines)

## Documentation Maintenance
**Last Updated:** 2026-04-06
**Document Version:** 1.2
**Maintained By:** Development Team

---

## Design Philosophy — "Kinetic Editorial"

FitBody sử dụng design language "Kinetic Editorial" — sự kết hợp giữa editorial typography, bold black backgrounds, và accent màu neon. Cảm giác: premium fitness magazine meets modern dark UI.

**Nguyên tắc:**
- **Bold & High Contrast**: Dark background + ElectricLime accent
- **Editorial Typography**: Headline-driven, bold weights
- **Kinetic Energy**: Layout tạo cảm giác chuyển động và năng lượng
- **Space & Intentionality**: Generous whitespace, purposeful layout

---

## Design Tokens

### Color System

| Token | Value | Ứng dụng |
|-------|-------|---------|
| `ElectricLime` | `#C9FF4F` | Primary accent; CTA buttons, highlights, active indicators |
| `Surface` | `#0E0E0E` | App background (near-black) |
| `SurfaceContainerHigh` | dark grey (~`#1C1C1C`) | Card backgrounds, input fields |
| `TonalLavender` | purple tone (~`#9E8EBF`) | Secondary accent; social forms, WeeklyChallenge card bg |
| `PrimaryKinetic` | (`AppColors.PrimaryKinetic`) | Avatar gradient start, button fill |
| `OnSurface` | white/light | Primary text on dark bg |
| `OnSurfaceVariant` | muted white | Secondary / helper text |
| `Error` | red tone | Error messages, validation indicators |

**Rules:**
- KHÔNG hardcode hex trực tiếp trong Composables — luôn dùng `AppColors.*`
- Ngoại lệ được phép: một-hai hardcoded màu nền trong gradient list(VD: `RecommendedCard`)

### Typography Scale

| Style | Ứng dụng |
|-------|---------|
| `headlineLarge` | Screen titles (Welcome, Let's Start!) |
| `headlineSmall` | Dashboard greeting ("Hi, username") |
| `titleLarge` | Screen top bar title |
| `titleMedium` | Card titles, section headings |
| `bodyLarge` | Form fields, primary body text |
| `bodyMedium` | Field labels |
| `bodySmall` | Helper text, subtitles, links |
| `labelMedium` | Error messages, tags, chip text, meta info |

**Font family:** Android Roboto (system default). Không dùng custom fonts ngoài hệ thống.

**Font weights hay dùng:** `FontWeight.Bold` cho headlines/CTAs, `FontWeight.SemiBold` cho card titles, `FontWeight.Normal` cho body.

### Spacing System

| Token | Value | Ứng dụng |
|-------|-------|---------|
| `ScreenHorizontal` | 20dp | Horizontal screen edge padding |
| `Medium` | 12dp | Padding bên trong components |
| `Large` | 24dp | Khoảng cách giữa các sections |
| `ExtraLarge` | 32dp | Bottom safe area spacing |

**Rule:** Chỉ dùng `AppSpacing.*` — không hardcode dp giá trị lẻ (ngoại lệ: các icon size đặc thù).

### Shape System

| Token | Ứng dụng |
|-------|---------|
| `AppShape.Small` | Tags, chips |
| `AppShape.Medium` | TextFields, input containers |
| `AppShape.Large` | Cards (GymCard, RecommendedCard, ArticleCard) |
| `CircleShape` | Avatar, icon buttons |

---

## Component Specifications

### GymButton

| Variant | Style | Ứng dụng |
|---------|-------|---------|
| GHOST | Outline ElectricLime border + transparent bg | Hầu hết CTA buttons |
| PRIMARY | Solid ElectricLime fill | Secondary actions, confirmation |

**Sizing:**
- `fillMaxWidth(0.65f)` — centered CTA (standard)
- `fillMaxWidth()` — full-width CTA (auth screens, setup)

### GymTextField

- Background: `SurfaceContainerHigh` với `AppShape.Medium` radius
- Bottom indicator: 2dp line, `PrimaryKinetic` (default) / `Error` (error state)
- Error message: `labelMedium` style, `Error` color, `AnimatedVisibility`
- Password fields: `PasswordVisualTransformation` + toggle icon

### GymScaffold

- Background: `Surface` (`#0E0E0E`)
- Top padding: 48dp (safe area for status bar)
- Optional `scrollable: Boolean` parameter

### GymAvatar

- Size: 120dp circle
- Default state: gradient `PrimaryKinetic → TonalLavender`
- Image loaded: `ContentScale.Crop` clipped to circle
- Edit badge: 32dp ElectricLime circle, bottom-right corner

### GymCard

- Background: customizable `containerColor` (default `SurfaceContainerHigh`)
- Shape: `AppShape.Large`
- Padding: `AppSpacing.Medium`

### StepProgressBar

- Dùng trong Setup Wizard để hiển thị tiến trình 8 bước
- Active step: ElectricLime indicator

### StatCard

Hiển thị số liệu (stats) với title + value. Dùng trong Home Dashboard (future).

---

## Screen Layout Pattern

```
GymScaffold (root wrapper)
├── TopBar (Row - SpaceBetween)
│   ├── Back button "◀" (ElectricLime, clickable)
│   ├── Screen title (titleLarge, Bold, ElectricLime, center)
│   └── Invisible spacer (cùng width với back button)
│
└── Content (Column + verticalScroll nếu scrollable=true)
    ├── Spacer(32dp)
    ├── Headline text (headlineLarge)
    ├── Subtitle (bodySmall, TonalLavender)
    ├── Spacer(48dp)
    ├── Form fields / Content
    ├── Error message (bodySmall, Error color) — AnimatedVisibility
    ├── Spacer(weight=1f)  ← pushes button to bottom
    └── [Optional] Bottom link text
 
CTA Button (Row, full-width or 65%)
```

---

## Auth Screens Pattern

- TonalLavender block bao quanh SignUp form container.
- ElectricLime dùng cho interactive text links ("Đăng Ký", "Đăng Nhập", "Quên mật khẩu").
- Social icon circles: size 44dp, `TonalLavender` bg + border.
- "Touched pattern" cho validation: chỉ hiển thị error sau khi user tương tác với field.

---

## Onboarding Screen Pattern

- Full-screen background photos (`ContentScale.Crop`).
- Black gradient overlay từ dưới lên (che nội dung text).
- ElectricLime page indicator dots.
- `HorizontalPager` custom style.

---

## Setup Wizard Pattern

**8 bước:** Intro → Gender → Age → Weight → Height → Goal → Activity Level → Fill Profile

- Top bar: back `◀` left + step name center (ElectricLime, Bold).
- Bottom: `GhostButton` full-width ("Continue" / "Next" / "Start").
- Step contents: pill selectors, scroll pickers, form fields, avatar upload.
- `StepProgressBar` ở top để track tiến trình.

---

## Home Dashboard Pattern

- Header: greeting text (headlineSmall, ElectricLime) + action icon buttons (36dp circles, SurfaceContainerHigh bg).
- Category grid: 2×2 layout.
- Content sections: `SectionHeader` + horizontal scroll rows.
- Cards: gradient background (multiple color pairs), bottom-aligned text.
- Weekly Challenge: `GymCard` với `TonalLavender` bg.

---

## Icon Guidelines

- Dùng `androidx.compose.material.icons.Icons.Default.*` cho standard icons.
- Tint color: `AppColors.OnSurface` (default), `AppColors.ElectricLime` (accent/active).
- Icon button container: 36dp circle, `SurfaceContainerHigh` background.

---

## Animation Guidelines

- Form errors: `AnimatedVisibility` (fade + expand)
- Navigation transitions: default Compose Navigation (fade)
- Loading: `CircularProgressIndicator` với `color = AppColors.ElectricLime`
- Splash: 1.5s delay → navigate (không có animation phức tạp)
