# Phase 04 — Resources & Favorites

**Plan:** [plan.md](plan.md)
**Parallelization:** ✅ Chạy song song với Phase 02, 03, 05 (sau Phase 01)
**Priority:** Medium
**Status:** Todo

## Tổng quan

Triển khai tab **Resources** (11.2) và tab **Favorites** (11.3) trong Bottom Navigation Bar, cùng màn hình phụ **Support & Help** (11.4).

## Tính năng cần triển khai

**11.2 Resources:**
- 11.2.1 Articles & Tips → danh sách bài viết, filter theo category
- 11.2.2 Workout Videos → danh sách video (embed YouTube hoặc thumbnail)

**11.3 Favorites:**
- 11.3.1 All — tất cả mục đã yêu thích
- 11.3.2 Video — lọc: chỉ video
- 11.3.3 Article — lọc: chỉ bài viết

**10. Articles & Tips** (section trên Home) → tái dụng ArticleCard

**11.4 Support & Help:**
- 11.4.1 Help Center → FAQ accordion list
- 11.4.2 Online Support → form liên hệ / chat link

## Kiến trúc

```
feature_home/
├── presentation/
│   ├── HomeScreen.kt          (Phase 06 owns)
│   ├── resources/
│   │   ├── ResourcesScreen.kt     ← NEW
│   │   ├── ArticlesScreen.kt      ← NEW
│   │   ├── ArticleDetailScreen.kt ← NEW
│   │   ├── WorkoutVideosScreen.kt ← NEW
│   │   └── ResourcesViewModel.kt  ← NEW
│   ├── favorites/
│   │   ├── FavoritesScreen.kt     ← NEW
│   │   └── FavoritesViewModel.kt  ← NEW
│   └── support/
│       ├── SupportScreen.kt       ← NEW
│       ├── HelpCenterScreen.kt    ← NEW
│       └── OnlineSupportScreen.kt ← NEW
```

## File Ownership

- `feature_home/src/main/java/com/gym/feature/home/presentation/resources/*` ← NEW
- `feature_home/src/main/java/com/gym/feature/home/presentation/favorites/*` ← NEW
- `feature_home/src/main/java/com/gym/feature/home/presentation/support/*` ← NEW

## Data Models

```kotlin
// domain/model/Article.kt (NEW)
data class Article(
    val id: String,
    val title: String,
    val category: String,       // "Nutrition" | "Training" | "Recovery"
    val readTimeMinutes: Int,
    val thumbnailUrl: String?,
    val content: String,
    val isFavorite: Boolean = false
)

// domain/model/WorkoutVideo.kt (NEW)
data class WorkoutVideo(
    val id: String,
    val title: String,
    val durationSeconds: Int,
    val thumbnailUrl: String?,
    val youtubeId: String?,
    val isFavorite: Boolean = false
)
```

## Favorites Storage

Lưu favorites vào Room database với local table:
```kotlin
// data/local/FavoriteEntity.kt (NEW)
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val type: String,   // "article" | "video"
    val title: String,
    val savedAt: Long
)
```

## Bước triển khai

### Bước 1: Domain models
- Tạo `Article.kt`, `WorkoutVideo.kt` trong `domain/model/`
- Tạo `ArticleRepository.kt` interface

### Bước 2: Data layer
- Tạo `FavoriteEntity.kt` + `FavoriteDao.kt` trong `data/local/`
- Thêm `FavoriteDao` vào `GymDatabase`

### Bước 3: ResourcesScreen
- Tab nội bộ: Articles | Videos
- `ArticleCard` (tái dụng pattern từ HomeScreen)
- `VideoCard` với thumbnail + thời lượng overlay

### Bước 4: FavoritesScreen
- Filter chips: All | Video | Article
- LazyColumn danh sách (swipe-to-remove)

### Bước 5: ArticleDetailScreen
- Hero image, title, category badge, nội dung Markdown/Text
- FAB bookmark icon để toggle favorite

### Bước 6: SupportScreen → HelpCenter + OnlineSupport

## Todo List

- [ ] Tạo `Article.kt`, `WorkoutVideo.kt` (domain)
- [ ] Tạo `FavoriteEntity.kt` + `FavoriteDao.kt` (data)
- [ ] Thêm FavoriteDao vào GymDatabase
- [ ] Tạo `ResourcesScreen.kt` + `ResourcesViewModel.kt`
- [ ] Tạo `ArticlesScreen.kt` + `ArticleDetailScreen.kt`
- [ ] Tạo `WorkoutVideosScreen.kt`
- [ ] Tạo `FavoritesScreen.kt` + `FavoritesViewModel.kt`
- [ ] Tạo `SupportScreen.kt` + `HelpCenterScreen.kt` + `OnlineSupportScreen.kt`
- [ ] Seed 5 articles, 3 workout videos (static data)

## Success Criteria

- ✅ Resources tab hiển thị articles và videos
- ✅ Bookmark article → xuất hiện ở Favorites tab
- ✅ Filter Favorites hoạt động (All / Video / Article)
- ✅ Swipe-to-remove xóa khỏi favorites

## Conflict Prevention

Phase này KHÔNG sửa `HomeScreen.kt` (thuộc Phase 06), `WorkoutScreen.kt` (Phase 02), hay bất kỳ file auth nào.
