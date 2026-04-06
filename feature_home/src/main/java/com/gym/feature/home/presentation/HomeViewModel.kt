package com.gym.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.gym.core.base.GymLogger
import com.gym.feature.home.data.VideoRepository
import com.gym.feature.home.data.WorkoutVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

// ── Domain models (local to home feature, YAGNI) ─────────────────────────

data class RecommendedRoutine(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val calories: Int,
    val imageRes: Int? = null
)

data class ArticleTip(
    val id: String,
    val title: String,
    val category: String,
    val imageRes: Int? = null
)

data class WeeklyChallenge(
    val title: String,
    val description: String,
    val current: Int,
    val target: Int
)

// ── UI State ───────────────────────────────────────────────────────────────

data class HomeState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val avatarPath: String? = null,
    val recommendedRoutines: List<RecommendedRoutine> = emptyList(),
    val workoutVideos: List<WorkoutVideo> = emptyList(),
    val recentArticles: List<ArticleTip> = emptyList(),
    val activeChallenge: WeeklyChallenge? = null,
    val error: String? = null
)

// ── ViewModel ─────────────────────────────────────────────────────────────

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()



    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        val uid = auth.currentUser?.uid
        GymLogger.d(TAG, "loadHomeData uid=$uid")

        // Hiện seed data ngay lập tức → không bao giờ thấy màn trắng
        viewModelScope.launch {
            val seedVideos = videoRepository.getWorkoutVideos()
            _state.update {
                it.copy(
                    isLoading = false,
                    workoutVideos = seedVideos,
                    recommendedRoutines = defaultRoutines(),
                    recentArticles = defaultArticles()
                )
            }
        }

        if (uid == null) {
            GymLogger.w(TAG, "loadHomeData: no current user — seed data shown")
            return
        }

        viewModelScope.launch {
            try {
                withTimeout(FIREBASE_TIMEOUT_MS) {
                    // Load user profile
                    val snap = database.getReference("users/$uid").get().await()
                    val fullName = snap.child("fullName").value as? String ?: ""
                    GymLogger.d(TAG, "loadHomeData: profile loaded name='$fullName'")

                    // Load weekly challenge from Firebase
                    val challengeSnap = database.getReference("users/$uid/weeklyChallenge").get().await()
                    val currentCount = (challengeSnap.child("current").value as? Long)?.toInt() ?: 0
                    val targetCount = (challengeSnap.child("target").value as? Long)?.toInt() ?: 5
                    val challengeTitle = challengeSnap.child("title").value as? String ?: "Plank With Hip Twist"
                    GymLogger.d(TAG, "loadHomeData: challenge $currentCount/$targetCount '$challengeTitle'")

                    val challenge = WeeklyChallenge(
                        title = "Weekly Challenge",
                        description = challengeTitle,
                        current = currentCount,
                        target = targetCount
                    )

                    // Load workout videos (Firebase with seed fallback)
                    val videos = videoRepository.getWorkoutVideos()
                    GymLogger.d(TAG, "loadHomeData: loaded ${videos.size} workout videos")

                    GymLogger.i(TAG, "loadHomeData success: name='$fullName', ${videos.size} videos")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userName = fullName,
                            recommendedRoutines = defaultRoutines(),
                            workoutVideos = videos,
                            recentArticles = defaultArticles(),
                            activeChallenge = challenge,
                            error = null
                        )
                    }
                }
            } catch (e: TimeoutCancellationException) {
                GymLogger.w(TAG, "loadHomeData timeout — showing default content")
                // Timeout: show app với seed videos, không block user
                val videos = try { videoRepository.getWorkoutVideos() } catch (_: Exception) { emptyList() }
                _state.update {
                    it.copy(
                        isLoading = false,
                        recommendedRoutines = defaultRoutines(),
                        workoutVideos = videos,
                        recentArticles = defaultArticles(),
                        error = null
                    )
                }
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "loadHomeData failed uid=$uid")
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun retry() {
        GymLogger.d(TAG, "retry")
        loadHomeData()
    }



    companion object {
        private const val TAG = "HomeViewModel"

        /** Firebase RTDB timeout — nếu không phản hồi sau 8s thì dùng seed data */
        private const val FIREBASE_TIMEOUT_MS = 8_000L

        private fun defaultRoutines() = listOf(
            RecommendedRoutine("1", "Squat Exercise", 12, 120),
            RecommendedRoutine("2", "Full Body Stretching", 12, 120),
            RecommendedRoutine("3", "Core Crusher", 20, 180),
            RecommendedRoutine("4", "Upper Body Blast", 25, 200)
        )

        private fun defaultArticles() = listOf(
            ArticleTip("1", "Supplement Guide..", "Nutrition"),
            ArticleTip("2", "15 Quick & Effective Daily Routines...", "Training")
        )
    }
}
