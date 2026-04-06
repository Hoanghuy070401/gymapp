package com.gym.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00070\u0006H\u0016J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/gym/data/repository/WorkoutRepositoryImpl;", "Lcom/gym/domain/repository/WorkoutRepository;", "dao", "Lcom/gym/data/local/WorkoutDao;", "(Lcom/gym/data/local/WorkoutDao;)V", "getWorkouts", "Lkotlinx/coroutines/flow/Flow;", "Lcom/gym/domain/util/Resource;", "", "Lcom/gym/domain/model/WorkoutSession;", "saveWorkout", "", "workout", "(Lcom/gym/domain/model/WorkoutSession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncOfflineWorkouts", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "data_debug"})
public final class WorkoutRepositoryImpl implements com.gym.domain.repository.WorkoutRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.data.local.WorkoutDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "WorkoutRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.data.repository.WorkoutRepositoryImpl.Companion Companion = null;
    
    @javax.inject.Inject()
    public WorkoutRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.gym.data.local.WorkoutDao dao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.gym.domain.util.Resource<java.util.List<com.gym.domain.model.WorkoutSession>>> getWorkouts() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveWorkout(@org.jetbrains.annotations.NotNull()
    com.gym.domain.model.WorkoutSession workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object syncOfflineWorkouts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/data/repository/WorkoutRepositoryImpl$Companion;", "", "()V", "TAG", "", "data_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}