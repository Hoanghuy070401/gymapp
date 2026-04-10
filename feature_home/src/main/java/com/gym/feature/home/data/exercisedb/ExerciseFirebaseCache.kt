package com.gym.feature.home.data.exercisedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gym.core.base.GymLogger
import com.gym.domain.model.ExerciseInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Cache layer: Đọc/ghi ExerciseInfo lên Firebase Realtime Database.
 * - Admin push data lên 1 lần → User đọc từ Firebase, KHÔNG gọi ExerciseDB API lại.
 * - Path: exercises/{bodyPart}/{exerciseId}
 */
@Singleton
class ExerciseFirebaseCache @Inject constructor() {

    private val db = FirebaseDatabase.getInstance()
    private val root = db.getReference("exercises")

    // ── Admin: Push exercises by body part ───────────────────────────────────

    /** Push danh sách bài tập theo body part lên Firebase (Admin only) */
    suspend fun pushExercises(bodyPart: String, exercises: List<ExerciseInfo>): Result<Int> =
        runCatching {
            val ref = root.child(sanitize(bodyPart))
            exercises.forEach { ex ->
                ref.child(ex.id).setValue(ex.toMap()).await()
            }
            GymLogger.d(TAG, "Pushed ${exercises.size} exercises for bodyPart=$bodyPart")
            exercises.size
        }.onFailure { e ->
            GymLogger.e(TAG, e, "Failed to push exercises bodyPart=$bodyPart")
        }

    /** Push danh sách body parts lên Firebase (Admin only) */
    suspend fun pushBodyPartList(parts: List<String>): Result<Unit> = runCatching {
        root.child("_meta").child("bodyParts").setValue(parts).await()
        GymLogger.d(TAG, "Pushed ${parts.size} body parts")
    }.onFailure { GymLogger.e(TAG, it, "Failed to push body parts") }

    /**
     * Push exercises và tự động cập nhật _meta/bodyParts.
     * Gọi sau mỗi lần import — kể cả import từng phần (không chỉ importAll).
     */
    suspend fun pushExercisesAndUpdateMeta(bodyPart: String, exercises: List<ExerciseInfo>): Result<Int> =
        runCatching {
            val ref = root.child(sanitize(bodyPart))
            exercises.forEach { ex ->
                ref.child(ex.id).setValue(ex.toMap()).await()
            }
            // Cập nhật _meta/bodyParts = tất cả keys hiện tại trên Firebase
            refreshMetaBodyParts()
            GymLogger.d(TAG, "Pushed ${exercises.size} exercises for bodyPart=$bodyPart")
            exercises.size
        }.onFailure { e ->
            GymLogger.e(TAG, e, "Failed to push exercises bodyPart=$bodyPart")
        }

    /** Xoá toàn bộ bài tập của 1 body part khỏi Firebase (Admin only) */
    suspend fun deleteBodyPart(bodyPart: String): Result<Unit> = runCatching {
        root.child(sanitize(bodyPart)).removeValue().await()
        GymLogger.d(TAG, "Deleted bodyPart=$bodyPart from Firebase")
    }.onFailure { GymLogger.e(TAG, it, "Failed to delete bodyPart=$bodyPart") }

    /** Xoá toàn bộ exercises (giữ lại _meta) trong Firebase (Admin only) */
    suspend fun clearAllExercises(): Result<Unit> = runCatching {
        val snap = root.get().await()
        snap.children
            .filter { it.key != "_meta" }
            .forEach { it.ref.removeValue().await() }
        GymLogger.d(TAG, "Cleared all exercise data from Firebase")
    }.onFailure { GymLogger.e(TAG, it, "Failed to clear all exercises") }

    // ── User: Read from Firebase ──────────────────────────────────────────────

    /** Kiểm tra xem body part có data trên Firebase không */
    suspend fun hasBodyPart(bodyPart: String): Boolean = runCatching {
        val snap = root.child(sanitize(bodyPart)).limitToFirst(1).get().await()
        snap.exists()
    }.getOrDefault(false)

    /** Đếm số bài tập đã lưu cho body part */
    suspend fun getExerciseCount(bodyPart: String): Int = runCatching {
        val snap = root.child(sanitize(bodyPart)).get().await()
        snap.childrenCount.toInt()
    }.getOrDefault(0)

    /**
     * Tải danh sách body parts từ Firebase.
     * - Ư u tiên: đọc _meta/bodyParts
     * - Fallback: derive từ các node keys (trường hợp admin chưa chạy importAll)
     */
    suspend fun getBodyPartList(): Result<List<String>> = runCatching {
        // 1. Try _meta first
        val metaSnap = root.child("_meta").child("bodyParts").get().await()
        val metaParts = metaSnap.children.mapNotNull { it.getValue(String::class.java) }
        if (metaParts.isNotEmpty()) {
            GymLogger.d(TAG, "Body parts from _meta: ${metaParts.size}")
            return@runCatching metaParts
        }

        // 2. Fallback: derive from existing node keys (e.g. after individual imports)
        val rootSnap = root.get().await()
        val derivedParts = rootSnap.children
            .mapNotNull { it.key }
            .filter { it != "_meta" }
            .map { it.replace("_", " ") }  // reverse sanitize
        if (derivedParts.isNotEmpty()) {
            GymLogger.d(TAG, "Body parts derived from node keys: ${derivedParts.size}")
            return@runCatching derivedParts
        }

        GymLogger.d(TAG, "No body parts found in Firebase cache")
        emptyList()
    }.onFailure { GymLogger.e(TAG, it, "Failed to get body parts from cache") }

    /** Tải bài tập theo body part từ Firebase */
    suspend fun getExercises(bodyPart: String, limit: Int = 30): Result<List<ExerciseInfo>> =
        runCatching {
            val snap = root.child(sanitize(bodyPart)).limitToFirst(limit).get().await()
            snap.children.mapNotNull { it.toExerciseInfo() }
        }.onFailure { GymLogger.e(TAG, it, "Failed to get exercises from cache bodyPart=$bodyPart") }

    /** Tải tất cả bài tập (all) từ Firebase — random sample từ các body part */
    suspend fun getAllExercises(limit: Int = 30): Result<List<ExerciseInfo>> = runCatching {
        val snap = root.get().await()
        snap.children
            .filter { it.key != "_meta" }
            .flatMap { bodyPartSnap ->
                bodyPartSnap.children.mapNotNull { it.toExerciseInfo() }
            }
            .shuffled()
            .take(limit)
    }.onFailure { GymLogger.e(TAG, it, "Failed to get all exercises from cache") }

    /** Search theo tên bài tập trong Firebase */
    suspend fun searchByName(query: String, limit: Int = 20): Result<List<ExerciseInfo>> =
        runCatching {
            val snap = root.get().await()
            val lq = query.lowercase()
            snap.children
                .filter { it.key != "_meta" }
                .flatMap { it.children.mapNotNull { ex -> ex.toExerciseInfo() } }
                .filter { it.name.lowercase().contains(lq) }
                .take(limit)
        }.onFailure { GymLogger.e(TAG, it, "Failed to search exercises query=$query") }

    // ── Real-time stream ──────────────────────────────────────────────────────

    fun streamBodyParts(): Flow<List<String>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                val parts = snap.children.mapNotNull { it.getValue(String::class.java) }
                trySend(parts)
            }
            override fun onCancelled(error: DatabaseError) {
                GymLogger.e(TAG, error.toException(), "streamBodyParts cancelled")
            }
        }
        val ref = root.child("_meta").child("bodyParts")
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Sau khi xóa/thêm — cập nhật _meta/bodyParts từ các node keys hiện tại */
    private suspend fun refreshMetaBodyParts() {
        runCatching {
            val snap = root.get().await()
            val parts = snap.children
                .mapNotNull { it.key }
                .filter { it != "_meta" }
                .map { it.replace("_", " ") }
            root.child("_meta").child("bodyParts").setValue(parts).await()
        }.onFailure { GymLogger.e(TAG, it, "Failed to refresh meta body parts") }
    }

    private fun sanitize(key: String) = key.lowercase().replace(" ", "_")

    private fun ExerciseInfo.toMap() = mapOf(
        "id" to id,
        "name" to name,
        "bodyPart" to bodyPart,
        "target" to target,
        "equipment" to equipment,
        "gifUrl" to gifUrl,
        "secondaryMuscles" to secondaryMuscles,
        "instructions" to instructions
    )

    @Suppress("UNCHECKED_CAST")
    private fun DataSnapshot.toExerciseInfo(): ExerciseInfo? = runCatching {
        ExerciseInfo(
            id = child("id").getValue(String::class.java) ?: return@runCatching null,
            name = child("name").getValue(String::class.java) ?: "",
            bodyPart = child("bodyPart").getValue(String::class.java) ?: "",
            target = child("target").getValue(String::class.java) ?: "",
            equipment = child("equipment").getValue(String::class.java) ?: "",
            gifUrl = child("gifUrl").getValue(String::class.java) ?: "",
            secondaryMuscles = (child("secondaryMuscles").value as? List<String>) ?: emptyList(),
            instructions = (child("instructions").value as? List<String>) ?: emptyList()
        )
    }.getOrNull()

    companion object {
        private const val TAG = "ExerciseFirebaseCache"
    }
}
