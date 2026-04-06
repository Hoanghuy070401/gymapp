package com.gym.domain.usecase

import com.gym.domain.repository.WorkoutRepository
import javax.inject.Inject

class SyncWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke() = repository.syncOfflineWorkouts()
}
