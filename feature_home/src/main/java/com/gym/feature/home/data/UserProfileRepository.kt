package com.gym.feature.home.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.gym.core.base.GymLogger
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/** User profile data đọc từ Firebase — dùng để personalize Exercise Library */
data class UserProfile(
    val uid: String = "",
    val age: Int = 25,
    val goal: String = "",
    val activityLevel: String = "Moderate",
    val gender: String = "",
    val weightKg: Float = 60f,
    val heightCm: Int = 160
)

/**
 * Đọc profile user từ Firebase Realtime Database.
 * Path: users/{uid}/{field}
 */
@Singleton
class UserProfileRepository @Inject constructor() {

    private val db   = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getCurrentUserProfile(): Result<UserProfile> = runCatching {
        val uid = auth.currentUser?.uid
            ?: return Result.failure(Exception("User chưa đăng nhập"))

        val snap = db.getReference("users/$uid").get().await()
        if (!snap.exists()) return Result.failure(Exception("Không tìm thấy profile"))

        UserProfile(
            uid          = uid,
            age          = (snap.child("age").value as? Long)?.toInt() ?: 25,
            goal         = snap.child("goal").getValue(String::class.java) ?: "",
            activityLevel = snap.child("activityLevel").getValue(String::class.java) ?: "Moderate",
            gender       = snap.child("gender").getValue(String::class.java) ?: "",
            weightKg     = (snap.child("weightKg").value as? Double)?.toFloat() ?: 60f,
            heightCm     = (snap.child("heightCm").value as? Long)?.toInt() ?: 160
        ).also {
            GymLogger.d(TAG, "Loaded profile uid=$uid age=${it.age} goal=${it.goal} level=${it.activityLevel}")
        }
    }.onFailure { GymLogger.e(TAG, it, "Failed to load user profile") }

    companion object {
        private const val TAG = "UserProfileRepository"
    }
}
