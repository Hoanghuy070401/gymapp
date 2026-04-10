package com.gym.feature.home.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.CheckCircle
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
 * Admin screen: Import bài tập từ ExerciseDB API → Firebase Realtime Database.
 * Sau khi import xong, user sẽ đọc data từ Firebase, không gọi API nữa.
 */
@Composable
fun AdminExerciseImportScreen(
    viewModel: AdminExerciseImportViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    GymScaffold(scrollable = false) {
        // ── Header ────────────────────────────────────────────────────────
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
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .run { this },
            )
            Text(
                text = "Admin — Import Bài Tập",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.ElectricLime
            )
            Spacer(Modifier.width(40.dp))
        }

        // ── Import All Button ─────────────────────────────────────────────
        Button(
            onClick = { viewModel.importAll() },
            enabled = !state.isLoading && state.bodyParts.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.ElectricLime),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.CloudUpload, contentDescription = null, tint = Color.Black)
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (state.isLoading) "Đang import..." else "Import TẤT CẢ lên Firebase",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal),
                color = AppColors.ElectricLime
            )
        }

        // ── Body Part List ────────────────────────────────────────────────
        Text(
            text = "Body Parts (${state.importedParts.size}/${state.bodyParts.size} đã lưu)",
            style = MaterialTheme.typography.labelMedium,
            color = AppColors.OnSurfaceVariant,
            modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp)
        )

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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = part.replaceFirstChar { it.uppercase() },
                            color = AppColors.OnSurface,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (imported) "✅ Đã có trong Firebase" else "⚠️ Chưa import",
                            color = if (imported) AppColors.ElectricLime else AppColors.OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                    if (!imported) {
                        TextButton(
                            onClick = { viewModel.importBodyPart(part) },
                            enabled = !state.isLoading
                        ) {
                            Icon(
                                Icons.Default.CloudUpload,
                                contentDescription = null,
                                tint = AppColors.ElectricLime,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Import", color = AppColors.ElectricLime, fontSize = 12.sp)
                        }
                    } else {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AppColors.ElectricLime,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // ── Log Output ───────────────────────────────────────────────────
        if (state.log.isNotEmpty()) {
            Divider(color = AppColors.SurfaceContainerHigh)
            Text(
                text = "Log",
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurfaceVariant,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
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
