package com.gym.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gym.domain.model.WorkoutSession

@Entity(tableName = "workout_sessions")
data class WorkoutEntity(
    @PrimaryKey val id: String,
    val name: String,
    val dateTimestamp: Long,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val isSynced: Boolean
)

fun WorkoutEntity.toDomain() = WorkoutSession(
    id = id,
    name = name,
    dateTimestamp = dateTimestamp,
    durationMinutes = durationMinutes,
    isCompleted = isCompleted,
    isSynced = isSynced
)

fun WorkoutSession.toEntity() = WorkoutEntity(
    id = id,
    name = name,
    dateTimestamp = dateTimestamp,
    durationMinutes = durationMinutes,
    isCompleted = isCompleted,
    isSynced = isSynced
)
