# Implementation Plan: Post-SignUp Navigation Flow

**Date:** 2026-04-03
**Created By:** Nguyen Huy <huy.nh@ots.vn>
**Status:** IN_PROGRESS
**Complexity:** Medium
**Estimated Effort:** 2–3 hours

## Summary

Sau khi đăng ký thành công, điều hướng người dùng qua Setup Wizard (8 bước) rồi đến Home. Firebase sync ở bước cuối.

## Flow

```
SignUp → [Firebase Auth] → Screen.Setup (bước 0→7) → [Firebase RTDB sync] → Screen.Home
```

## Phases

| # | Phase | Status |
|---|-------|--------|
| 01 | Implement Firebase sync in SetupViewModel | IN_PROGRESS |
| 02 | Smart Splash routing (skip setup nếu đã done) | Todo |

## Links

- [phase-01-setup-firebase-sync.md](phase-01-setup-firebase-sync.md)
- [phase-02-splash-routing.md](phase-02-splash-routing.md)
