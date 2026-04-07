package com.gym.feature.home.data.importer

import com.google.gson.annotations.SerializedName

// ── WGER API Response Models ──────────────────────────────────────────────────
// API reference: https://wger.de/api/v2/exerciseinfo/?format=json&language=2&limit=20

data class WgerExerciseListResponse(
    val count: Int,
    val next: String?,
    val results: List<WgerExerciseInfo>
)

data class WgerExerciseInfo(
    val id: Int,
    @SerializedName("category") val category: WgerCategory,
    @SerializedName("muscles") val muscles: List<WgerMuscle>,
    @SerializedName("muscles_secondary") val musclesSecondary: List<WgerMuscle>,
    @SerializedName("equipment") val equipment: List<WgerEquipment>,
    val translations: List<WgerTranslation>
) {
    /** Get the English name (language=2) or fall back to first available */
    val englishName: String
        get() = translations.firstOrNull { it.language == 2 }?.name
            ?: translations.firstOrNull()?.name
            ?: "Exercise #$id"

    val englishDescription: String
        get() = translations.firstOrNull { it.language == 2 }?.description?.let {
            // strip HTML tags from WGER descriptions
            it.replace(Regex("<[^>]+>"), "").trim()
        } ?: ""
}

data class WgerCategory(
    val id: Int,
    val name: String
)

data class WgerMuscle(
    val id: Int,
    @SerializedName("name_en") val nameEn: String
)

data class WgerEquipment(
    val id: Int,
    val name: String
)

data class WgerTranslation(
    val id: Int,
    val name: String,
    val description: String = "",
    val language: Int     // 2 = English
)
