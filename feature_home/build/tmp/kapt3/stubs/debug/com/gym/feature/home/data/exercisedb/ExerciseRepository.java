package com.gym.feature.home.data.exercisedb;

/**
 * Repository for exercises.
 * Strategy: Cache-first
 *  1. Kiểm tra Firebase (ExerciseFirebaseCache).
 *  2. Nếu Firebase có data → trả về cache, KHÔNG gọi API.
 *  3. Nếu Firebase trống → gọi ExerciseDB API → trả kết quả về caller.
 *     (Admin tự quyết định có push lên Firebase không qua AdminExerciseImporter)
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JJ\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J*\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\r0\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J,\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ>\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 JF\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010\u0016J<\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b%\u0010&J\u0014\u0010\'\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "", "cache", "Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;", "(Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;)V", "api", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "getApi", "()Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "api$delegate", "Lkotlin/Lazy;", "fetchRawExercisesForImport", "Lkotlin/Result;", "", "Lcom/gym/domain/model/ExerciseInfo;", "apiKey", "", "bodyPart", "limit", "", "offset", "fetchRawExercisesForImport-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBodyPartList", "getBodyPartList-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExerciseById", "id", "getExerciseById-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercises", "getExercises-BWLJW6A", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercisesByBodyPart", "getExercisesByBodyPart-yxL6bBk", "searchByName", "name", "searchByName-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomain", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbItem;", "Companion", "feature_home_debug"})
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