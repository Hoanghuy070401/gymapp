package com.gym.feature.workout.presentation

import androidx.lifecycle.viewModelScope
import com.gym.core.base.BaseViewModel
import com.gym.domain.model.WorkoutSession
import com.gym.domain.usecase.GetWorkoutsUseCase
import com.gym.domain.usecase.SaveWorkoutUseCase
import com.gym.domain.usecase.SyncWorkoutsUseCase
import com.gym.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase,
    private val syncWorkoutsUseCase: SyncWorkoutsUseCase
) : BaseViewModel<WorkoutState, WorkoutEvent, WorkoutEffect>() {

    override fun createInitialState() = WorkoutState()

    init {
        setEvent(WorkoutEvent.LoadWorkouts)
    }

    override fun handleEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.LoadWorkouts -> loadWorkouts()
            is WorkoutEvent.StartWorkout -> saveWorkout(event.name, event.durationMinutes)
            is WorkoutEvent.SyncOffline -> syncOffline()
        }
    }

    private fun loadWorkouts() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getWorkoutsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> setState {
                        copy(workouts = result.data.orEmpty(), isLoading = false)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, errorMessage = result.message) }
                        setEffect { WorkoutEffect.ShowError(result.message ?: "Unknown error") }
                    }
                    is Resource.Loading -> setState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun saveWorkout(name: String, durationMinutes: Int) {
        viewModelScope.launch {
            val session = WorkoutSession(
                id = UUID.randomUUID().toString(),
                name = name,
                dateTimestamp = System.currentTimeMillis(),
                durationMinutes = durationMinutes,
                isCompleted = true,
                isSynced = false
            )
            saveWorkoutUseCase(session)
            setEffect { WorkoutEffect.WorkoutSaved }
        }
    }

    private fun syncOffline() {
        viewModelScope.launch { syncWorkoutsUseCase() }
    }
}
