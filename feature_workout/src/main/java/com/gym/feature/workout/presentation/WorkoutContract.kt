package com.gym.feature.workout.presentation

import com.gym.core.base.ViewEffect
import com.gym.core.base.ViewEvent
import com.gym.core.base.ViewState
import com.gym.domain.model.WorkoutSession

data class WorkoutState(
    val workouts: List<WorkoutSession> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : ViewState

sealed class WorkoutEvent : ViewEvent {
    data object LoadWorkouts : WorkoutEvent()
    data class StartWorkout(val name: String, val durationMinutes: Int) : WorkoutEvent()
    data object SyncOffline : WorkoutEvent()
}

sealed class WorkoutEffect : ViewEffect {
    data class ShowError(val message: String) : WorkoutEffect()
    data object WorkoutSaved : WorkoutEffect()
}
