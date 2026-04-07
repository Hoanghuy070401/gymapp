package com.gym.feature.home.presentation.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.home.data.importer.ImportResult

/**
 * Admin-only screen for running Bulk Video Import.
 * Fetches exercises from WGER → finds YouTube videos → filters non-embeddable → tags → saves to Firebase.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BulkImportScreen(
    onBack: () -> Unit,
    viewModel: BulkImportViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var batchSizeText by remember { mutableStateOf("10") }
    var offsetText by remember { mutableStateOf("0") }

    Scaffold(
        containerColor = AppColors.Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "📥 Bulk Import Video",
                        style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.OnSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !state.isRunning) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = AppColors.TonalLavender
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Surface)
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

            // ── Info banner ────────────────────────────────────────────────────
            InfoBanner()

            // ── Config ────────────────────────────────────────────────────────
            Text(
                "Cấu hình",
                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Số bài tập (batch)",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = batchSizeText,
                        onValueChange = { if (it.all(Char::isDigit) && it.length <= 2) batchSizeText = it },
                        singleLine = true,
                        enabled = !state.isRunning,
                        colors = importFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Max 20 (quota YouTube)",
                        style = AppTypography.labelSmall,
                        color = AppColors.OnSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Offset (bỏ qua N đầu)",
                        style = AppTypography.labelMedium,
                        color = AppColors.OnSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = offsetText,
                        onValueChange = { if (it.all(Char::isDigit)) offsetText = it },
                        singleLine = true,
                        enabled = !state.isRunning,
                        colors = importFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "0 = từ đầu, 10 = trang 2...",
                        style = AppTypography.labelSmall,
                        color = AppColors.OnSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            // ── Progress ──────────────────────────────────────────────────────
            if (state.isRunning) {
                ImportProgressCard(state)
            }

            // ── Result ────────────────────────────────────────────────────────
            state.result?.let { result ->
                ImportResultCard(result, onDismiss = viewModel::clearResult)
            }

            state.error?.let { error ->
                ImportErrorCard(error, onDismiss = viewModel::clearResult)
            }

            // ── Start Button ──────────────────────────────────────────────────
            Button(
                onClick = {
                    val batch = batchSizeText.toIntOrNull()?.coerceIn(1, 20) ?: 10
                    val offset = offsetText.toIntOrNull() ?: 0
                    viewModel.startImport(batch, offset)
                },
                enabled = !state.isRunning,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.ElectricLime,
                    contentColor = AppColors.Surface,
                    disabledContainerColor = AppColors.ElectricLime.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                if (state.isRunning) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = AppColors.Surface
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Đang import...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(Icons.Default.CloudDownload, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Bắt đầu Import", fontWeight = FontWeight.Bold, style = AppTypography.labelLarge)
                }
            }

            Spacer(Modifier.height(AppSpacing.Large))
        }
    }
}

// ── Sub-composables ─────────────────────────────────────────────────────────

@Composable
private fun InfoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.TonalLavender.copy(alpha = 0.12f))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "⚡ Admin Tool — Bulk Importer",
                style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.TonalLavender
            )
            Text(
                "• Lấy bài tập từ WGER API (miễn phí)\n" +
                "• Tìm video YouTube phù hợp tự động\n" +
                "• Lọc ngầm video tắt tính năng nhúng\n" +
                "• Gắn nhãn độ tuổi & mục tiêu tự động\n" +
                "• Lưu vào Firebase (chỉ video hợp lệ)",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun ImportProgressCard(state: BulkImportState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.SurfaceContainerHigh)
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Đang xử lý ${state.progressCurrent}/${state.progressTotal}",
                style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface
            )
            if (state.progressTotal > 0) {
                LinearProgressIndicator(
                    progress = { state.progressCurrent.toFloat() / state.progressTotal },
                    modifier = Modifier.fillMaxWidth(),
                    color = AppColors.ElectricLime,
                    trackColor = AppColors.OnSurfaceVariant.copy(alpha = 0.2f)
                )
            }
            Text(
                state.progressMessage,
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun ImportResultCard(result: ImportResult, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1B4332))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    "Import hoàn tất!",
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF4CAF50)
                )
            }
            ResultRow("✅ Đã lưu vào Firebase", "${result.imported} video")
            ResultRow("🚫 Bỏ qua (tắt nhúng)", "${result.skippedNotEmbeddable} video")
            ResultRow("🔍 Không tìm thấy video", "${result.skippedNoVideo} bài tập")
            if (result.errors > 0) {
                ResultRow("⚠️ Lỗi xử lý", "${result.errors} bài tập")
            }
            TextButton(onClick = onDismiss) {
                Text("Đóng", color = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = AppTypography.bodySmall, color = Color.White.copy(alpha = 0.8f))
        Text(value, style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
    }
}

@Composable
private fun ImportErrorCard(error: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.Error.copy(alpha = 0.12f))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, null, tint = AppColors.Error, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    "Lỗi Import",
                    style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.Error
                )
            }
            Text(error, style = AppTypography.bodySmall, color = AppColors.OnSurfaceVariant)
            TextButton(onClick = onDismiss) {
                Text("Đóng", color = AppColors.Error)
            }
        }
    }
}

@Composable
private fun importFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppColors.TonalLavender,
    unfocusedBorderColor = AppColors.OnSurfaceVariant.copy(alpha = 0.3f),
    focusedTextColor = AppColors.OnSurface,
    unfocusedTextColor = AppColors.OnSurface,
    disabledTextColor = AppColors.OnSurface.copy(alpha = 0.5f),
    disabledBorderColor = AppColors.OnSurfaceVariant.copy(alpha = 0.2f),
    cursorColor = AppColors.TonalLavender
)
