package com.gym.feature.home.data.exercisedb;

/**
 * Client-side age & goal filter cho ExerciseInfo.
 * Cơ sở: ACSM (American College of Sports Medicine) guidelines.
 *
 * ExerciseDB API không có field tuổi → ta phân loại dựa trên
 *  • equipment  (barbell = high load risk)
 *  • bodyPart   (spine/neck = risk for seniors)
 *  • target     (muscle group affected)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eJ2\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseAgeFilter;", "", "()V", "ACTIVITY_EQUIPMENT_PRIORITY", "", "", "", "GOAL_PRIORITY_BODY_PARTS", "SENIOR_RISKY_BODY_PARTS", "SENIOR_RISKY_EQUIPMENT", "SENIOR_RISKY_TARGETS", "TEEN_BLOCKED_EQUIPMENT", "ageGroupLabel", "age", "", "filterAndSort", "", "Lcom/gym/domain/model/ExerciseInfo;", "exercises", "goal", "activityLevel", "isAppropriateForAge", "", "exercise", "priorityScore", "feature_home_debug"})
public final class ExerciseAgeFilter {
    
    /**
     * Thiết bị KHÔNG phù hợp cho thiếu niên (< 18)
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> TEEN_BLOCKED_EQUIPMENT = null;
    
    /**
     * Thiết bị KHÔNG phù hợp cho người cao tuổi (> 60)
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> SENIOR_RISKY_EQUIPMENT = null;
    
    /**
     * Vùng cơ/body part TRÁNH cho người cao tuổi
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> SENIOR_RISKY_BODY_PARTS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> SENIOR_RISKY_TARGETS = null;
    
    /**
     * Body parts ƯU TIÊN cho từng mục tiêu.
     * Dùng để sắp xếp (sort) bài tập phù hợp lên đầu — không loại bỏ.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> GOAL_PRIORITY_BODY_PARTS = null;
    
    /**
     * Equipment ƯU TIÊN theo activity level
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> ACTIVITY_EQUIPMENT_PRIORITY = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.exercisedb.ExerciseAgeFilter INSTANCE = null;
    
    private ExerciseAgeFilter() {
        super();
    }
    
    /**
     * Kiểm tra bài tập có phù hợp với độ tuổi không.
     * true = phù hợp, false = nên loại bỏ.
     */
    public final boolean isAppropriateForAge(@org.jetbrains.annotations.NotNull()
    com.gym.domain.model.ExerciseInfo exercise, int age) {
        return false;
    }
    
    /**
     * Tính điểm ưu tiên cho bài tập dựa trên user profile.
     * Điểm cao → hiển thị trước.
     * 0 = không liên quan, 3 = rất phù hợp.
     */
    public final int priorityScore(@org.jetbrains.annotations.NotNull()
    com.gym.domain.model.ExerciseInfo exercise, @org.jetbrains.annotations.NotNull()
    java.lang.String goal, @org.jetbrains.annotations.NotNull()
    java.lang.String activityLevel) {
        return 0;
    }
    
    /**
     * Filter + sort theo user profile.
     * - Loại bỏ bài tập không phù hợp tuổi
     * - Sắp xếp bài tập phù hợp nhất lên đầu
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.gym.domain.model.ExerciseInfo> filterAndSort(@org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.domain.model.ExerciseInfo> exercises, int age, @org.jetbrains.annotations.NotNull()
    java.lang.String goal, @org.jetbrains.annotations.NotNull()
    java.lang.String activityLevel) {
        return null;
    }
    
    /**
     * Label mô tả nhóm tuổi — hiển thị trên UI.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String ageGroupLabel(int age) {
        return null;
    }
}