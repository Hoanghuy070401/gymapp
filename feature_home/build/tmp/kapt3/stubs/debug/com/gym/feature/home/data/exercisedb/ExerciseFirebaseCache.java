package com.gym.feature.home.data.exercisedb;

/**
 * Cache layer: Đọc/ghi ExerciseInfo lên Firebase Realtime Database.
 * - Admin push data lên 1 lần → User đọc từ Firebase, KHÔNG gọi ExerciseDB API lại.
 * - Path: exercises/{bodyPart}/{exerciseId}
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\b\u0007\u0018\u0000 52\u00020\u0001:\u00015B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J,\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\"\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00120\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u000bJ4\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0010J*\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J2\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\b2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b%\u0010&J2\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00150\b2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b(\u0010&J\u000e\u0010)\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u000eH\u0002J4\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b2\u0006\u0010-\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b.\u0010\u001cJ\u0012\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u001200J\u000e\u00101\u001a\u0004\u0018\u00010\u0013*\u000202H\u0002J\u0018\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000104*\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00066"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;", "", "()V", "db", "Lcom/google/firebase/database/FirebaseDatabase;", "root", "Lcom/google/firebase/database/DatabaseReference;", "clearAllExercises", "Lkotlin/Result;", "", "clearAllExercises-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBodyPart", "bodyPart", "", "deleteBodyPart-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllExercises", "", "Lcom/gym/domain/model/ExerciseInfo;", "limit", "", "getAllExercises-gIAlu-s", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBodyPartList", "getBodyPartList-IoAF18A", "getExercises", "getExercises-0E7RQCE", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasBodyPart", "", "pushBodyPartList", "parts", "pushBodyPartList-gIAlu-s", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushExercises", "exercises", "pushExercises-0E7RQCE", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushExercisesAndUpdateMeta", "pushExercisesAndUpdateMeta-0E7RQCE", "refreshMetaBodyParts", "sanitize", "key", "searchByName", "query", "searchByName-0E7RQCE", "streamBodyParts", "Lkotlinx/coroutines/flow/Flow;", "toExerciseInfo", "Lcom/google/firebase/database/DataSnapshot;", "toMap", "", "Companion", "feature_home_debug"})
public final class ExerciseFirebaseCache {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.FirebaseDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference root = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ExerciseFirebaseCache";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache.Companion Companion = null;
    
    @javax.inject.Inject()
    public ExerciseFirebaseCache() {
        super();
    }
    
    /**
     * Kiểm tra xem body part có data trên Firebase không
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasBodyPart(@org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> streamBodyParts() {
        return null;
    }
    
    /**
     * Sau khi xóa/thêm — cập nhật _meta/bodyParts từ các node keys hiện tại
     */
    private final java.lang.Object refreshMetaBodyParts(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String sanitize(java.lang.String key) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.lang.Object> toMap(com.gym.domain.model.ExerciseInfo $this$toMap) {
        return null;
    }
    
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    private final com.gym.domain.model.ExerciseInfo toExerciseInfo(com.google.firebase.database.DataSnapshot $this$toExerciseInfo) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache$Companion;", "", "()V", "TAG", "", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}