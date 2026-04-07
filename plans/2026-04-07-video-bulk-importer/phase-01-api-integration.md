# Phase 1: API Integration & Data Retrieval

[Parent Plan](./plan.md) | [Recommendation Engine Data Model](../2026-04-06-video-recommendation-engine/phase-01-data-model.md)

## Overview
**Date:** 2026-04-07  
**Created By:** AI Assistant  
**Description:** Initialize network clients for WGER API and YouTube Data API v3 to act as the primary sources for the workout video curriculum.  
**Priority:** High  
**Status:** Pending  
**Review Status:** Draft  

## Key Insights
- WGER provides unrestricted access to community-driven exercise metadata (AGPL-3.0), which serves as our free foundation.
- YouTube Data API v3 enforces a 10,000 unit daily limit. Fetching `search` costs 100 units, while `videos.list` costs 1 unit. We must optimize logic to minimize quota burn.

## Requirements
- HTTP Client (Retrofit/Ktor) setup for base URL `https://wger.de/api/v2/`.
- HTTP Client setup for `https://www.googleapis.com/youtube/v3/`.
- Configuration holder for `YOUTUBE_API_KEY`.

## Architecture
- `WgerRepository`: Handles paginated fetching of `/exerciseinfo`.
- `YoutubeRepository`: Handles `/search` to find matching tutorial vids, then `/videos` to get `contentDetails` and `status.embeddable`.

## Implementation Steps
1. Create data models matching the WGER JSON structure (id, name, target muscles).
2. Create data models matching YouTube API responses (VideoItem, Snippet, ContentDetails, Status).
3. Draft the integration logic:
   - Call WGER for a batch of exercises (e.g. 50 items).
   - For each exercise, call YouTube `/search` using `q = "[Exercise Name] tutorial form"`.
   - Take the top 1-2 results and call YouTube `/videos` with `id={vid}` to get precise runtimes and embed status.

## Risk Assessment
- **Rate Limiting:** High risk of burning through the 10,000 YouTube quota if the script loops too aggressively over thousands of WGER records.
- **Mitigation:** Only run this synchronization manually from a developer endpoint or Admin Activity. Batch limit to 10-20 exercises per run.

## Next steps
Proceed to Phase 2: Filtering & Auto-Tagging Engine to sanitize and upload the merged data.
