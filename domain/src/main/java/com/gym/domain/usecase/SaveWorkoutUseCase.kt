package com.gym.domain.usecase

import com.gym.domain.model.WorkoutSession
import com.gym.domain.repository.WorkoutRepository
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workout: WorkoutSession) = repository.saveWorkout(workout)
}
