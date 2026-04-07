package com.gym.feature.home.data.importer

import com.gym.core.base.GymLogger
import com.gym.feature.home.BuildConfig
import com.gym.feature.home.data.VideoRepository
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

// ── Result model ──────────────────────────────────────────────────────────────

data class ImportResult(
    val imported: Int,
    val skippedNotEmbeddable: Int,
    val skippedNoVideo: Int,
    val errors: Int,
    val errorMessages: List<String> = emptyList()  // per-exercise error details
)

// ── Service ───────────────────────────────────────────────────────────────────

/**
 * Orchestrates: WGER exercise page → YouTube search → embeddable filter → auto-tag → Firebase.
 *
 * Flow:
 *  1. fetchExercisePreviews()  — load WGER list (no YouTube quota used)
 *  2. User selects exercises in UI
 *  3. runImportSelected()      — YouTube search + sanitize + Firebase for selected
 *
 * Designed for ADMIN-ONLY use. Never call from user-facing flows.
 * YouTube quota: each exercise costs ~101 units (100 search + 1 videos.list).
 */
@Singleton
class BulkImporterService @Inject constructor(
    private val videoRepository: VideoRepository
) {
    private val wgerApi: WgerApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WgerApiService::class.java)
    }

    private val youtubeApi: YoutubeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YoutubeApiService::class.java)
    }

    private val apiKey: String get() = BuildConfig.YOUTUBE_API_KEY

    // ── Cache: raw WGER results from the last preview fetch ──────────────────
    private var cachedExercises: List<WgerExerciseInfo> = emptyList()

    // ── Preview model ─────────────────────────────────────────────────────────

    /** Lightweight exercise info shown in the selection list. */
    data class ExercisePreview(
        val id: Int,
        val name: String,
        val category: String,
        val muscles: String,
        val level: String
    )

    // ── Step 1: Fetch previews (free — no YouTube calls) ─────────────────────

    /**
     * Load a page of exercises from WGER.
     * Results are cached so [runImportSelected] can reference full objects.
     */
    suspend fun fetchExercisePreviews(
        batchSize: Int = 10,
        offset: Int = 0
    ): List<ExercisePreview> {
        val results = wgerApi.getExercises(limit = batchSize, offset = offset).results
        cachedExercises = results          // cache for import step

        val previews = results.map { exercise ->
            ExercisePreview(
                id = exercise.id,
                name = exercise.englishName,
                category = exercise.category.name,
                muscles = exercise.muscles.joinToString(", ") { it.nameEn }
                    .ifBlank { exercise.musclesSecondary.joinToString(", ") { it.nameEn } }
                    .ifBlank { "General" },
                level = inferLevel(exercise)
            )
        }

        // ── Log danh sách exercise đã fetch ───────────────────────────────
        GymLogger.i(TAG, "═══════════════════════════════════════════════════")
        GymLogger.i(TAG, "WGER Fetch: ${previews.size} exercises (offset=$offset, batch=$batchSize)")
        GymLogger.i(TAG, "───────────────────────────────────────────────────")
        previews.forEachIndexed { i, ex ->
            GymLogger.i(
                TAG,
                "#${i + 1} [id=${ex.id}] ${ex.name} | ${ex.category} | ${ex.level} | 💪 ${ex.muscles}"
            )
        }
        GymLogger.i(TAG, "═══════════════════════════════════════════════════")

        return previews
    }

    // ── Step 2: Import selected exercises (uses YouTube quota) ────────────────

    /**
     * Import only the exercises whose IDs are in [selectedIds].
     * Uses the cached WGER results from the last [fetchExercisePreviews] call.
     */
    suspend fun runImportSelected(
        selectedIds: Set<Int>,
        onProgress: (Int, Int, String) -> Unit = { _, _, _ -> }
    ): ImportResult {
        val toImport = cachedExercises.filter { it.id in selectedIds }
        if (toImport.isEmpty()) {
            GymLogger.w(TAG, "runImportSelected called but no matching exercises in cache")
            return ImportResult(0, 0, 0, 0)
        }
        return runImportInternal(toImport, onProgress)
    }

    // ── Core import pipeline ──────────────────────────────────────────────────

    private suspend fun runImportInternal(
        exercises: List<WgerExerciseInfo>,
        onProgress: (Int, Int, String) -> Unit
    ): ImportResult {
        var imported = 0
        var skippedNotEmbeddable = 0
        var skippedNoVideo = 0
        var errors = 0
        val errorMessages = mutableListOf<String>()

        val total = exercises.size
        GymLogger.i(TAG, "Import starting: $total exercises selected")

        exercises.forEachIndexed { index, exercise ->
            val name = exercise.englishName
            onProgress(index + 1, total, "Đang xử lý: $name")

            try {
                // ── YouTube search ───────────────────────────────────────────
                val searchResults = youtubeApi.searchVideos(
                    query = "$name workout tutorial proper form",
                    maxResults = 2,
                    key = apiKey
                ).items

                if (searchResults.isEmpty()) {
                    GymLogger.d(TAG, "No YouTube results for '$name'")
                    skippedNoVideo++
                    return@forEachIndexed
                }

                // ── Embeddable check ─────────────────────────────────────────
                val videoIds = searchResults.joinToString(",") { it.id.videoId }
                val videoDetails = youtubeApi.getVideoDetails(ids = videoIds, key = apiKey).items
                val safeVideos = videoDetails.filter { it.status.embeddable }

                if (safeVideos.isEmpty()) {
                    GymLogger.d(TAG, "All videos for '$name' are non-embeddable")
                    skippedNotEmbeddable += videoDetails.size
                    return@forEachIndexed
                }

                val video = safeVideos.first()
                val youtubeUrl = "https://www.youtube.com/watch?v=${video.id}"

                // ── Auto-tag ──────────────────────────────────────────────────
                val (targetAges, targetGoals, targetBMIs) = autoTag(exercise)
                val level = inferLevel(exercise)

                // ── Save to Firebase ──────────────────────────────────────────
                videoRepository.addWorkoutVideo(
                    title = video.snippet.title,
                    youtubeUrl = youtubeUrl,
                    description = exercise.englishDescription.ifBlank {
                        video.snippet.description.take(200)
                    },
                    durationMinutes = video.durationMinutes,
                    level = level,
                    targetAges = targetAges,
                    targetGoals = targetGoals,
                    targetBMIs = targetBMIs
                )

                GymLogger.i(TAG, "Imported '$name' (${video.id})")
                imported++

            } catch (e: Exception) {
                val msg = "[$name] ${e.javaClass.simpleName}: ${e.message?.take(80) ?: "Unknown error"}"
                GymLogger.e(TAG, e, "Error importing '$name'")
                errorMessages += msg
                errors++
            }

            delay(300L) // throttle
        }

        val result = ImportResult(imported, skippedNotEmbeddable, skippedNoVideo, errors, errorMessages)
        GymLogger.i(TAG, "Import done: $result")
        return result
    }

    // ── Auto-tagging ──────────────────────────────────────────────────────────

    private fun autoTag(exercise: WgerExerciseInfo): Triple<List<String>, List<String>, List<String>> {
        val categoryName = exercise.category.name.lowercase()
        val muscleNames = exercise.muscles.map { it.nameEn.lowercase() }
            .plus(exercise.musclesSecondary.map { it.nameEn.lowercase() })

        val isMobility = categoryName.contains("stretch") ||
                categoryName.contains("mobil") ||
                categoryName.contains("yoga") ||
                categoryName.contains("flexib")

        val isCardio = categoryName.contains("cardio") || categoryName.contains("endurance")

        val isHeavyCompound = muscleNames.any {
            it.contains("gluteus") || it.contains("quadriceps") || it.contains("pectoralis")
        } && !isMobility

        val targetAges = when {
            isMobility      -> listOf("12-17", "18-25", "26-35", "36-50", "50+")
            isCardio        -> listOf("18-25", "26-35", "36-50")
            isHeavyCompound -> listOf("18-25", "26-35")
            else            -> listOf("18-25", "26-35", "36-50")
        }

        val targetGoals = when {
            isMobility      -> listOf("Flexibility", "Get Fitter")
            isCardio        -> listOf("Lose Weight", "Get Fitter")
            isHeavyCompound -> listOf("Gain Weight", "Get Fitter")
            else            -> listOf("Get Fitter")
        }

        return Triple(targetAges, targetGoals, listOf("All"))
    }

    private fun inferLevel(exercise: WgerExerciseInfo): String {
        val hasBarbell = exercise.equipment.any { it.name.lowercase().contains("barbell") }
        val hasDumbbell = exercise.equipment.any { it.name.lowercase().contains("dumbbell") }
        val muscleCount = exercise.muscles.size + exercise.musclesSecondary.size
        return when {
            hasBarbell && muscleCount >= 3 -> "Advanced"
            hasDumbbell || muscleCount >= 2 -> "Intermediate"
            else -> "Beginner"
        }
    }

    companion object {
        private const val TAG = "BulkImporterService"
    }
}
