package com.gym.feature.workout.presentation;

import com.gym.domain.usecase.GetWorkoutsUseCase;
import com.gym.domain.usecase.SaveWorkoutUseCase;
import com.gym.domain.usecase.SyncWorkoutsUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WorkoutViewModel_Factory implements Factory<WorkoutViewModel> {
  private final Provider<GetWorkoutsUseCase> getWorkoutsUseCaseProvider;

  private final Provider<SaveWorkoutUseCase> saveWorkoutUseCaseProvider;

  private final Provider<SyncWorkoutsUseCase> syncWorkoutsUseCaseProvider;

  public WorkoutViewModel_Factory(Provider<GetWorkoutsUseCase> getWorkoutsUseCaseProvider,
      Provider<SaveWorkoutUseCase> saveWorkoutUseCaseProvider,
      Provider<SyncWorkoutsUseCase> syncWorkoutsUseCaseProvider) {
    this.getWorkoutsUseCaseProvider = getWorkoutsUseCaseProvider;
    this.saveWorkoutUseCaseProvider = saveWorkoutUseCaseProvider;
    this.syncWorkoutsUseCaseProvider = syncWorkoutsUseCaseProvider;
  }

  @Override
  public WorkoutViewModel get() {
    return newInstance(getWorkoutsUseCaseProvider.get(), saveWorkoutUseCaseProvider.get(), syncWorkoutsUseCaseProvider.get());
  }

  public static WorkoutViewModel_Factory create(
      Provider<GetWorkoutsUseCase> getWorkoutsUseCaseProvider,
      Provider<SaveWorkoutUseCase> saveWorkoutUseCaseProvider,
      Provider<SyncWorkoutsUseCase> syncWorkoutsUseCaseProvider) {
    return new WorkoutViewModel_Factory(getWorkoutsUseCaseProvider, saveWorkoutUseCaseProvider, syncWorkoutsUseCaseProvider);
  }

  public static WorkoutViewModel newInstance(GetWorkoutsUseCase getWorkoutsUseCase,
      SaveWorkoutUseCase saveWorkoutUseCase, SyncWorkoutsUseCase syncWorkoutsUseCase) {
    return new WorkoutViewModel(getWorkoutsUseCase, saveWorkoutUseCase, syncWorkoutsUseCase);
  }
}
