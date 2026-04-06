package com.gym.feature.home.presentation.video

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.extractYouTubeVideoId

/**
 * Admin dialog để thêm workout video lên Firebase.
 * Chỉ dùng tạm thời cho mục đích seed data.
 */
@Composable
fun AddVideoDialog(
    isLoading: Boolean = false,
    onDismiss: () -> Unit,
    onSubmit: (
        title: String,
        youtubeUrl: String,
        description: String,
        durationMinutes: Int,
        level: String
    ) -> Unit
) {
    var youtubeUrl by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var durationText by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf("Beginner") }
    var urlError by remember { mutableStateOf<String?>(null) }

    val levels = listOf("Beginner", "Intermediate", "Advanced")

    // Auto-detect videoId khi user nhập URL
    val detectedVideoId = remember(youtubeUrl) {
        if (youtubeUrl.isNotBlank()) extractYouTubeVideoId(youtubeUrl) else null
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.SurfaceContainerHigh)
                .padding(AppSpacing.Large)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // ── Header ──────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "➕ Thêm Video",
                        style = AppTypography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AppColors.TonalLavender
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Đóng",
                        tint = AppColors.OnSurfaceVariant,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onDismiss)
                    )
                }

                // ── YouTube URL ─────────────────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "YouTube URL *",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                    OutlinedTextField(
                        value = youtubeUrl,
                        onValueChange = {
                            youtubeUrl = it
                            urlError = null
                        },
                        placeholder = {
                            Text(
                                "https://youtu.be/... hoặc youtube.com/watch?v=...",
                                style = AppTypography.bodySmall,
                                color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Link, contentDescription = null, tint = AppColors.TonalLavender)
                        },
                        trailingIcon = {
                            // Hiện tick xanh nếu URL valid
                            if (detectedVideoId != null) {
                                Text("✓", color = Color(0xFF4CAF50), fontSize = 18.sp)
                            }
                        },
                        isError = urlError != null,
                        supportingText = {
                            when {
                                urlError != null -> Text(urlError!!, color = AppColors.Error)
                                detectedVideoId != null -> Text(
                                    "ID: $detectedVideoId",
                                    color = Color(0xFF4CAF50),
                                    style = AppTypography.labelSmall
                                )
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = outlinedTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // ── Title ───────────────────────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Tiêu đề *",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = {
                            Text("Tên bài tập...", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = outlinedTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // ── Description (optional) ──────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Mô tả (tuỳ chọn)",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = {
                            Text("Mô tả video...", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                        },
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = outlinedTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // ── Duration + Level ────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "Thời lượng (phút)",
                            style = AppTypography.labelMedium,
                            color = AppColors.OnSurfaceVariant
                        )
                        OutlinedTextField(
                            value = durationText,
                            onValueChange = { if (it.all(Char::isDigit)) durationText = it },
                            placeholder = {
                                Text("15", color = AppColors.OnSurfaceVariant.copy(alpha = 0.5f))
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            colors = outlinedTextFieldColors(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "Cấp độ",
                            style = AppTypography.labelMedium,
                            color = AppColors.OnSurfaceVariant
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            levels.forEach { lvl ->
                                val isSelected = selectedLevel == lvl
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) AppColors.TonalLavender.copy(alpha = 0.2f)
                                            else Color.Transparent
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isSelected) AppColors.TonalLavender
                                                    else AppColors.OnSurfaceVariant.copy(alpha = 0.3f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedLevel = lvl }
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = lvl,
                                        style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Medium),
                                        color = if (isSelected) AppColors.TonalLavender
                                                else AppColors.OnSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                Divider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.15f))

                // ── Action Buttons ──────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AppColors.OnSurfaceVariant)
                    ) {
                        Text("Huỷ")
                    }

                    Button(
                        onClick = {
                            // Validate
                            val videoId = extractYouTubeVideoId(youtubeUrl)
                            when {
                                youtubeUrl.isBlank() -> urlError = "Vui lòng nhập URL"
                                videoId == null -> urlError = "URL không hợp lệ"
                                title.isBlank() -> { /* title error handled separately */ }
                                else -> onSubmit(
                                    title.trim().ifBlank { "Workout Video" },
                                    youtubeUrl.trim(),
                                    description.trim(),
                                    durationText.toIntOrNull() ?: 0,
                                    selectedLevel
                                )
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.TonalLavender,
                            contentColor = AppColors.Surface
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = AppColors.Surface
                            )
                        } else {
                            Text("Thêm Video", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun outlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppColors.TonalLavender,
    unfocusedBorderColor = AppColors.OnSurfaceVariant.copy(alpha = 0.3f),
    focusedTextColor = AppColors.OnSurface,
    unfocusedTextColor = AppColors.OnSurface,
    cursorColor = AppColors.TonalLavender,
    focusedLabelColor = AppColors.TonalLavender
)
