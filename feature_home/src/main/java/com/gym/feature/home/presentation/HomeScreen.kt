package com.gym.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import coil.compose.AsyncImage
import com.gym.core.designsystem.component.GymCard
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.SectionHeader
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.WorkoutVideo
import com.gym.feature.home.data.YoutubeThumbnailQuality
import com.gym.feature.home.data.youtubeThumbnailUrl
import com.gym.feature.home.presentation.categories.HomeCategoryGrid

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

    GymScaffold(scrollable = true) {
        // ── Header ─────────────────────────────────────────────────────
        HomeHeaderSection(
            userName = uiState.userName,
            onSearchClick = onNavigateToSearch,
            onNotificationsClick = onNavigateToNotifications,
            onProfileClick = onNavigateToProfile
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // ── Category Grid ──────────────────────────────────────────────
        HomeCategoryGrid(
            onWorkoutClick = onNavigateToWorkout,
            onProgressClick = onNavigateToProgress,
            onNutritionClick = onNavigateToNutrition,
            onCommunityClick = onNavigateToCommunity
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // ── Recommended Sessions ───────────────────────────────────────
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(AppSpacing.Large),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.ElectricLime)
            }
        } else {
            RecommendedSection(
                routines = uiState.recommendedRoutines,
                onSeeAll = {}
            )

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            // ── Workout Videos (YouTube) ────────────────────────────────
            if (uiState.workoutVideos.isNotEmpty()) {
                WorkoutVideosSection(
                    videos = uiState.workoutVideos,
                    onVideoClick = onNavigateToVideo,
                    onSeeAll = {}
                )
                Spacer(modifier = Modifier.height(AppSpacing.Large))
            }

            // ── Weekly Challenge ───────────────────────────────────────
            uiState.activeChallenge?.let { challenge ->
                WeeklyChallengeSection(challenge = challenge)
                Spacer(modifier = Modifier.height(AppSpacing.Large))
            }

            // ── Articles & Tips ────────────────────────────────────────
            if (uiState.recentArticles.isNotEmpty()) {
                ArticlesSection(
                    articles = uiState.recentArticles,
                    onSeeAll = {}
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

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
                    color = AppColors.ElectricLime
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
                Icon(Icons.Default.Search, contentDescription = "Search", tint = AppColors.OnSurface, modifier = Modifier.size(20.dp))
            }
            HeaderIconButton(onClick = onNotificationsClick) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = AppColors.OnSurface, modifier = Modifier.size(20.dp))
            }
            HeaderIconButton(onClick = onProfileClick) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = AppColors.OnSurface, modifier = Modifier.size(20.dp))
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

// ─── Recommended ───────────────────────────────────────────────────────────

@Composable
private fun RecommendedSection(
    routines: List<RecommendedRoutine>,
    onSeeAll: () -> Unit
) {
    Column {
        SectionHeader(title = "Recommendations", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.ElectricLime,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            routines.forEach { routine ->
                RecommendedCard(routine)
            }
        }
    }
}

@Composable
private fun RecommendedCard(routine: RecommendedRoutine) {
    val gradients = listOf(
        listOf(AppColors.PrimaryKinetic, AppColors.TonalLavender),
        listOf(AppColors.TonalLavender, AppColors.PrimaryKinetic),
        listOf(Color(0xFF1B5E20), Color(0xFF4CAF50)),
        listOf(Color(0xFF4A148C), Color(0xFF7B1FA2))
    )
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetaChip(text = "▶  ${routine.durationMinutes} Mins")
                MetaChip(text = "🔥 ${routine.calories} Kcal")
            }
        }
    }
}

@Composable
private fun MetaChip(text: String) {
    Text(
        text = text,
        style = AppTypography.labelMedium.copy(fontSize = 9.sp),
        color = AppColors.OnSurface.copy(alpha = 0.85f)
    )
}

// ─── Workout Videos (YouTube) ──────────────────────────────────────────────

@Composable
private fun WorkoutVideosSection(
    videos: List<WorkoutVideo>,
    onVideoClick: (WorkoutVideo) -> Unit,
    onSeeAll: () -> Unit
) {
    Column {
        SectionHeader(title = "Workout Videos", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.ElectricLime,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            videos.forEach { video ->
                VideoCard(video = video, onClick = { onVideoClick(video) })
            }
        }
    }
}

@Composable
private fun VideoCard(
    video: WorkoutVideo,
    onClick: () -> Unit
) {
    val thumbnailUrl = video.videoId?.let {
        youtubeThumbnailUrl(it, YoutubeThumbnailQuality.HIGH)
    }

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .clip(AppShape.Large)
            .clickable(onClick = onClick)
    ) {
        // Thumbnail image
        if (thumbnailUrl != null) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = video.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Fallback gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(listOf(AppColors.PrimaryKinetic, AppColors.TonalLavender))
                    )
            )
        }

        // Dark gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                        startY = 0f
                    )
                )
        )

        // Play button — centered
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f))
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
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
                        color = AppColors.ElectricLime
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
private fun ArticlesSection(
    articles: List<ArticleTip>,
    onSeeAll: () -> Unit
) {
    Column {
        SectionHeader(title = "Articles & Tips", trailingContent = {
            Text(
                text = "See All ▷",
                style = AppTypography.labelMedium,
                color = AppColors.ElectricLime,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        })
        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            articles.forEach { article ->
                ArticleCard(article = article)
            }
        }
    }
}

@Composable
private fun ArticleCard(article: ArticleTip) {
    val bgColors = listOf(Color(0xFF3E2723), Color(0xFF1A237E))

    Box(
        modifier = Modifier
            .width(140.dp)
            .height(160.dp)
            .clip(AppShape.Large)
            .background(Brush.linearGradient(bgColors))
            .padding(AppSpacing.Medium),
        contentAlignment = Alignment.BottomStart
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AppColors.ElectricLime,
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
