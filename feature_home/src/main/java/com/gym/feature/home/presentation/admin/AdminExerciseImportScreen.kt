package com.gym.feature.home.presentation.admin

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Refresh
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

private val RedColor    = Color(0xFFFF5252)
private val OrangeColor = Color(0xFFFF9800)
private val GreenColor  = AppColors.ElectricLime

@Composable
fun AdminExerciseImportScreen(
    viewModel: AdminExerciseImportViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var confirmDeletePart by remember { mutableStateOf<String?>(null) }

    // Dialog xác nhận xóa tất cả
    if (state.confirmClearAll) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelClearAll() },
            containerColor = AppColors.SurfaceContainerHigh,
            title = { Text("⚠️ Xác nhận xóa tất cả", color = RedColor, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Toàn bộ ${state.importedParts.size} body part sẽ bị xóa khỏi Firebase.\nHành động này KHÔNG THỂ hoàn tác.",
                    color = AppColors.OnSurface
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.confirmClearAll() },
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor)
                ) { Text("Xóa tất cả", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelClearAll() }) {
                    Text("Hủy", color = GreenColor)
                }
            }
        )
    }

    // Dialog xác nhận xóa từng body part
    confirmDeletePart?.let { part ->
        AlertDialog(
            onDismissRequest = { confirmDeletePart = null },
            containerColor = AppColors.SurfaceContainerHigh,
            title = { Text("Xóa '$part'?", color = RedColor, fontWeight = FontWeight.Bold) },
            text = { Text("Toàn bộ bài tập của '$part' sẽ bị xóa khỏi Firebase.", color = AppColors.OnSurface) },
            confirmButton = {
                Button(
                    onClick = { viewModel.deleteBodyPart(part); confirmDeletePart = null },
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor)
                ) { Text("Xóa", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeletePart = null }) { Text("Hủy", color = GreenColor) }
            }
        )
    }

    GymScaffold(scrollable = false) {
        // ── Header ────────────────────────────────────────────────────────
        AdminHeader(onBack = onBack)

        // ── Stats chips ───────────────────────────────────────────────────
        StatsRow(
            total     = state.bodyPartInfos.size,
            imported  = state.importedParts.size,
            pending   = state.pendingParts.size
        )

        // ── Global action buttons ──────────────────────────────────────────
        GlobalActionBar(
            isLoading     = state.isLoading,
            hasPending    = state.pendingParts.isNotEmpty(),
            hasImported   = state.importedParts.isNotEmpty(),
            hasParts      = state.bodyPartInfos.isNotEmpty(),
            onImportPending = { viewModel.importPending() },
            onImportAll     = { viewModel.importAll() },
            onClearAll      = { viewModel.requestClearAll() }
        )

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = GreenColor,
                trackColor = AppColors.SurfaceContainerHigh
            )
        }

        // ── Filter Tabs ───────────────────────────────────────────────────
        FilterTabRow(
            current  = state.activeFilter,
            counts   = Triple(state.bodyPartInfos.size, state.pendingParts.size, state.importedParts.size),
            onSelect = { viewModel.setFilter(it) }
        )

        // ── Body Part List ────────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = AppSpacing.ScreenHorizontal, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filteredList, key = { it.name }) { info ->
                BodyPartCard(
                    info          = info,
                    isGlobalLoading = state.isLoading,
                    onImport      = { viewModel.importBodyPart(info.name) },
                    onLoadMore    = { viewModel.loadMoreForBodyPart(info.name) },
                    onDelete      = { confirmDeletePart = info.name }
                )
            }
        }

        // ── Log Console ───────────────────────────────────────────────────
        if (state.log.isNotEmpty()) {
            LogConsole(log = state.log)
        }
    }
}

// ── Header ────────────────────────────────────────────────────────────────────

@Composable
private fun AdminHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "◀",
            color = GreenColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .clickable(onClick = onBack)
                .padding(8.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Admin", style = MaterialTheme.typography.labelSmall, color = AppColors.OnSurfaceVariant)
            Text(
                "Quản Lý Bài Tập",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GreenColor
            )
        }
    }
}

// ── Stats Row ─────────────────────────────────────────────────────────────────

@Composable
private fun StatsRow(total: Int, imported: Int, pending: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatChip(label = "Tổng",     value = "$total",    color = AppColors.OnSurfaceVariant)
        StatChip(label = "Đã import", value = "$imported", color = GreenColor)
        StatChip(label = "Chưa import", value = "$pending", color = if (pending > 0) OrangeColor else AppColors.OnSurfaceVariant)
    }
}

@Composable
private fun RowScope.StatChip(label: String, value: String, color: Color) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(AppColors.SurfaceContainerHigh)
            .padding(vertical = 6.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
            Text(label, fontSize = 10.sp, color = AppColors.OnSurfaceVariant)
        }
    }
}

// ── Global Action Bar ─────────────────────────────────────────────────────────

@Composable
private fun GlobalActionBar(
    isLoading: Boolean,
    hasPending: Boolean,
    hasImported: Boolean,
    hasParts: Boolean,
    onImportPending: () -> Unit,
    onImportAll: () -> Unit,
    onClearAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Import Chưa import
        Button(
            onClick = onImportPending,
            enabled = !isLoading && hasPending,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Icon(Icons.Default.CloudUpload, null, tint = Color.Black, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text(
                text = if (isLoading) "Đang xử lý..." else "Import Mới",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1
            )
        }

        // Import Tất cả (ghi đè)
        OutlinedButton(
            onClick = onImportAll,
            enabled = !isLoading && hasParts,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(GreenColor.copy(alpha = 0.5f))
            ),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Icon(Icons.Default.Refresh, null, tint = GreenColor, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text("Tất Cả", color = GreenColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Xóa tất cả
        IconButton(
            onClick = onClearAll,
            enabled = !isLoading && hasImported,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(RedColor.copy(alpha = if (hasImported) 0.15f else 0.05f))
        ) {
            Icon(Icons.Default.DeleteSweep, null, tint = RedColor, modifier = Modifier.size(20.dp))
        }
    }
}

// ── Filter Tab Row ────────────────────────────────────────────────────────────

@Composable
private fun FilterTabRow(
    current: BodyPartFilter,
    counts: Triple<Int, Int, Int>,  // total, pending, imported
    onSelect: (BodyPartFilter) -> Unit
) {
    val tabs = listOf(
        Triple(BodyPartFilter.ALL,          "Tất cả",     "${counts.first}"),
        Triple(BodyPartFilter.NOT_IMPORTED, "Chưa import","${counts.second}"),
        Triple(BodyPartFilter.IMPORTED,     "Đã import",  "${counts.third}")
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        tabs.forEach { (filter, label, count) ->
            val selected = filter == current
            FilterTab(label = label, count = count, selected = selected, onClick = { onSelect(filter) })
        }
    }
}

@Composable
private fun RowScope.FilterTab(label: String, count: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) GreenColor else AppColors.SurfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) Color.Black else AppColors.OnSurfaceVariant)
            Spacer(Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (selected) Color.Black.copy(0.2f) else AppColors.SurfaceContainerHigh.copy(0f))
                    .border(
                        width = if (!selected) 1.dp else 0.dp,
                        color = AppColors.OnSurfaceVariant.copy(0.3f),
                        shape = CircleShape
                    )
                    .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
                Text(count, fontSize = 10.sp, color = if (selected) Color.Black else AppColors.OnSurfaceVariant)
            }
        }
    }
}

// ── Body Part Card ────────────────────────────────────────────────────────────

@Composable
private fun BodyPartCard(
    info: BodyPartImportInfo,
    isGlobalLoading: Boolean,
    onImport: () -> Unit,
    onLoadMore: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.SurfaceContainerHigh)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Name + status badge
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = info.name.replaceFirstChar { it.uppercase() },
                    color = AppColors.OnSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (info.isImported) {
                        StatusBadge(
                            text  = "✓ Firebase",
                            bg    = GreenColor.copy(alpha = 0.12f),
                            tint  = GreenColor
                        )
                        if (info.exerciseCount > 0) {
                            StatusBadge(
                                text = "${info.exerciseCount} bài",
                                bg   = AppColors.SurfaceContainerHigh,
                                tint = AppColors.OnSurfaceVariant
                            )
                        }
                    } else {
                        StatusBadge(
                            text  = "Chưa import",
                            bg    = OrangeColor.copy(alpha = 0.12f),
                            tint  = OrangeColor
                        )
                    }
                }
            }

            // Right: Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (info.isLoadingMore) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = GreenColor,
                        strokeWidth = 2.dp
                    )
                } else if (!info.isImported) {
                    // Import button
                    Button(
                        onClick = onImport,
                        enabled = !isGlobalLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, null, tint = Color.Black, modifier = Modifier.size(13.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Import", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Check icon
                    Icon(Icons.Default.CheckCircle, null, tint = GreenColor, modifier = Modifier.size(18.dp))
                    // Delete
                    IconButton(
                        onClick = onDelete,
                        enabled = !isGlobalLoading,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Delete, null, tint = RedColor, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // Load More / Done row (chỉ hiện khi đã import)
        if (info.isImported && !info.isLoadingMore) {
            HorizontalDivider(color = AppColors.SurfaceContainerHigh.copy(0.5f), thickness = 0.5.dp)
            if (info.hasMoreData) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isGlobalLoading, onClick = onLoadMore)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ExpandMore, null, tint = GreenColor.copy(0.7f), modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Load thêm 100 bài (offset: ${info.loadedOffset})",
                        fontSize = 11.sp,
                        color = GreenColor.copy(0.7f)
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✓ Đã tải hết — ${info.exerciseCount} bài",
                        fontSize = 11.sp,
                        color = AppColors.OnSurfaceVariant.copy(0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(text: String, bg: Color, tint: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text, fontSize = 10.sp, color = tint, fontWeight = FontWeight.Medium)
    }
}

// ── Log Console ───────────────────────────────────────────────────────────────

@Composable
private fun LogConsole(log: List<String>) {
    HorizontalDivider(color = AppColors.SurfaceContainerHigh)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Log hoạt động", style = MaterialTheme.typography.labelSmall, color = AppColors.OnSurfaceVariant)
        Text("${log.size} dòng", style = MaterialTheme.typography.labelSmall, color = AppColors.OnSurfaceVariant.copy(0.6f))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = AppSpacing.ScreenHorizontal)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF0A0A0A))
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {
        log.forEach { line ->
            Text(
                text = line,
                color = Color(0xFF98FF98),
                fontSize = 10.5.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
    }
    Spacer(Modifier.height(12.dp))
}
