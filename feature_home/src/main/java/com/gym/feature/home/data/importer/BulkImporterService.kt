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
    val errors: Int
)

// ── Service ───────────────────────────────────────────────────────────────────

/**
 * Orchestrates: WGER exercise page → YouTube search → embeddable filter → auto-tag → Firebase.
 *
 * Designed for ADMIN-ONLY use. Never call from user-facing flows.
 * Respects YouTube 10,000 quota/day: each exercise costs ~101 quota units (100 search + 1 videos.list).
 * → Max ~99 exercises per day safely (leave 1% buffer).
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

    /**
     * Run one import batch.
     *
     * @param batchSize  max exercises to fetch from WGER (recommend ≤ 20 to stay within quota)
     * @param offset     pagination offset into WGER exercise list
     * @param onProgress callback with (currentIndex, totalCount, message) for UI updates
     */
    suspend fun runImport(
        batchSize: Int = 10,
        offset: Int = 0,
        onProgress: (Int, Int, String) -> Unit = { _, _, _ -> }
    ): ImportResult {
        var imported = 0
        var skippedNotEmbeddable = 0
        var skippedNoVideo = 0
        var errors = 0

        GymLogger.i(TAG, "BulkImport starting: batchSize=$batchSize offset=$offset")

        // ── Step 1: Fetch exercises from WGER ────────────────────────────────
        val exercises = try {
            wgerApi.getExercises(limit = batchSize, offset = offset).results
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "WGER fetch failed")
            return ImportResult(0, 0, 0, batchSize)
        }

        val total = exercises.size
        GymLogger.i(TAG, "WGER returned $total exercises")

        exercises.forEachIndexed { index, exercise ->
            val name = exercise.englishName
            onProgress(index + 1, total, "Đang xử lý: $name")

            try {
                // ── Step 2: Search YouTube ───────────────────────────────────
                val searchResults = youtubeApi.searchVideos(
                    query = "$name workout tutorial proper form",
                    maxResults = 2,
                    key = apiKey
                ).items

                if (searchResults.isEmpty()) {
                    GymLogger.d(TAG, "No YouTube results for '$name' — skipping")
                    skippedNoVideo++
                    return@forEachIndexed
                }

                // ── Step 3: Get video details (embeddable check) ─────────────
                val videoIds = searchResults.joinToString(",") { it.id.videoId }
                val videoDetails = youtubeApi.getVideoDetails(ids = videoIds, key = apiKey).items

                // SILENT FILTER: only keep videos where embeddable == true
                val safeVideos = videoDetails.filter { it.status.embeddable }

                if (safeVideos.isEmpty()) {
                    GymLogger.d(TAG, "All videos for '$name' have embedding disabled — skipping")
                    skippedNotEmbeddable += videoDetails.size
                    return@forEachIndexed
                }

                // Pick the first safe video
                val video = safeVideos.first()
                val youtubeUrl = "https://www.youtube.com/watch?v=${video.id}"

                // ── Step 4: Auto-tag based on WGER category + muscles ────────
                val (targetAges, targetGoals, targetBMIs) = autoTag(exercise)
                val level = inferLevel(exercise)

                // ── Step 5: Save to Firebase ─────────────────────────────────
                videoRepository.addWorkoutVideo(
                    title = video.snippet.title,
                    youtubeUrl = youtubeUrl,
                    description = exercise.englishDescription.ifBlank { video.snippet.description.take(200) },
                    durationMinutes = video.durationMinutes,
                    level = level,
                    targetAges = targetAges,
                    targetGoals = targetGoals,
                    targetBMIs = targetBMIs
                )

                GymLogger.i(TAG, "Imported '$name' (${video.id}) ages=$targetAges goals=$targetGoals")
                imported++

            } catch (e: Exception) {
                GymLogger.e(TAG, e, "Error importing '$name'")
                errors++
            }

            // Throttle to avoid hammering APIs
            delay(300L)
        }

        val result = ImportResult(imported, skippedNotEmbeddable, skippedNoVideo, errors)
        GymLogger.i(TAG, "BulkImport done: $result")
        return result
    }

    // ── Auto-tagging algorithm ────────────────────────────────────────────────

    /**
     * Maps WGER exercise metadata → FitBody demographic tags.
     *
     * Age logic (safety-first):
     *   - High-load compound lifts (Chest / Back / Legs) → "18-25", "26-35"
     *   - Mobility / Stretching / Flexibility → all ages including "12-17", "50+"
     *   - Default (Arms, Shoulders, Abs) → "18-25", "26-35", "36-50"
     *
     * Goal logic:
     *   - Cardio → "Lose Weight", "Get Fitter"
     *   - Legs / Chest / Back → "Gain Weight", "Get Fitter"
     *   - Calves / Abs → "Get Fitter"
     *   - Mobility / Stretching → "Flexibility", "Get Fitter"
     *   - Arms → "Gain Weight", "Get Fitter"
     */
    private fun autoTag(exercise: WgerExerciseInfo): Triple<List<String>, List<String>, List<String>> {
        val categoryName = exercise.category.name.lowercase()
        val muscleNames = exercise.muscles.map { it.nameEn.lowercase() }
            .plus(exercise.musclesSecondary.map { it.nameEn.lowercase() })

        val isMobility = categoryName.contains("stretch") ||
                categoryName.contains("mobil") ||
                categoryName.contains("yoga") ||
                categoryName.contains("flexib")

        val isCardio = categoryName.contains("cardio") ||
                categoryName.contains("endurance")

        val isHeavyCompound = muscleNames.any { it.contains("gluteus") || it.contains("quadriceps") || it.contains("pectoralis") } &&
                !isMobility

        // ── Ages ──────────────────────────────────────────────────────────────
        val targetAges = when {
            isMobility -> listOf("12-17", "18-25", "26-35", "36-50", "50+")
            isCardio   -> listOf("18-25", "26-35", "36-50")
            isHeavyCompound -> listOf("18-25", "26-35")
            else       -> listOf("18-25", "26-35", "36-50")
        }

        // ── Goals ─────────────────────────────────────────────────────────────
        val targetGoals = when {
            isMobility -> listOf("Flexibility", "Get Fitter")
            isCardio   -> listOf("Lose Weight", "Get Fitter")
            isHeavyCompound -> listOf("Gain Weight", "Get Fitter")
            else       -> listOf("Get Fitter")
        }

        // ── BMIs: default All unless mobility (explicitly ok for everyone) ────
        val targetBMIs = listOf("All")

        return Triple(targetAges, targetGoals, targetBMIs)
    }

    /** Infer exercise level from equipment and muscle complexity */
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
