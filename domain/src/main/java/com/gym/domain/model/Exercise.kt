package com.gym.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val weightKg: Float,
    val restSeconds: Int = 60
)
