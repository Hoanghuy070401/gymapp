package com.gym.feature.home.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing

/**
 * Admin screen: Import / Xóa bài tập → Firebase Realtime Database.
 */
@Composable
fun AdminExerciseImportScreen(
    viewModel: AdminExerciseImportViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Dialog xác nhận xóa từng body part
    var confirmDeletePart by remember { mutableStateOf<String?>(null) }

    // Dialog xác nhận xóa tất cả
    if (state.confirmClearAll) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelClearAll() },
            containerColor = AppColors.SurfaceContainerHigh,
            title = {
                Text("⚠️ Xác nhận xóa tất cả", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Toàn bộ ${state.importedParts.size} body part sẽ bị xóa khỏi Firebase. Hành động này KHÔNG THỂ hoàn tác.",
                    color = AppColors.OnSurface
                )
            },
            confirmButton = {
                TextButton(onClick = { viewModel.confirmClearAll() }) {
                    Text("Xóa tất cả", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelClearAll() }) {
                    Text("Hủy", color = AppColors.ElectricLime)
                }
            }
        )
    }

    // Dialog xác nhận xóa từng body part
    confirmDeletePart?.let { part ->
        AlertDialog(
            onDismissRequest = { confirmDeletePart = null },
            containerColor = AppColors.SurfaceContainerHigh,
            title = {
                Text("Xóa '$part'?", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
            },
            text = {
                Text("Toàn bộ bài tập '$part' sẽ bị xóa khỏi Firebase.", color = AppColors.OnSurface)
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBodyPart(part)
                    confirmDeletePart = null
                }) {
                    Text("Xóa", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeletePart = null }) {
                    Text("Hủy", color = AppColors.ElectricLime)
                }
            }
        )
    }

    GymScaffold(scrollable = false) {
        // ── Header ────────────────────────────────────────────────────────
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
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "Admin — Quản Lý Bài Tập",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.ElectricLime
            )
        }

        // ── Action Buttons ────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Import All
            Button(
                onClick = { viewModel.importAll() },
                enabled = !state.isLoading && state.bodyParts.isNotEmpty(),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.ElectricLime),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.CloudUpload, null, tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    text = if (state.isLoading) "Đang xử lý..." else "Import Tất Cả",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            // Clear All (chỉ enable khi có data)
            Button(
                onClick = { viewModel.requestClearAll() },
                enabled = !state.isLoading && state.importedParts.isNotEmpty(),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5252).copy(alpha = 0.15f),
                    disabledContainerColor = AppColors.SurfaceContainerHigh
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.DeleteSweep, null, tint = Color(0xFFFF5252), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Xóa Tất Cả", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal),
                color = AppColors.ElectricLime
            )
        }

        // ── Stats Bar ─────────────────────────────────────────────────────
        Text(
            text = "Body Parts: ${state.importedParts.size}/${state.bodyParts.size} đã lưu trên Firebase",
            style = MaterialTheme.typography.labelMedium,
            color = AppColors.OnSurfaceVariant,
            modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp)
        )

        // ── Body Part List ────────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(state.bodyParts) { part ->
                val imported = part in state.importedParts
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppColors.SurfaceContainerHigh)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Label + status
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = part.replaceFirstChar { it.uppercase() },
                            color = AppColors.OnSurface,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (imported) "✅ Firebase" else "⚠️ Chưa import",
                            color = if (imported) AppColors.ElectricLime else AppColors.OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    // Action buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (!imported) {
                            // Import button
                            TextButton(
                                onClick = { viewModel.importBodyPart(part) },
                                enabled = !state.isLoading,
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.CloudUpload, null, tint = AppColors.ElectricLime, modifier = Modifier.size(15.dp))
                                Spacer(Modifier.width(2.dp))
                                Text("Import", color = AppColors.ElectricLime, fontSize = 12.sp)
                            }
                        } else {
                            // Imported icon + Delete button
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AppColors.ElectricLime,
                                modifier = Modifier.size(18.dp)
                            )
                            IconButton(
                                onClick = { confirmDeletePart = part },
                                enabled = !state.isLoading,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Xóa $part",
                                    tint = Color(0xFFFF5252),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // ── Log Output ───────────────────────────────────────────────────
        if (state.log.isNotEmpty()) {
            HorizontalDivider(color = AppColors.SurfaceContainerHigh)
            Text(
                text = "Log hoạt động",
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurfaceVariant,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = AppSpacing.ScreenHorizontal)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0D0D0D))
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            ) {
                state.log.forEach { line ->
                    Text(
                        text = line,
                        color = Color(0xFF98FF98),
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}
