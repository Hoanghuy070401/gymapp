package com.gym.feature.home.presentation.exerciselibrary

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.gym.core.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gym.core.designsystem.component.EmptyContent
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.LoadingContent
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.domain.model.ExerciseInfo

@Composable
fun ExerciseLibraryScreen(
    viewModel: ExerciseLibraryViewModel = hiltViewModel(),
    onNavigateToDetail: (ExerciseInfo) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val imageLoader = rememberGifImageLoader()

    GymScaffold(scrollable = false) {
        ExerciseLibraryTopBar(onBack = onBack)
        when {
            state.noApiKey -> NoApiKeyBanner()
            else -> ExerciseLibraryContent(
                state = state,
                imageLoader = imageLoader,
                onSearchChanged = viewModel::onSearchQueryChanged,
                onBodyPartSelected = viewModel::onBodyPartSelected,
                onExerciseClick = onNavigateToDetail,
                onRetry = viewModel::retry,
                onTogglePersonalization = viewModel::togglePersonalization
            )
        }
    }
}

// ── Top Bar ──────────────────────────────────────────────────────────────────

@Composable
private fun ExerciseLibraryTopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "◀",
            color = AppColors.ElectricLime,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .clickable(onClick = onBack)
                .padding(8.dp)
        )
        Text(
            text = stringResource(id = R.string.exercise_library_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AppColors.ElectricLime
        )
    }
}

// ── Main Content ─────────────────────────────────────────────────────────────

@Composable
private fun ExerciseLibraryContent(
    state: ExerciseLibraryState,
    imageLoader: coil.ImageLoader,
    onSearchChanged: (String) -> Unit,
    onBodyPartSelected: (String) -> Unit,
    onExerciseClick: (ExerciseInfo) -> Unit,
    onRetry: () -> Unit,
    onTogglePersonalization: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = state.searchQuery,
            onQueryChanged = onSearchChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 8.dp)
        )

        // Personalization chip + label
        if (state.userProfile != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = state.isPersonalized,
                    onClick = onTogglePersonalization,
                    label = {
                        Text(
                            text = if (state.isPersonalized) "🧠 Cá nhân hoá" else "🧠 Lọc theo tuổi",
                            fontSize = 12.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppColors.ElectricLime,
                        selectedLabelColor = Color.Black,
                        containerColor = AppColors.SurfaceContainerHigh,
                        labelColor = AppColors.OnSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = state.isPersonalized,
                        selectedBorderColor = AppColors.ElectricLime,
                        borderColor = AppColors.SurfaceContainerHigh
                    )
                )
                if (state.isPersonalized && state.ageGroupLabel.isNotBlank()) {
                    Text(
                        text = state.ageGroupLabel,
                        fontSize = 11.sp,
                        color = AppColors.OnSurfaceVariant
                    )
                }
                state.userProfile.goal.takeIf { it.isNotBlank() }?.let { goal ->
                    if (state.isPersonalized) {
                        Text(
                            text = "• $goal",
                            fontSize = 11.sp,
                            color = AppColors.ElectricLime.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        BodyPartChipRow(
            bodyParts = state.bodyParts,
            selected = state.selectedBodyPart,
            onSelected = onBodyPartSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Spinner khi đang dịch (ML Kit chạy nền)
        if (state.isTranslating && !state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal),
                color = AppColors.ElectricLime,
                trackColor = AppColors.SurfaceContainerHigh
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        when {
            state.isLoading     -> LoadingContent()
            state.error != null -> ErrorContent(message = state.error, onRetry = onRetry)
            state.isEmpty || (state.exercises.isEmpty() && !state.isTranslating)
                                -> AdminNotImportedBanner()
            else -> ExerciseGrid(exercises = state.exercises, imageLoader = imageLoader, onExerciseClick = onExerciseClick)
        }
    }
}

// ── Admin Not Imported Banner ────────────────────────────────────────────────

@Composable
private fun AdminNotImportedBanner() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🏋️", fontSize = 56.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Chưa có bài tập nào",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnSurface
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Quản trị viên cần import bài tập lên Firebase trước.\nMở tab Thư viện → nhấn nút ➕ để vào trang Import.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.OnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

// ── Search Bar ───────────────────────────────────────────────────────────────

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(stringResource(id = R.string.search_exercises), color = AppColors.OnSurfaceVariant) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = AppColors.ElectricLime)
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AppColors.SurfaceContainerHigh,
            unfocusedContainerColor = AppColors.SurfaceContainerHigh,
            focusedBorderColor = AppColors.ElectricLime,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = AppColors.ElectricLime,
            focusedTextColor = AppColors.OnSurface,
            unfocusedTextColor = AppColors.OnSurface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    )
}

// ── Body Part Chips ───────────────────────────────────────────────────────────

@Composable
private fun BodyPartChipRow(
    bodyParts: List<Pair<String, String>>,
    selected: String,
    onSelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(bodyParts, key = { it.first }) { part ->
            val isSelected = part.first == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) AppColors.ElectricLime else AppColors.SurfaceContainerHigh)
                    .clickable { onSelected(part.first) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = part.second.replaceFirstChar { it.uppercase() },
                    color = if (isSelected) AppColors.Surface else AppColors.OnSurface,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

// ── Exercise Grid ─────────────────────────────────────────────────────────────

@Composable
private fun ExerciseGrid(
    exercises: List<ExerciseInfo>,
    imageLoader: coil.ImageLoader,
    onExerciseClick: (ExerciseInfo) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            horizontal = AppSpacing.ScreenHorizontal,
            vertical = AppSpacing.Medium
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(exercises, key = { it.id }) { exercise ->
            ExerciseCard(exercise = exercise, imageLoader = imageLoader, onClick = { onExerciseClick(exercise) })
        }
    }
}

// ── Exercise Card ─────────────────────────────────────────────────────────────

@Composable
private fun ExerciseCard(
    exercise: ExerciseInfo,
    imageLoader: coil.ImageLoader,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.SurfaceContainerHigh),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(AppColors.Surface)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(exercise.gifUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = exercise.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.OnSurface,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = exercise.bodyPart.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelMedium,
                    color = AppColors.ElectricLime
                )
                Text(
                    text = exercise.equipment.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelMedium,
                    color = AppColors.OnSurfaceVariant
                )
            }
        }
    }
}

// ── Error Content ─────────────────────────────────────────────────────────────

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                color = AppColors.Error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onRetry) {
                Text(stringResource(id = R.string.retry_button), color = AppColors.ElectricLime)
            }
        }
    }
}

// ── No API Key Banner ─────────────────────────────────────────────────────────

@Composable
private fun NoApiKeyBanner() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppSpacing.ScreenHorizontal)
        ) {
            Text(text = "🔑", fontSize = 48.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(AppSpacing.Medium))
            Text(
                text = "Cần RapidAPI Key",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AppColors.OnSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Thêm EXERCISEDB_API_KEY vào local.properties\n" +
                        "Lấy key miễn phí tại rapidapi.com",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
