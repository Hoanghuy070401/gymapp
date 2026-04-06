package com.gym.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import kotlinx.coroutines.launch

/** Picker mode: DATE shows day/month/year, TIME shows hour/minute. */
enum class GymPickerMode { DATE, TIME }

/**
 * Reusable drum-roll date or time picker composable shown inside a BottomSheet.
 *
 * @param mode         DATE = (day, month, year) | TIME = (hour, minute)
 * @param initialDay   Initial day value (1–31). Used only in DATE mode.
 * @param initialMonth Initial month value (1–12). Used only in DATE mode.
 * @param initialYear  Initial year value (e.g. 2000). Used only in DATE mode.
 * @param initialHour  Initial hour (0–23). Used only in TIME mode.
 * @param initialMinute Initial minute (0–59). Used only in TIME mode.
 * @param onDateConfirmed Called with (day, month, year) in DATE mode.
 * @param onTimeConfirmed Called with (hour, minute) in TIME mode.
 * @param onDismiss    Called when the sheet is dismissed without confirming.
 */
@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun GymDateTimePicker(
    mode: GymPickerMode = GymPickerMode.DATE,
    // DATE initial values
    initialDay: Int = 1,
    initialMonth: Int = 1,
    initialYear: Int = 2000,
    // TIME initial values
    initialHour: Int = 8,
    initialMinute: Int = 0,
    onDateConfirmed: (day: Int, month: Int, year: Int) -> Unit = { _, _, _ -> },
    onTimeConfirmed: (hour: Int, minute: Int) -> Unit = { _, _ -> },
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.SurfaceContainerHigh,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = if (mode == GymPickerMode.DATE) "Chọn ngày sinh" else "Chọn giờ",
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (mode == GymPickerMode.DATE) {
                DatePickerContent(
                    initialDay = initialDay,
                    initialMonth = initialMonth,
                    initialYear = initialYear,
                    onConfirm = { d, m, y ->
                        scope.launch {
                            sheetState.hide()
                            onDateConfirmed(d, m, y)
                        }
                    },
                    onCancel = {
                        scope.launch { sheetState.hide(); onDismiss() }
                    }
                )
            } else {
                TimePickerContent(
                    initialHour = initialHour,
                    initialMinute = initialMinute,
                    onConfirm = { h, m ->
                        scope.launch {
                            sheetState.hide()
                            onTimeConfirmed(h, m)
                        }
                    },
                    onCancel = {
                        scope.launch { sheetState.hide(); onDismiss() }
                    }
                )
            }
        }
    }
}

// ── Internal: Date Picker ─────────────────────────────────────────────────────

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun DatePickerContent(
    initialDay: Int,
    initialMonth: Int,
    initialYear: Int,
    onConfirm: (Int, Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    val days = (1..31).toList()
    val months = (1..12).toList()
    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    val years = (1920..currentYear).toList()

    val dayState = rememberPagerState(
        initialPage = (initialDay - 1).coerceIn(0, days.lastIndex),
        pageCount = { days.size }
    )
    val monthState = rememberPagerState(
        initialPage = (initialMonth - 1).coerceIn(0, months.lastIndex),
        pageCount = { months.size }
    )
    val yearState = rememberPagerState(
        initialPage = (initialYear - 1920).coerceIn(0, years.lastIndex),
        pageCount = { years.size }
    )

    val selectedDay by remember { derivedStateOf { days[dayState.currentPage] } }
    val selectedMonth by remember { derivedStateOf { months[monthState.currentPage] } }
    val selectedYear by remember { derivedStateOf { years[yearState.currentPage] } }

    val monthNames = listOf("Th.1","Th.2","Th.3","Th.4","Th.5","Th.6",
        "Th.7","Th.8","Th.9","Th.10","Th.11","Th.12")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Selected value display
        Text(
            text = "%02d / %s / %d".format(selectedDay, monthNames[selectedMonth - 1], selectedYear),
            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = AppColors.ElectricLime,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Drum-roll rows
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = AppSpacing.ScreenHorizontal)
        ) {
            // Selection highlight
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.ElectricLime.copy(alpha = 0.12f))
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Day column
                DrumRollColumn(
                    pagerState = dayState,
                    label = "Ngày",
                    modifier = Modifier.weight(1f)
                ) { page -> "%02d".format(days[page]) }

                // Month column
                DrumRollColumn(
                    pagerState = monthState,
                    label = "Tháng",
                    modifier = Modifier.weight(1f)
                ) { page -> monthNames[months[page] - 1] }

                // Year column
                DrumRollColumn(
                    pagerState = yearState,
                    label = "Năm",
                    modifier = Modifier.weight(1.4f)
                ) { page -> "${years[page]}" }
            }

            // Fade overlay top
            FadeOverlay(fromTop = true, modifier = Modifier.align(Alignment.TopCenter))
            FadeOverlay(fromTop = false, modifier = Modifier.align(Alignment.BottomCenter))
        }

        Spacer(modifier = Modifier.height(24.dp))
        PickerButtons(
            onConfirm = { onConfirm(selectedDay, selectedMonth, selectedYear) },
            onCancel = onCancel
        )
    }
}

// ── Internal: Time Picker ─────────────────────────────────────────────────────

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun TimePickerContent(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    val hours = (0..23).toList()
    val minutes = (0..59).toList()

    val hourState = rememberPagerState(
        initialPage = initialHour.coerceIn(0, 23),
        pageCount = { hours.size }
    )
    val minuteState = rememberPagerState(
        initialPage = initialMinute.coerceIn(0, 59),
        pageCount = { minutes.size }
    )

    val selectedHour by remember { derivedStateOf { hours[hourState.currentPage] } }
    val selectedMinute by remember { derivedStateOf { minutes[minuteState.currentPage] } }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Selected value display
        Text(
            text = "%02d : %02d".format(selectedHour, selectedMinute),
            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = AppColors.ElectricLime,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .height(200.dp)
        ) {
            // Selection highlight
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.ElectricLime.copy(alpha = 0.12f))
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DrumRollColumn(
                    pagerState = hourState,
                    label = "Giờ",
                    modifier = Modifier.weight(1f)
                ) { page -> "%02d".format(hours[page]) }

                Text(
                    text = ":",
                    style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.OnSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                DrumRollColumn(
                    pagerState = minuteState,
                    label = "Phút",
                    modifier = Modifier.weight(1f)
                ) { page -> "%02d".format(minutes[page]) }
            }

            FadeOverlay(fromTop = true, modifier = Modifier.align(Alignment.TopCenter))
            FadeOverlay(fromTop = false, modifier = Modifier.align(Alignment.BottomCenter))
        }

        Spacer(modifier = Modifier.height(24.dp))
        PickerButtons(
            onConfirm = { onConfirm(selectedHour, selectedMinute) },
            onCancel = onCancel
        )
    }
}

// ── Shared sub-components ─────────────────────────────────────────────────────

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun DrumRollColumn(
    pagerState: androidx.compose.foundation.pager.PagerState,
    label: String,
    modifier: Modifier = Modifier,
    itemText: (page: Int) -> String
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = AppTypography.labelMedium,
            color = AppColors.OnSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        VerticalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(48.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(vertical = 76.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            val isCenter = page == pagerState.currentPage
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = itemText(page),
                    style = AppTypography.bodyLarge.copy(
                        fontWeight = if (isCenter) FontWeight.Bold else FontWeight.Normal,
                        fontSize = if (isCenter) androidx.compose.ui.unit.TextUnit(
                            20f, androidx.compose.ui.unit.TextUnitType.Sp
                        ) else androidx.compose.ui.unit.TextUnit(
                            15f, androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    ),
                    color = if (isCenter) AppColors.OnSurface
                    else AppColors.OnSurfaceVariant.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun FadeOverlay(fromTop: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                Brush.verticalGradient(
                    colors = if (fromTop)
                        listOf(AppColors.SurfaceContainerHigh, Color.Transparent)
                    else
                        listOf(Color.Transparent, AppColors.SurfaceContainerHigh)
                )
            )
    )
}

@Composable
private fun PickerButtons(onConfirm: () -> Unit, onCancel: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cancel
        Box(
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(AppColors.SurfaceContainerHigh)
                .clickable(onClick = onCancel),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Huỷ",
                style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = AppColors.OnSurfaceVariant
            )
        }
        // Confirm
        Box(
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(AppColors.ElectricLime)
                .clickable(onClick = onConfirm),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Xác nhận",
                style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.Surface
            )
        }
    }
}
