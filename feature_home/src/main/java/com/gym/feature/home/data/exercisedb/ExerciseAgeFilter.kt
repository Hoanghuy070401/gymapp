package com.gym.feature.home.data.exercisedb

import com.gym.domain.model.ExerciseInfo

/**
 * Client-side age & goal filter cho ExerciseInfo.
 * Cơ sở: ACSM (American College of Sports Medicine) guidelines.
 *
 * ExerciseDB API không có field tuổi → ta phân loại dựa trên
 *   • equipment  (barbell = high load risk)
 *   • bodyPart   (spine/neck = risk for seniors)
 *   • target     (muscle group affected)
 */
object ExerciseAgeFilter {

    // ── Quy chuẩn tuổi ────────────────────────────────────────────────────────

    /** Thiết bị KHÔNG phù hợp cho thiếu niên (< 18) */
    private val TEEN_BLOCKED_EQUIPMENT = setOf(
        "barbell", "olympic barbell", "ez barbell", "smith machine",
        "trap bar", "hammer"
    )

    /** Thiết bị KHÔNG phù hợp cho người cao tuổi (> 60) */
    private val SENIOR_RISKY_EQUIPMENT = setOf(
        "barbell", "olympic barbell", "ez barbell",
        "smith machine", "trap bar", "hammer"
    )

    /** Vùng cơ/body part TRÁNH cho người cao tuổi */
    private val SENIOR_RISKY_BODY_PARTS = setOf("spine", "neck")
    private val SENIOR_RISKY_TARGETS    = setOf("spine", "neck", "cervical")

    // ── Quy chuẩn mục tiêu ────────────────────────────────────────────────────

    /**
     * Body parts ƯU TIÊN cho từng mục tiêu.
     * Dùng để sắp xếp (sort) bài tập phù hợp lên đầu — không loại bỏ.
     */
    private val GOAL_PRIORITY_BODY_PARTS: Map<String, Set<String>> = mapOf(
        "Lose Weight"      to setOf("cardio", "waist", "upper legs", "lower legs"),
        "Build Muscle"     to setOf("chest", "back", "upper arms", "shoulders", "upper legs"),
        "Improve Stamina"  to setOf("cardio", "upper legs", "lower legs", "waist"),
        "Stay Healthy"     to setOf("back", "waist", "upper legs", "lower arms"),
        "Increase Strength" to setOf("chest", "back", "upper arms", "upper legs", "shoulders"),
        "Flexibility"      to setOf("waist", "upper legs", "lower legs", "back"),
        "Weight Gain"      to setOf("chest", "back", "upper arms", "shoulders", "upper legs")
    )

    /** Equipment ƯU TIÊN theo activity level */
    private val ACTIVITY_EQUIPMENT_PRIORITY: Map<String, Set<String>> = mapOf(
        "Sedentary"   to setOf("body weight", "resistance band", "foam roll"),
        "Light"       to setOf("body weight", "resistance band", "dumbbell", "ez barbell"),
        "Moderate"    to setOf("dumbbell", "cable", "barbell", "resistance band"),
        "Active"      to setOf("barbell", "cable", "dumbbell", "olympic barbell", "kettlebell"),
        "Very Active" to setOf("barbell", "olympic barbell", "cable", "kettlebell", "trap bar")
    )

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Kiểm tra bài tập có phù hợp với độ tuổi không.
     * true = phù hợp, false = nên loại bỏ.
     */
    fun isAppropriateForAge(exercise: ExerciseInfo, age: Int): Boolean = when {
        age < 18 -> exercise.equipment.lowercase() !in TEEN_BLOCKED_EQUIPMENT
        age > 60 -> {
            val equip   = exercise.equipment.lowercase()
            val part    = exercise.bodyPart.lowercase()
            val target  = exercise.target.lowercase()
            equip !in SENIOR_RISKY_EQUIPMENT &&
            part  !in SENIOR_RISKY_BODY_PARTS &&
            target !in SENIOR_RISKY_TARGETS
        }
        else -> true  // 18–60: không giới hạn
    }

    /**
     * Tính điểm ưu tiên cho bài tập dựa trên user profile.
     * Điểm cao → hiển thị trước.
     * 0 = không liên quan, 3 = rất phù hợp.
     */
    fun priorityScore(
        exercise: ExerciseInfo,
        goal: String,
        activityLevel: String
    ): Int {
        var score = 0

        // +2 nếu body part khớp mục tiêu
        val goalParts = GOAL_PRIORITY_BODY_PARTS[goal] ?: emptySet()
        if (exercise.bodyPart.lowercase() in goalParts) score += 2

        // +1 nếu equipment phù hợp activity level
        val preferred = ACTIVITY_EQUIPMENT_PRIORITY[activityLevel] ?: emptySet()
        if (exercise.equipment.lowercase() in preferred) score += 1

        return score
    }

    /**
     * Filter + sort theo user profile.
     * - Loại bỏ bài tập không phù hợp tuổi
     * - Sắp xếp bài tập phù hợp nhất lên đầu
     */
    fun filterAndSort(
        exercises: List<ExerciseInfo>,
        age: Int,
        goal: String,
        activityLevel: String
    ): List<ExerciseInfo> {
        return exercises
            .filter { isAppropriateForAge(it, age) }
            .sortedByDescending { priorityScore(it, goal, activityLevel) }
    }

    /**
     * Label mô tả nhóm tuổi — hiển thị trên UI.
     */
    fun ageGroupLabel(age: Int): String = when {
        age < 18 -> "Thiếu niên ($age tuổi)"
        age < 40 -> "Thanh niên ($age tuổi)"
        age < 60 -> "Trung niên ($age tuổi)"
        else     -> "Cao tuổi ($age tuổi)"
    }
}
