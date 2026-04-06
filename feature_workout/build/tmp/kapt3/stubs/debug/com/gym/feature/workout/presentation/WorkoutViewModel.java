package com.gym.feature.workout.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0002H\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/gym/feature/workout/presentation/WorkoutViewModel;", "Lcom/gym/core/base/BaseViewModel;", "Lcom/gym/feature/workout/presentation/WorkoutState;", "Lcom/gym/feature/workout/presentation/WorkoutEvent;", "Lcom/gym/feature/workout/presentation/WorkoutEffect;", "getWorkoutsUseCase", "Lcom/gym/domain/usecase/GetWorkoutsUseCase;", "saveWorkoutUseCase", "Lcom/gym/domain/usecase/SaveWorkoutUseCase;", "syncWorkoutsUseCase", "Lcom/gym/domain/usecase/SyncWorkoutsUseCase;", "(Lcom/gym/domain/usecase/GetWorkoutsUseCase;Lcom/gym/domain/usecase/SaveWorkoutUseCase;Lcom/gym/domain/usecase/SyncWorkoutsUseCase;)V", "createInitialState", "handleEvent", "", "event", "loadWorkouts", "saveWorkout", "name", "", "durationMinutes", "", "syncOffline", "feature_workout_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class WorkoutViewModel extends com.gym.core.base.BaseViewModel<com.gym.feature.workout.presentation.WorkoutState, com.gym.feature.workout.presentation.WorkoutEvent, com.gym.feature.workout.presentation.WorkoutEffect> {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.domain.usecase.GetWorkoutsUseCase getWorkoutsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.domain.usecase.SaveWorkoutUseCase saveWorkoutUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.domain.usecase.SyncWorkoutsUseCase syncWorkoutsUseCase = null;
    
    @javax.inject.Inject()
    public WorkoutViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.domain.usecase.GetWorkoutsUseCase getWorkoutsUseCase, @org.jetbrains.annotations.NotNull()
    com.gym.domain.usecase.SaveWorkoutUseCase saveWorkoutUseCase, @org.jetbrains.annotations.NotNull()
    com.gym.domain.usecase.SyncWorkoutsUseCase syncWorkoutsUseCase) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.gym.feature.workout.presentation.WorkoutState createInitialState() {
        return null;
    }
    
    @java.lang.Override()
    public void handleEvent(@org.jetbrains.annotations.NotNull()
    com.gym.feature.workout.presentation.WorkoutEvent event) {
    }
    
    private final void loadWorkouts() {
    }
    
    private final void saveWorkout(java.lang.String name, int durationMinutes) {
    }
    
    private final void syncOffline() {
    }
}