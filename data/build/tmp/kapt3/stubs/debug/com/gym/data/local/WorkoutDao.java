package com.gym.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/gym/data/local/WorkoutDao;", "", "getAllWorkouts", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/gym/data/local/WorkoutEntity;", "getUnsyncedWorkouts", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertWorkout", "", "workout", "(Lcom/gym/data/local/WorkoutEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertWorkouts", "workouts", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
@androidx.room.Dao()
public abstract interface WorkoutDao {
    
    @androidx.room.Query(value = "SELECT * FROM workout_sessions ORDER BY dateTimestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.gym.data.local.WorkoutEntity>> getAllWorkouts();
    
    @androidx.room.Query(value = "SELECT * FROM workout_sessions WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedWorkouts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.gym.data.local.WorkoutEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertWorkout(@org.jetbrains.annotations.NotNull()
    com.gym.data.local.WorkoutEntity workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertWorkouts(@org.jetbrains.annotations.NotNull()
    java.util.List<com.gym.data.local.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}