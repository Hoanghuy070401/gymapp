package com.gym.domain.model

/**
 * Domain model cho một bài tập trong Exercise Library.
 * Source: ExerciseDB API (exercisedb.p.rapidapi.com)
 * Serializable cho Navigation SavedStateHandle.
 */
data class ExerciseInfo(
    val id: String,
    val name: String,
    val bodyPart: String,       // e.g. "chest", "back", "legs"
    val target: String,         // primary muscle target
    val equipment: String,      // e.g. "barbell", "dumbbell", "body weight"
    val gifUrl: String,         // looping GIF URL
    val secondaryMuscles: List<String> = emptyList(),
    val instructions: List<String> = emptyList()
) : java.io.Serializable
