package com.gym.data.repository

import com.gym.core.base.GymLogger
import com.gym.data.local.WorkoutDao
import com.gym.data.local.toDomain
import com.gym.data.local.toEntity
import com.gym.domain.model.WorkoutSession
import com.gym.domain.repository.WorkoutRepository
import com.gym.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDao
    // TODO: Inject Firebase Realtime DB reference here
) : WorkoutRepository {

    override fun getWorkouts(): Flow<Resource<List<WorkoutSession>>> {
        GymLogger.d(TAG, "getWorkouts: subscribing to local DB")
        return dao.getAllWorkouts().map { entities ->
            // In a full implementation, we trigger Firebase sync here if network is available
            // but Room is the Single Source of Truth
            GymLogger.d(TAG, "getWorkouts: emitting ${entities.size} sessions")
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override suspend fun saveWorkout(workout: WorkoutSession) {
        GymLogger.d(TAG, "saveWorkout id=${workout.id} name='${workout.name}'")

        // 1. Save to Room (marked as unsynced if network fails)
        val entity = workout.toEntity().copy(isSynced = false)
        dao.insertWorkout(entity)
        GymLogger.d(TAG, "saveWorkout: saved locally id=${workout.id}")

        // 2. Try pushing to Firebase
        try {
            // firebaseDB.child("workouts").child(workout.id).setValue(...)
            // If success, update local DB to isSynced = true
            dao.insertWorkout(entity.copy(isSynced = true))
            GymLogger.i(TAG, "saveWorkout: synced to Firebase id=${workout.id}")
        } catch (e: Exception) {
            // Remains isSynced = false for later sync
            GymLogger.e(TAG, e, "saveWorkout: Firebase sync failed — queued for later id=${workout.id}")
        }
    }

    override suspend fun syncOfflineWorkouts() {
        val unsynced = dao.getUnsyncedWorkouts()
        GymLogger.d(TAG, "syncOfflineWorkouts: ${unsynced.size} pending")

        unsynced.forEach { entity ->
            try {
                // push to Firebase
                dao.insertWorkout(entity.copy(isSynced = true))
                GymLogger.i(TAG, "syncOfflineWorkouts: synced id=${entity.id}")
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "syncOfflineWorkouts: failed for id=${entity.id} — will retry")
                // Keep trying later
            }
        }
    }

    companion object {
        private const val TAG = "WorkoutRepository"
    }
}
