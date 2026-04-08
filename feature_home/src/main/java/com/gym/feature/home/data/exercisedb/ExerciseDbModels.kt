package com.gym.feature.home.data.exercisedb

import com.google.gson.annotations.SerializedName

// ── ExerciseDB API Response Models ───────────────────────────────────────────
// Base URL: https://exercisedb.p.rapidapi.com
// Headers: X-RapidAPI-Key, X-RapidAPI-Host: exercisedb.p.rapidapi.com

data class ExerciseDbItem(
    val id: String?,
    val name: String?,
    val bodyPart: String?,
    val target: String?,
    val equipment: String?,
    val gifUrl: String?,
    @SerializedName("secondaryMuscles")
    val secondaryMuscles: List<String>? = emptyList(),
    val instructions: List<String>? = emptyList()
)
