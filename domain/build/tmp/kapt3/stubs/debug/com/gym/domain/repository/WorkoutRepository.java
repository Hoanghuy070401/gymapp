package com.gym.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H&J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/gym/domain/repository/WorkoutRepository;", "", "getWorkouts", "Lkotlinx/coroutines/flow/Flow;", "Lcom/gym/domain/util/Resource;", "", "Lcom/gym/domain/model/WorkoutSession;", "saveWorkout", "", "workout", "(Lcom/gym/domain/model/WorkoutSession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncOfflineWorkouts", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface WorkoutRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.gym.domain.util.Resource<java.util.List<com.gym.domain.model.WorkoutSession>>> getWorkouts();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveWorkout(@org.jetbrains.annotations.NotNull()
    com.gym.domain.model.WorkoutSession workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object syncOfflineWorkouts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}