package com.gym.feature.workout.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.component.*
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.domain.model.WorkoutSession

@Composable
fun WorkoutScreen(viewModel: WorkoutViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Surface)
            .padding(horizontal = AppSpacing.ScreenHorizontal)
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        SectionHeader(title = "Workout Log", modifier = Modifier.padding(horizontal = 0.dp))

        Spacer(modifier = Modifier.height(AppSpacing.SectionPadding))

        when {
            state.isLoading -> LoadingContent()
            state.workouts.isEmpty() -> EmptyContent(message = "No workouts yet.\nStart your first session!")
            else -> WorkoutList(workouts = state.workouts)
        }
    }
}

@Composable
private fun WorkoutList(workouts: List<WorkoutSession>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(AppSpacing.Small)) {
        items(items = workouts, key = { it.id }) { session ->
            WorkoutCard(session)
        }
    }
}

@Composable
private fun WorkoutCard(session: WorkoutSession) {
    GymCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(session.name, style = AppTypography.bodyLarge, color = AppColors.OnSurface)
                Text("${session.durationMinutes} min", style = AppTypography.labelMedium, color = AppColors.OnSurfaceVariant)
            }
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        if (session.isSynced) AppColors.ElectricLime else AppColors.TonalLavender,
                        shape = AppShape.Pill
                    )
            )
        }
    }
}
