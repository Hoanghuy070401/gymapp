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

    companion object {
        private const val TAG = "VideoRepository"

        /** Curated seed videos shown when Firebase is empty */
        private fun seedVideos() = listOf(
            WorkoutVideo(
                id = "seed_1",
                title = "Squat Exercise — Full Tutorial",
                youtubeUrl = "https://www.youtube.com/watch?v=Lvh7aZ6Txg0",
                description = "Complete squat tutorial for beginners and intermediate gym-goers. Learn proper form to maximize gains and prevent injury.",
                durationMinutes = 12,
                level = "Beginner"
            ),
            WorkoutVideo(
                id = "seed_2",
                title = "Full Body Stretching & Warm Up",
                youtubeUrl = "https://www.youtube.com/watch?v=sTANio_2E0Q",
                description = "A complete full-body stretching routine to improve flexibility, reduce muscle soreness, and warm up before your workout.",
                durationMinutes = 15,
                level = "Beginner"
            ),
            WorkoutVideo(
                id = "seed_3",
                title = "Core Crusher — Ab Workout",
                youtubeUrl = "https://www.youtube.com/watch?v=StcXVFgzJkE",
                description = "Intense core-focused workout targeting all abdominal muscles. No equipment needed — just your bodyweight.",
                durationMinutes = 20,
                level = "Intermediate"
            ),
            WorkoutVideo(
                id = "seed_4",
                title = "Upper Body Blast",
                youtubeUrl = "https://www.youtube.com/watch?v=kE0LmbtMc2M",
                description = "Chest, shoulders, back and arms — complete upper body session using dumbbells. Perfect for a push day.",
                durationMinutes = 25,
                level = "Intermediate"
            )
        )
    }
}
