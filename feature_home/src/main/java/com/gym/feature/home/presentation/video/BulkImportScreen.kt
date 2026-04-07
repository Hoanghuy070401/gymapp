package com.gym.feature.home.presentation.video

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BulkImportScreen(
    onBack: () -> Unit,
    viewModel: BulkImportViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = AppColors.Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bulk Import Video",
                        style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.ElectricLime
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = AppColors.TonalLavender
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Surface)
            )
        },
        bottomBar = {
            // Dynamic bottom bar depending on phase
            when {
                // Phase 1: Show "Load list" button
                state.exercises.isEmpty() && !state.isFetchingList && state.result == null -> {
                    BottomBar {
                        ConfigSection(state, viewModel)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.fetchExerciseList() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.ElectricLime,
                                contentColor = Color.Black
                            ),
                            shape = AppShape.Medium
                        ) {
                            Icon(Icons.Default.Search, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Tải danh sách bài tập",
                                style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
                // Phase 2: Show "Import selected" button
                state.exercises.isNotEmpty() && !state.isImporting -> {
                    BottomBar {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Select all / clear toggle
                            OutlinedButton(
                                onClick = {
                                    if (state.allSelected) viewModel.clearSelection()
                                    else viewModel.selectAll()
                                },
                                modifier = Modifier.weight(1f),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    width = 1.dp
                                ),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = AppColors.TonalLavender
                                ),
                                shape = AppShape.Medium
                            ) {
                                Text(
                                    if (state.allSelected) "Bỏ chọn tất cả" else "Chọn tất cả",
                                    style = AppTypography.bodyMedium
                                )
                            }
                            // Import button
                            Button(
                                onClick = { viewModel.importSelected() },
                                enabled = !state.noneSelected,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.ElectricLime,
                                    contentColor = Color.Black,
                                    disabledContainerColor = AppColors.SurfaceContainerHigh,
                                    disabledContentColor = AppColors.OnSurfaceVariant
                                ),
                                shape = AppShape.Medium
                            ) {
                                Icon(Icons.Default.CloudDownload, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    "Import (${state.selectedCount})",
                                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                // ── Loading list ───────────────────────────────────────────
                state.isFetchingList -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = AppColors.ElectricLime)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Đang tải danh sách từ WGER...",
                            style = AppTypography.bodyMedium,
                            color = AppColors.OnSurfaceVariant
                        )
                    }
                }

                // ── Fetch error ────────────────────────────────────────────
                state.fetchError != null -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(AppSpacing.ScreenHorizontal),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⚠️ Lỗi tải dữ liệu", style = AppTypography.titleMedium, color = AppColors.Error)
                        Spacer(Modifier.height(8.dp))
                        Text(state.fetchError!!, style = AppTypography.bodySmall, color = AppColors.OnSurfaceVariant)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchExerciseList() },
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.ElectricLime, contentColor = Color.Black)) {
                            Text("Thử lại")
                        }
                    }
                }

                // ── Import in progress ─────────────────────────────────────
                state.isImporting -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(AppSpacing.ScreenHorizontal),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Đang import YouTube videos...",
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = AppColors.OnSurface
                        )
                        Spacer(Modifier.height(24.dp))
                        LinearProgressIndicator(
                            progress = {
                                if (state.importTotal > 0)
                                    state.importProgress.toFloat() / state.importTotal
                                else 0f
                            },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                            color = AppColors.ElectricLime,
                            trackColor = AppColors.SurfaceContainerHigh
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "${state.importProgress} / ${state.importTotal}",
                            style = AppTypography.labelMedium,
                            color = AppColors.TonalLavender
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            state.importProgressMsg,
                            style = AppTypography.bodySmall,
                            color = AppColors.OnSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // ── Result card ────────────────────────────────────────────
                state.result != null -> {
                    val r = state.result!!
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(AppSpacing.ScreenHorizontal),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AppColors.ElectricLime,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Import hoàn tất!",
                            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = AppColors.ElectricLime
                        )
                        Spacer(Modifier.height(24.dp))
                        ResultRow("✅ Đã lưu vào Firebase", "${r.imported} video", AppColors.ElectricLime)
                        ResultRow("🚫 Bỏ qua (không nhúng được)", "${r.skippedNotEmbeddable} video", AppColors.OnSurfaceVariant)
                        ResultRow("🔍 Không tìm thấy video", "${r.skippedNoVideo} bài tập", AppColors.OnSurfaceVariant)
                        ResultRow("⚠️ Lỗi xử lý", "${r.errors} bài tập", if (r.errors > 0) AppColors.Error else AppColors.OnSurfaceVariant)
                        if (r.errorMessages.isNotEmpty()) {
                            Spacer(Modifier.height(12.dp))
                            ErrorLogSection(r.errorMessages)
                        }
                        Spacer(Modifier.height(32.dp))
                        Button(
                            onClick = { viewModel.dismissResult() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.ElectricLime,
                                contentColor = Color.Black
                            ),
                            shape = AppShape.Medium
                        ) {
                            Text("Import thêm", style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }

                // ── Empty state (initial or after dismiss) ─────────────────
                state.exercises.isEmpty() -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(AppSpacing.ScreenHorizontal),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = AppColors.TonalLavender,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Cấu hình và tải danh sách",
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = AppColors.OnSurface
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Chọn số bài tập và trang bắt đầu,\nsau đó chọn bài muốn import.",
                            style = AppTypography.bodySmall,
                            color = AppColors.OnSurfaceVariant
                        )
                    }
                }

                // ── Exercise selection list ────────────────────────────────
                else -> {
                    ExerciseSelectionList(
                        exercises = state.exercises,
                        selectedIds = state.selectedIds,
                        onToggle = { viewModel.toggleSelection(it) }
                    )
                }
            }

            // ── Import error snackbar overlay ──────────────────────────────
            AnimatedVisibility(
                visible = state.importError != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            ) {
                if (state.importError != null) {
                    Surface(
                        shape = AppShape.Medium,
                        color = AppColors.Error.copy(alpha = 0.9f)
                    ) {
                        Text(
                            state.importError!!,
                            modifier = Modifier.padding(12.dp),
                            style = AppTypography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// ── Exercise Selection List ───────────────────────────────────────────────────

@Composable
private fun ExerciseSelectionList(
    exercises: List<ExercisePreview>,
    selectedIds: Set<Int>,
    onToggle: (Int) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = AppSpacing.ScreenHorizontal,
            vertical = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "${exercises.size} bài tập — chọn bài muốn import",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        items(exercises, key = { it.id }) { exercise ->
            ExerciseItem(
                exercise = exercise,
                isSelected = exercise.id in selectedIds,
                onToggle = { onToggle(exercise.id) },
                onPreviewYouTube = {
                    val query = Uri.encode("${exercise.name} workout tutorial")
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query=$query")
                    )
                    context.startActivity(intent)
                }
            )
        }
        // Bottom padding for FAB/bottomBar
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
private fun ExerciseItem(
    exercise: ExercisePreview,
    isSelected: Boolean,
    onToggle: () -> Unit,
    onPreviewYouTube: () -> Unit
) {
    val borderColor = if (isSelected) AppColors.ElectricLime else Color.Transparent
    val bgColor = if (isSelected) AppColors.ElectricLime.copy(alpha = 0.08f) else AppColors.SurfaceContainerHigh

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppShape.Large)
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 0.dp,
                color = borderColor,
                shape = AppShape.Large
            )
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox circle
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) AppColors.ElectricLime else AppColors.SurfaceContainerHigh
                )
                .border(1.5.dp, if (isSelected) AppColors.ElectricLime else AppColors.OnSurfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                exercise.name,
                style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = AppColors.OnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(2.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(exercise.category, AppColors.TonalLavender.copy(alpha = 0.2f), AppColors.TonalLavender)
                Chip(exercise.level, AppColors.ElectricLime.copy(alpha = 0.12f), AppColors.ElectricLime)
            }
            if (exercise.muscles.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text(
                    "💪 ${exercise.muscles}",
                    style = AppTypography.labelMedium,
                    color = AppColors.OnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // YouTube preview button
        IconButton(
            onClick = onPreviewYouTube,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                Icons.Default.PlayCircle,
                contentDescription = "Xem video YouTube",
                tint = AppColors.TonalLavender,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// ── Small chip ────────────────────────────────────────────────────────────────

@Composable
private fun Chip(label: String, bg: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(label, style = AppTypography.labelMedium, color = textColor)
    }
}

// ── Result row ────────────────────────────────────────────────────────────────

@Composable
private fun ResultRow(label: String, value: String, valueColor: Color) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = AppTypography.bodySmall, color = AppColors.OnSurfaceVariant)
        Text(value, style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold), color = valueColor)
    }
}

// ── Error log accordion ─────────────────────────────────────────────────────────────

@Composable
fun ErrorLogSection(errorMessages: List<String>) {
    if (errorMessages.isEmpty()) return

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppShape.Large)
            .background(AppColors.Error.copy(alpha = 0.08f))
            .border(1.dp, AppColors.Error.copy(alpha = 0.3f), AppShape.Large)
    ) {
        // Header — toggle expand
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "⚠️ Chi tiết lỗi (${errorMessages.size})",
                style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = AppColors.Error
            )
            Icon(
                if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = AppColors.Error
            )
        }

        // Expandable error list
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HorizontalDivider(color = AppColors.Error.copy(alpha = 0.2f))
                Spacer(Modifier.height(4.dp))
                errorMessages.forEachIndexed { i, msg ->
                    Text(
                        "${i + 1}. $msg",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                }
            }
        }
    }
}

// ── Config section (batch / offset) ──────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfigSection(state: BulkImportUiState, viewModel: BulkImportViewModel) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text("Số bài tập (batch)", style = AppTypography.labelMedium, color = AppColors.OnSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = state.batchSize.toString(),
                onValueChange = { it.toIntOrNull()?.let { v -> viewModel.setBatchSize(v) } },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppColors.ElectricLime,
                    unfocusedBorderColor = AppColors.SurfaceContainerHigh,
                    focusedTextColor = AppColors.OnSurface,
                    unfocusedTextColor = AppColors.OnSurface,
                    cursorColor = AppColors.ElectricLime
                ),
                shape = AppShape.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Max 25 (quota YouTube)", style = AppTypography.labelMedium, color = AppColors.OnSurfaceVariant)
        }
        Column(Modifier.weight(1f)) {
            Text("Offset (bỏ qua N đầu)", style = AppTypography.labelMedium, color = AppColors.OnSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = state.offset.toString(),
                onValueChange = { it.toIntOrNull()?.let { v -> viewModel.setOffset(v) } },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppColors.ElectricLime,
                    unfocusedBorderColor = AppColors.SurfaceContainerHigh,
                    focusedTextColor = AppColors.OnSurface,
                    unfocusedTextColor = AppColors.OnSurface,
                    cursorColor = AppColors.ElectricLime
                ),
                shape = AppShape.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Text("0 = từ đầu, 10 = trang 2...", style = AppTypography.labelMedium, color = AppColors.OnSurfaceVariant)
        }
    }
}

// ── Bottom bar wrapper ────────────────────────────────────────────────────────

@Composable
private fun BottomBar(content: @Composable ColumnScope.() -> Unit) {
    Surface(color = AppColors.Surface, shadowElevation = 8.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 12.dp),
            content = content
        )
    }
}
