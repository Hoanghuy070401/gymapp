package com.gym.feature.home.presentation.video

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.ClosedCaptionDisabled
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.WorkoutVideo

/**
 * Full-screen video detail screen.
 *
 * Layout:
 *  - TopBar (back + title)
 *  - YouTubePlayer (16:9)
 *  - Action row: Quality selector | Subtitle toggle
 *  - Scrollable content: title, badges, description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    video: WorkoutVideo,
    onBack: () -> Unit
) {
    val videoId = video.videoId ?: run {
        // Invalid video ID — go back
        LaunchedEffect(Unit) { onBack() }
        return
    }

    var showQualitySheet by remember { mutableStateOf(false) }
    var subtitlesEnabled by remember { mutableStateOf(false) }
    var selectedQuality by remember { mutableStateOf("auto") }

    val qualityOptions = listOf(
        "auto" to "Auto",
        "hd1080" to "1080p HD",
        "hd720" to "720p HD",
        "large" to "480p",
        "medium" to "360p"
    )

    // Quality BottomSheet
    if (showQualitySheet) {
        ModalBottomSheet(
            onDismissRequest = { showQualitySheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = AppColors.SurfaceContainerHigh,
            dragHandle = null,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text(
                    text = "Chất lượng video",
                    style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.OnSurface,
                    modifier = Modifier
                        .padding(horizontal = AppSpacing.ScreenHorizontal)
                        .padding(bottom = 16.dp)
                )

                qualityOptions.forEach { (key, label) ->
                    val isSelected = key == selectedQuality
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedQuality = key
                                showQualitySheet = false
                                // setPlaybackQuality() not exposed by androidyoutubeplayer API.
                                // Quality preference is stored in UI state only;
                                // YouTube IFrame auto-selects based on network conditions.
                            }
                            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            style = AppTypography.bodyLarge,
                            color = if (isSelected) AppColors.ElectricLime else AppColors.OnSurface
                        )
                        if (isSelected) {
                            Text("✓", color = AppColors.ElectricLime, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (key != qualityOptions.last().first) {
                        HorizontalDivider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.15f))
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Surface)
    ) {
        // ── TopBar ──────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = AppColors.OnSurface
                )
            }
            Text(
                text = video.title,
                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
        }

        // ── YouTube Player (16:9) ────────────────────────────────────────────
        YouTubePlayerComposable(
            videoId = videoId,
            autoPlay = true,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )

        // ── Action Row ──────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.SurfaceContainerHigh)
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quality button
            ActionChip(
                label = when (selectedQuality) {
                    "auto" -> "Chất lượng: Auto"
                    "hd1080" -> "1080p HD"
                    "hd720" -> "720p HD"
                    "large" -> "480p"
                    "medium" -> "360p"
                    else -> "Auto"
                },
                icon = {
                    Icon(
                        Icons.Default.Hd,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                onClick = { showQualitySheet = true }
            )

            // Subtitle toggle
            ActionChip(
                label = if (subtitlesEnabled) "CC: Bật" else "CC: Tắt",
                icon = {
                    Icon(
                        if (subtitlesEnabled) Icons.Default.ClosedCaption
                        else Icons.Default.ClosedCaptionDisabled,
                        contentDescription = "Subtitles",
                        modifier = Modifier.size(16.dp)
                    )
                },
                selected = subtitlesEnabled,
                onClick = { subtitlesEnabled = !subtitlesEnabled }
            )
        }

        // ── Scrollable Info ──────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(AppSpacing.ScreenHorizontal)
                .padding(top = 16.dp)
        ) {
            // Title
            Text(
                text = video.title,
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Badges row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LevelBadge(level = video.level)
                if (video.durationMinutes > 0) {
                    MetaBadge("⏱ ${video.durationMinutes} phút")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // Description
            if (video.description.isNotBlank()) {
                Text(
                    text = "Mô tả",
                    style = AppTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.ElectricLime
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = video.description,
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurfaceVariant,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun ActionChip(
    label: String,
    icon: @Composable (() -> Unit)? = null,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (selected) AppColors.ElectricLime.copy(alpha = 0.15f)
                else AppColors.SurfaceContainerHigh
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon?.invoke()
        Text(
            text = label,
            style = AppTypography.labelMedium,
            color = if (selected) AppColors.ElectricLime else AppColors.OnSurface
        )
    }
}

@Composable
private fun LevelBadge(level: String) {
    val (bg, textColor) = when (level.lowercase()) {
        "beginner" -> Color(0xFF1B5E20) to Color(0xFF69F0AE)
        "intermediate" -> Color(0xFFE65100) to Color(0xFFFFCC02)
        "advanced" -> Color(0xFF4A148C) to Color(0xFFEA80FC)
        else -> AppColors.SurfaceContainerHigh to AppColors.OnSurface
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = level,
            style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
    }
}

@Composable
private fun MetaBadge(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.SurfaceContainerHigh)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = AppTypography.labelSmall,
            color = AppColors.OnSurfaceVariant
        )
    }
}
