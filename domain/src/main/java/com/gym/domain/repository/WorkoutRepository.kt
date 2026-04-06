package com.gym.domain.repository

import com.gym.domain.model.WorkoutSession
import com.gym.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getWorkouts(): Flow<Resource<List<WorkoutSession>>>
    suspend fun saveWorkout(workout: WorkoutSession)
    suspend fun syncOfflineWorkouts()
}
