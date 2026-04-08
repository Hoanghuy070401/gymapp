package com.gym.feature.home.data.exercisedb;

/**
 * Repository for fetching exercises from ExerciseDB API.
 * Requires RapidAPI key set in local.properties as EXERCISEDB_API_KEY.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J*\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\r\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ,\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J>\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000b0\n2\u0006\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJF\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000b0\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\f2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ<\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000b0\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010 \u001a\u00020\f2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J\u0014\u0010#\u001a\u00020\u0011*\u00020$2\u0006\u0010\r\u001a\u00020\fH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006&"}, d2 = {"Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "", "()V", "api", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "getApi", "()Lcom/gym/feature/home/data/exercisedb/ExerciseDbApiService;", "api$delegate", "Lkotlin/Lazy;", "getBodyPartList", "Lkotlin/Result;", "", "", "apiKey", "getBodyPartList-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExerciseById", "Lcom/gym/domain/model/ExerciseInfo;", "id", "getExerciseById-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercises", "limit", "", "offset", "getExercises-BWLJW6A", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercisesByBodyPart", "bodyPart", "getExercisesByBodyPart-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByName", "name", "searchByName-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomain", "Lcom/gym/feature/home/data/exercisedb/ExerciseDbItem;", "Companion", "feature_home_debug"})
public final class ExerciseRepository {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy api$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ExerciseRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.data.exercisedb.ExerciseRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public ExerciseRepository() {
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