package com.gym.feature.home.data

import com.google.firebase.database.FirebaseDatabase
import com.gym.core.base.GymLogger
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fetches workout video data from Firebase Realtime Database.
 *
 * Firebase node: `workoutVideos/{id}`
 * Fields: title, youtubeUrl, description, durationMinutes, level
 *
 * Falls back to curated hardcoded list when Firebase is empty or unreachable.
 */
@Singleton
class VideoRepository @Inject constructor() {

    // URL được đọc tự động từ google-services.json (firebase_url field)
    private val database = FirebaseDatabase.getInstance()

    /**
     * Load workout videos. Firebase first, fallback to hardcoded seed if empty.
     */
    suspend fun getWorkoutVideos(): List<WorkoutVideo> {
        return try {
            val snap = database.getReference("workoutVideos").get().await()
            if (!snap.exists() || !snap.hasChildren()) {
                GymLogger.d(TAG, "Firebase workoutVideos empty — using seed data")
                return seedVideos()
            }

            val videos = snap.children.mapNotNull { child ->
                val title = child.child("title").value as? String ?: return@mapNotNull null
                val url = child.child("youtubeUrl").value as? String ?: return@mapNotNull null
                WorkoutVideo(
                    id = child.key ?: return@mapNotNull null,
                    title = title,
                    youtubeUrl = url,
                    description = child.child("description").value as? String ?: "",
                    durationMinutes = (child.child("durationMinutes").value as? Long)?.toInt() ?: 0,
                    level = child.child("level").value as? String ?: "Beginner"
                )
            }
            GymLogger.i(TAG, "Loaded ${videos.size} workout videos from Firebase")
            videos.ifEmpty { seedVideos() }
        } catch (e: Exception) {
            GymLogger.w(TAG, "getWorkoutVideos failed — using seed data: ${e.message}")
            seedVideos()
        }
    }

    /**
     * Push a new workout video to Firebase Realtime Database.
     * Uses push() to auto-generate a unique key.
     * tags must be non-empty (validated by AddVideoViewModel before calling).
     * @throws Exception if Firebase write fails
     */
    suspend fun addWorkoutVideo(
        title: String,
        youtubeUrl: String,
        description: String = "",
        durationMinutes: Int = 0,
        level: String = "Beginner",
        targetAges: List<String> = emptyList(),
        targetGoals: List<String> = emptyList(),
        targetBMIs: List<String> = listOf("All")
    ) {
        val ref = database.getReference("workoutVideos").push()
        val data = mapOf(
            "title" to title,
            "youtubeUrl" to youtubeUrl,
            "description" to description,
            "durationMinutes" to durationMinutes,
            "level" to level,
            "createdAt" to com.google.firebase.database.ServerValue.TIMESTAMP,
            "tags" to mapOf(
                "targetAges" to targetAges,
                "targetGoals" to targetGoals,
                "targetBMIs" to targetBMIs
            )
        )
        ref.setValue(data).await()
        GymLogger.i(TAG, "addWorkoutVideo: saved id=${ref.key} title='$title' ages=$targetAges goals=$targetGoals")
    }

    companion object {
        private const val TAG = "VideoRepository"

        /** Curated seed videos shown when Firebase is empty */
        private fun seedVideos() = listOf(
            WorkoutVideo(
                id = "seed_1",
                title = "Squat Exercise — Full Tutorial",
                youtubeUrl = "https://youtu.be/ugswyoxxi74",
                description = "Complete squat tutorial for beginners and intermediate gym-goers. Learn proper form to maximize gains and prevent injury.",
                durationMinutes = 12,
                level = "Beginner"
            ),
            WorkoutVideo(
                id = "seed_2",
                title = "Full Body Stretching & Warm Up",
                youtubeUrl = "https://youtu.be/ugswyoxxi74",
                description = "A complete full-body stretching routine to improve flexibility, reduce muscle soreness, and warm up before your workout.",
                durationMinutes = 15,
                level = "Beginner"
            ),
            WorkoutVideo(
                id = "seed_3",
                title = "Core Crusher — Ab Workout",
                youtubeUrl = "https://youtu.be/ugswyoxxi74",
                description = "Intense core-focused workout targeting all abdominal muscles. No equipment needed — just your bodyweight.",
                durationMinutes = 20,
                level = "Intermediate"
            ),
            WorkoutVideo(
                id = "seed_4",
                title = "Upper Body Blast",
                youtubeUrl = "https://youtu.be/ugswyoxxi74",
                description = "Chest, shoulders, back and arms — complete upper body session using dumbbells. Perfect for a push day.",
                durationMinutes = 25,
                level = "Intermediate"
            )
        )
    }
}
