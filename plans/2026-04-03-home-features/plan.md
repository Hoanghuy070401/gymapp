# Implementation Plan: Home Features

**Date:** 2026-04-03
**Status:** Draft
**Complexity:** High
**Estimated Effort:** 8–10 ngày dev

## Tổng quan (Overview)

Triển khai toàn bộ hệ thống tính năng màn hình Home theo sơ đồ chức năng đã xác định, bao gồm Bottom Navigation Bar, Floating Menu, các tab Top Menu (Workout / Progress / Nutrition / Community), màn hình phụ liên quan, và các tiện ích chung (Search, Notifications, Favorites, Resources).

## Phạm vi tính năng (Feature Scope)

| Nhóm | Tính năng |
|------|-----------|
| **Bottom Nav (11)** | Home, Resources, Favorite, Support & Help |
| **Home Header** | Greeting, Search, Notifications, Profile avatar |
| **Category Grid (7)** | Workout, Progress Tracking, Nutrition, Community |
| **Home Sections** | Recommended Sessions (8), Weekly Challenge (9), Articles & Tips (10) |

## Sơ đồ phụ thuộc (Dependency Graph)

```
Phase 01 ─────────────────────────────────────────── (Foundation)
  └─ Navigation + Screen routes + BottomNavBar shell

Phase 02 ── Phase 03 ── Phase 04 ── Phase 05 ─────── (Parallel)
  └─ Workout   └─ Profile    └─ Resources   └─ Progress/Nutrition
     & Search     & Floating    & Favorites    & Community
                  Menu

Phase 06 ─────────────────────────────────────────── (Integration)
  └─ Home Dashboard kết nối tất cả sections + Firebase data
```

**Chiến lược thực thi:**
- Phase 01 phải chạy trước (cơ sở navigation)
- Phase 02–05 chạy **song song** (parallel), mỗi phase sở hữu module riêng
- Phase 06 chạy sau khi 02–05 hoàn thành

## File Ownership Matrix

| File / Module | Phase |
|---------------|-------|
| `Screen.kt`, `AppNavHost.kt`, Bottom Nav | 01 |
| `feature_workout/*` | 02 |
| `feature_auth/Profile*`, Floating Menu | 03 |
| `feature_home/resources/*`, Favorites | 04 |
| Progress, Nutrition, Community screens | 05 |
| `feature_home/HomeScreen.kt` (data connect) | 06 |

## Danh sách Phase

- [Phase 01](phase-01-navigation-foundation.md) — Navigation + Bottom Nav Bar
- [Phase 02](phase-02-workout-search.md) — Workout Module + Search
- [Phase 03](phase-03-profile-floating-menu.md) — Profile + Floating Menu
- [Phase 04](phase-04-resources-favorites.md) — Resources + Favorites
- [Phase 05](phase-05-progress-nutrition-community.md) — Progress / Nutrition / Community
- [Phase 06](phase-06-home-integration.md) — Home Dashboard Integration
