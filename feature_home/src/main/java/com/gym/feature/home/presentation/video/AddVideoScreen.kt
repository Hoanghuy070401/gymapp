package com.gym.feature.home.presentation.video

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

private val LEVELS = listOf("Beginner", "Intermediate", "Advanced")
private val AGE_BUCKETS = listOf("12-17", "18-25", "26-35", "36-50", "50+")
private val GOALS = listOf("Gain Weight", "Lose Weight", "Get Fitter", "Flexibility")
private val BMIS = listOf("All", "Underweight", "Normal", "Overweight", "Obese")

/**
 * Dedicated screen for adding a new workout video to Firebase.
 * Features: strict validation, BackHandler with confirmation dialog, one-shot Snackbar events.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVideoScreen(
    onBack: () -> Unit,
    viewModel: AddVideoViewModel = hiltViewModel()
) {
    val form by viewModel.form.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDiscardDialog by remember { mutableStateOf(false) }

    // Collect one-shot UI events from Channel
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddVideoUiEvent.Success -> {
                    snackbarHostState.showSnackbar(event.message)
                    onBack()
                }
                is AddVideoUiEvent.Error -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    // BackHandler — warn if draft data exists
    BackHandler(enabled = form.hasDraft) {
        showDiscardDialog = true
    }

    // Discard confirmation dialog
    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            containerColor = AppColors.SurfaceContainerHigh,
            title = {
                Text(
                    "Bỏ thay đổi?",
                    style = AppTypography.titleMedium,
                    color = AppColors.OnSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Dữ liệu bạn đang nhập sẽ bị mất. Bạn có chắc muốn thoát không?",
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDiscardDialog = false
                    onBack()
                }) {
                    Text("Thoát", color = AppColors.Error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDiscardDialog = false }) {
                    Text("Tiếp tục nhập", color = AppColors.TonalLavender)
                }
            }
        )
    }

    Scaffold(
        containerColor = AppColors.Surface,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Thêm Video",
                        style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.OnSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (form.hasDraft) showDiscardDialog = true else onBack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = AppColors.TonalLavender
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.Large, vertical = AppSpacing.Medium),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── YouTube URL ─────────────────────────────────────────────────
            SectionHeader("YouTube URL *")
            OutlinedTextField(
                value = form.youtubeUrl,
                onValueChange = viewModel::onUrlChange,
                placeholder = {
                    Text("https://youtu.be/... hoặc youtube.com/watch?v=...",
                        style = AppTypography.bodySmall,
                        color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                },
                leadingIcon = { Icon(Icons.Default.Link, null, tint = AppColors.TonalLavender) },
                trailingIcon = {
                    if (form.isUrlValid) Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                },
                supportingText = {
                    when {
                        form.urlError != null -> Text(form.urlError!!, color = AppColors.Error)
                        form.isUrlValid -> Text("ID: ${form.urlVideoId}", color = Color(0xFF4CAF50), style = AppTypography.labelSmall)
                    }
                },
                isError = form.urlError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            // ── Title ───────────────────────────────────────────────────────
            SectionHeader("Tiêu đề *")
            OutlinedTextField(
                value = form.title,
                onValueChange = viewModel::onTitleChange,
                placeholder = {
                    Text("Tên bài tập...", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                },
                isError = form.titleError != null,
                supportingText = { if (form.titleError != null) Text(form.titleError!!, color = AppColors.Error) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            // ── Description ─────────────────────────────────────────────────
            SectionHeader("Mô tả (tuỳ chọn)")
            OutlinedTextField(
                value = form.description,
                onValueChange = viewModel::onDescriptionChange,
                placeholder = {
                    Text("Mô tả video...", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                },
                maxLines = 3,
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            // ── Duration + Level ────────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    SectionHeader("Thời lượng (phút)")
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = form.durationText,
                        onValueChange = viewModel::onDurationChange,
                        placeholder = {
                            Text("15", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        colors = fieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    SectionHeader("Cấp độ")
                    Spacer(Modifier.height(4.dp))
                    LEVELS.forEach { lvl ->
                        SelectableChip(
                            label = lvl,
                            selected = form.level == lvl,
                            onClick = { viewModel.onLevelChange(lvl) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                        )
                    }
                }
            }

            // ── Age Targets ─────────────────────────────────────────────────
            Column {
                SectionHeader("Nhóm tuổi mục tiêu *")
                if (form.agesError != null) {
                    Text(form.agesError!!, color = AppColors.Error, style = AppTypography.labelSmall)
                }
                Spacer(Modifier.height(6.dp))
                ChipGrid(
                    items = AGE_BUCKETS,
                    selectedItems = form.selectedAges,
                    onToggle = viewModel::onToggleAge,
                    hasError = form.agesError != null
                )
            }

            // ── Goals ────────────────────────────────────────────────────────
            Column {
                SectionHeader("Mục tiêu tập luyện *")
                if (form.goalsError != null) {
                    Text(form.goalsError!!, color = AppColors.Error, style = AppTypography.labelSmall)
                }
                Spacer(Modifier.height(6.dp))
                ChipGrid(
                    items = GOALS,
                    selectedItems = form.selectedGoals,
                    onToggle = viewModel::onToggleGoal,
                    hasError = form.goalsError != null
                )
            }

            // ── BMI (optional, default All) ──────────────────────────────────
            Column {
                SectionHeader("BMI mục tiêu (tuỳ chọn)")
                Spacer(Modifier.height(6.dp))
                ChipGrid(
                    items = BMIS,
                    selectedItems = form.selectedBMIs,
                    onToggle = viewModel::onToggleBMI,
                    hasError = false
                )
            }

            Divider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.15f))

            // ── Submit ───────────────────────────────────────────────────────
            Button(
                onClick = viewModel::submit,
                enabled = !form.isSubmitting,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.TonalLavender,
                    contentColor = AppColors.Surface,
                    disabledContainerColor = AppColors.TonalLavender.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (form.isSubmitting) {
                    CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp, color = AppColors.Surface)
                } else {
                    Text("Thêm Video", fontWeight = FontWeight.Bold, style = AppTypography.labelLarge)
                }
            }

            Spacer(Modifier.height(AppSpacing.Large))
        }
    }
}

// ── Reusable sub-composables ───────────────────────────────────────────────────

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
        color = AppColors.OnSurfaceVariant
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipGrid(
    items: List<String>,
    selectedItems: Set<String>,
    onToggle: (String) -> Unit,
    hasError: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (hasError) AppColors.Error else AppColors.OnSurfaceVariant.copy(alpha = 0.3f)
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val selected = selectedItems.contains(item)
            SelectableChip(label = item, selected = selected, onClick = { onToggle(item) }, errorBorder = hasError && !selected)
        }
    }
}

@Composable
private fun SelectableChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorBorder: Boolean = false
) {
    val bg = if (selected) AppColors.TonalLavender.copy(alpha = 0.2f) else Color.Transparent
    val border = when {
        selected -> AppColors.TonalLavender
        errorBorder -> AppColors.Error
        else -> AppColors.OnSurfaceVariant.copy(alpha = 0.3f)
    }
    val textColor = if (selected) AppColors.TonalLavender else AppColors.OnSurfaceVariant

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, style = AppTypography.labelMedium, color = textColor, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppColors.TonalLavender,
    unfocusedBorderColor = AppColors.OnSurfaceVariant.copy(alpha = 0.3f),
    focusedTextColor = AppColors.OnSurface,
    unfocusedTextColor = AppColors.OnSurface,
    cursorColor = AppColors.TonalLavender,
    focusedLabelColor = AppColors.TonalLavender
)
