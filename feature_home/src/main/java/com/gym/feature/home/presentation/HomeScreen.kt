package com.gym.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.gym.core.designsystem.component.GymCard
import com.gym.core.designsystem.component.SectionHeader
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.WorkoutVideo
import com.gym.feature.home.data.YoutubeThumbnailQuality
import com.gym.feature.home.data.youtubeThumbnailUrl
import com.gym.feature.home.presentation.categories.HomeCategoryGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToProgress: () -> Unit = {},
    onNavigateToNutrition: () -> Unit = {},
    onNavigateToCommunity: () -> Unit = {},
    onNavigateToVideo: (WorkoutVideo) -> Unit = {}
) {
    val uiState by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.refresh() },
        state = pullRefreshState,
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Surface)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Surface),
            contentPadding = PaddingValues(top = 48.dp, bottom = 100.dp)
        ) {
            // ── Header ──────────────────────────────────────────────────────
            item(key = "header") {
                HomeHeaderSection(
                    userName = uiState.userName,
                    onSearchClick = onNavigateToSearch,
                    onNotificationsClick = onNavigateToNotifications,
                    onProfileClick = onNavigateToProfile
                )
                Spacer(modifier = Modifier.height(AppSpacing.Large))
            }

            // ── Category Grid ────────────────────────────────────────────
            item(key = "categories") {
                HomeCategoryGrid(
                    onWorkoutClick = onNavigateToWorkout,
                    onProgressClick = onNavigateToProgress,
                    onNutritionClick = onNavigateToNutrition,
                    onCommunityClick = onNavigateToCommunity
                )
                Spacer(modifier = Modifier.height(AppSpacing.Large))
            }

            if (uiState.isLoading) {
                item(key = "loading") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppSpacing.Large),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppColors.ElectricLime)
                    }
                }
            } else {
                // ── Recommended Sessions ─────────────────────────────────
                item(key = "recommendations") {
                    RecommendedSection(
                        videos = uiState.workoutVideos,
                        routines = uiState.recommendedRoutines,
                        onVideoClick = onNavigateToVideo,
                        onSeeAll = {}
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.Large))
                }

                // ── Workout Videos (YouTube) ─────────────────────────────
                if (uiState.workoutVideos.isNotEmpty()) {
                    item(key = "videos") {
                        WorkoutVideosSection(
                            videos = uiState.workoutVideos,
                            onVideoClick = onNavigateToVideo,
                            onSeeAll = {}
                        )
                        Spacer(modifier = Modifier.height(AppSpacing.Large))
                    }
                }

                // ── Weekly Challenge ────────────────────────────────────
                uiState.activeChallenge?.let { challenge ->
                    item(key = "challenge") {
                        WeeklyChallengeSection(challenge = challenge)
                        Spacer(modifier = Modifier.height(AppSpacing.Large))
                    }
                }

                // ── Articles & Tips ─────────────────────────────────────
                if (uiState.recentArticles.isNotEmpty()) {
                    item(key = "articles") {
                        ArticlesSection(
                            articles = uiState.recentArticles,
                            onSeeAll = {}
                        )
                    }
                }
            }  // end else
        }  // end LazyColumn
    }  // end PullToRefreshBox
}  // end HomeScreen

// ─── Header ────────────────────────────────────────────────────────────────

@Composable
private fun HomeHeaderSection(
    userName: String,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            val greeting = if (userName.isNotBlank()) "Hi, $userName 👋" else "Hi there 👋"
            Text(
                text = greeting,
                style = AppTypography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TonalLavender
                )
            )
            Text(
                text = "It's time to challenge your limits.",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            HeaderIconButton(onClick = onSearchClick) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = AppColors.OnSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
            HeaderIconButton(onClick = onNotificationsClick) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = AppColors.OnSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
            HeaderIconButton(onClick = onProfileClick) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = AppColors.OnSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun HeaderIconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(AppColors.SurfaceContainerHigh)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = { content() }
    )
}

// ─── Recommended (with real images) ────────────────────────────────────────

@Composable
private fun RecommendedSection(
    videos: List<WorkoutVideo>,
    routines: List<RecommendedRoutine>,
    onVideoClick: (WorkoutVideo) -> Unit,
    onSeeAll: () -> Unit
) {
    Column {
        SectionHeader(title = "Recommendations", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.TonalLavender,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        // Use LazyRow for performance
        LazyRow(
            contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // If we have videos, show video cards (real thumbnails)
            if (videos.isNotEmpty()) {
                items(videos, key = { it.id }) { video ->
                    RecommendedVideoCard(
                        video = video,
                        onClick = { onVideoClick(video) }
                    )
                }
            } else {
                // Fallback gradient cards when no videos yet
                items(routines, key = { it.id }) { routine ->
                    RecommendedGradientCard(routine = routine)
                }
            }
        }
    }
}

/** Recommended card with real YouTube thumbnail — matches design spec */
@Composable
private fun RecommendedVideoCard(
    video: WorkoutVideo,
    onClick: () -> Unit
) {
    val thumbnailUrl = remember(video.videoId) {
        video.videoId?.let { youtubeThumbnailUrl(it, YoutubeThumbnailQuality.HIGH) }
    }

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .clip(AppShape.Large)
            .clickable(onClick = onClick)
    ) {
        // Real YouTube thumbnail
        if (thumbnailUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(model = thumbnailUrl),
                contentDescription = video.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                AppColors.PrimaryKinetic,
                                AppColors.TonalLavender
                            )
                        )
                    )
            )
        }

        // Gradient overlay darkening bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )

        // Star icon top-right
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AppColors.TonalLavender,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp)
        )

        // Play button center
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(AppColors.TonalLavender.copy(alpha = 0.9f))
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        // Title + meta at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = video.title,
                style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "▶ ${video.durationMinutes} Mins",
                    style = AppTypography.labelSmall.copy(fontSize = 9.sp),
                    color = Color.White.copy(alpha = 0.85f)
                )
                if (video.durationMinutes > 0) {
                    Text(
                        text = "🔥 ${video.durationMinutes * 10} Kcal",
                        style = AppTypography.labelSmall.copy(fontSize = 9.sp),
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

/** Fallback gradient card when no YouTube videos available */
@Composable
private fun RecommendedGradientCard(routine: RecommendedRoutine) {
    val gradients = remember {
        listOf(
            listOf(AppColors.PrimaryKinetic, AppColors.TonalLavender),
            listOf(AppColors.TonalLavender, AppColors.PrimaryKinetic),
            listOf(Color(0xFF1B5E20), Color(0xFF4CAF50)),
            listOf(Color(0xFF4A148C), Color(0xFF7B1FA2))
        )
    }
    val gradientColors = gradients[(routine.id.hashCode() and 0xFF) % gradients.size]

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .clip(AppShape.Large)
            .background(Brush.linearGradient(gradientColors))
            .padding(AppSpacing.Medium),
        contentAlignment = Alignment.BottomStart
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AppColors.OnSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.TopEnd)
        )
        Column {
            Text(
                text = routine.title,
                style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    "▶ ${routine.durationMinutes} Mins",
                    style = AppTypography.labelSmall.copy(fontSize = 9.sp),
                    color = AppColors.OnSurface
                )
                Text(
                    "🔥 ${routine.calories} Kcal",
                    style = AppTypography.labelSmall.copy(fontSize = 9.sp),
                    color = AppColors.OnSurface
                )
            }
        }
    }
}

// ─── Workout Videos (YouTube) ──────────────────────────────────────────────

@Composable
internal fun WorkoutVideosSection(
    videos: List<WorkoutVideo>,
    onVideoClick: (WorkoutVideo) -> Unit,
    onSeeAll: () -> Unit
) {
    Column {
        SectionHeader(title = "Workout Videos", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.TonalLavender,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        LazyRow(
            contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(videos, key = { "vid_${it.id}" }) { video ->
                VideoCard(video = video, onClick = { onVideoClick(video) })
            }
        }
    }
}

@Composable
private fun VideoCard(video: WorkoutVideo, onClick: () -> Unit) {
    val thumbnailUrl = remember(video.videoId) {
        video.videoId?.let { youtubeThumbnailUrl(it, YoutubeThumbnailQuality.HIGH) }
    }

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .clip(AppShape.Large)
            .clickable(onClick = onClick)
    ) {
        if (thumbnailUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(model = thumbnailUrl),
                contentDescription = video.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                AppColors.PrimaryKinetic,
                                AppColors.TonalLavender
                            )
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f))
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = video.title,
                style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (video.durationMinutes > 0) {
                Text(
                    text = "⏱ ${video.durationMinutes} phút · ${video.level}",
                    style = AppTypography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ─── Weekly Challenge ──────────────────────────────────────────────────────

@Composable
private fun WeeklyChallengeSection(challenge: WeeklyChallenge) {
    GymCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal),
        containerColor = AppColors.TonalLavender
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = challenge.title,
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.OnSurface
                )
                Text(
                    text = challenge.description,
                    style = AppTypography.bodySmall,
                    color = AppColors.OnSurface.copy(alpha = 0.8f)
                )
                if (challenge.target > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${challenge.current} / ${challenge.target} completed",
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.Surface
                    )
                }
            }
            Spacer(modifier = Modifier.width(AppSpacing.Medium))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.OnSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text("🏋️", fontSize = 32.sp)
            }
        }
    }
}

// ─── Articles & Tips ───────────────────────────────────────────────────────

@Composable
private fun ArticlesSection(articles: List<ArticleTip>, onSeeAll: () -> Unit) {
    Column {
        SectionHeader(title = "Articles & Tips", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.TonalLavender,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        LazyRow(
            contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles, key = { "art_${it.id}" }) { article ->
                ArticleCard(article = article)
            }
        }
    }
}

@Composable
private fun ArticleCard(article: ArticleTip) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(160.dp)
            .clip(AppShape.Large)
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF3E2723), Color(0xFF1A237E))
                )
            )
            .padding(AppSpacing.Medium),
        contentAlignment = Alignment.BottomStart
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AppColors.TonalLavender,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.TopEnd)
        )
        Text(
            text = article.title,
            style = AppTypography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = AppColors.OnSurface,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
