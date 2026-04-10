package com.gym.feature.home.presentation.exerciselibrary

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.gym.core.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.domain.model.ExerciseInfo

@Composable
fun ExerciseDetailScreen(
    exercise: ExerciseInfo,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val imageLoader = rememberGifImageLoader()

    GymScaffold(scrollable = false) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "◀",
                color = AppColors.ElectricLime,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.exercise_detail_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.width(40.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Animated GIF — full width
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(AppColors.SurfaceContainerHigh)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(exercise.gifUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = exercise.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal)
            ) {
                Spacer(modifier = Modifier.height(AppSpacing.Large))

                // Exercise name
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.OnSurface
                )

                Spacer(modifier = Modifier.height(AppSpacing.Medium))

                // Meta chips row
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoChip(label = exercise.bodyPart, isAccent = true)
                    InfoChip(label = exercise.target)
                    InfoChip(label = exercise.equipment)
                }

                // Secondary muscles
                if (exercise.secondaryMuscles.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(AppSpacing.Large))
                    SectionTitle(stringResource(id = R.string.secondary_muscles_title))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        exercise.secondaryMuscles.forEach { muscle ->
                            InfoChip(label = muscle)
                        }
                    }
                }

                // Instructions
                if (exercise.instructions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(AppSpacing.Large))
                    SectionTitle(stringResource(id = R.string.instructions_title))
                    Spacer(modifier = Modifier.height(8.dp))
                    exercise.instructions.forEachIndexed { index, step ->
                        InstructionStep(number = index + 1, text = step)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = AppColors.OnSurface
    )
}

@Composable
private fun InfoChip(label: String, isAccent: Boolean = false) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isAccent) AppColors.ElectricLime.copy(alpha = 0.15f)
                else AppColors.SurfaceContainerHigh
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label.replaceFirstChar { it.uppercase() },
            fontSize = 12.sp,
            color = if (isAccent) AppColors.ElectricLime else AppColors.OnSurfaceVariant,
            fontWeight = if (isAccent) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun InstructionStep(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.SurfaceContainerHigh)
            .padding(12.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(AppColors.ElectricLime),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$number",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Surface
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.OnSurface,
            modifier = Modifier.weight(1f)
        )
    }
}
