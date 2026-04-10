package com.gym.feature.home.data.exercisedb;

/**
 * Repository cho bài tập — tách biệt hoàn toàn 2 luồng:
 *
 * ── User (Exercise Library) ────────────────────────────────────────────────
 *  CHỈ đọc từ Firebase Realtime Database.
 *  Nếu Firebase trống → trả về empty list (không fallback API).
 *  Data do Admin push lên trước.
 *
 * ── Admin (AdminExerciseImportScreen) ─────────────────────────────────────
 *  Dùng fetchRawExercisesForImport() để gọi ExerciseDB API.
 *  Sau đó Admin push kết quả lên Firebase qua ExerciseFirebaseCache.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 (2\u00020\u0001:\u0001(B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011JJ\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\u0006\u0010\u000f\u001a\u00020\u000e2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\"\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ,\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ4\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J4\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\u0006\u0010$\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b%\u0010\"J\u0014\u0010&\u001a\u00020\u0013*\u00020\'2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006)"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "", "cache", "Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;", "(Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;)V", "api", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "getApi", "()Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "api$delegate", "Lkotlin/Lazy;", "fetchBodyPartListFromApi", "Lkotlin/Result;", "", "", "apiKey", "fetchBodyPartListFromApi-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchRawExercisesForImport", "Lcom/gym/domain/model/ExerciseInfo;", "bodyPart", "limit", "", "offset", "fetchRawExercisesForImport-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBodyPartList", "getBodyPartList-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercises", "getExercises-gIAlu-s", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercisesByBodyPart", "getExercisesByBodyPart-0E7RQCE", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByName", "query", "searchByName-0E7RQCE", "toDomain", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbItem;", "Companion", "feature_home_debug"})
public final class ExerciseRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache cache = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy api$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ExerciseRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.exercisedb.ExerciseRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public ExerciseRepository(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache cache) {
        super();
    }
    
    private final com.gym.feature.home.data.exercisedb.ExerciseDbApiService getApi() {
        return null;
    }
    
    private final com.gym.domain.model.ExerciseInfo toDomain(com.gym.feature.home.data.exercisedb.ExerciseDbItem $this$toDomain, java.lang.String apiKey) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseRepository$Companion;", "", "()V", "TAG", "", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}