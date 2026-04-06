package com.gym.domain.model

data class WorkoutSession(
    val id: String,
    val name: String,
    val dateTimestamp: Long,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val isSynced: Boolean = true // True if synced to Firebase, False if only local
)
