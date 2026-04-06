package com.gym.domain.usecase

import com.gym.domain.model.WorkoutSession
import com.gym.domain.repository.WorkoutRepository
import com.gym.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    operator fun invoke(): Flow<Resource<List<WorkoutSession>>> = repository.getWorkouts()
}
