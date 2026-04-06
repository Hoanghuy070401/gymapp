package com.gym.domain.usecase;

import com.gym.domain.repository.WorkoutRepository;
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
public final class SaveWorkoutUseCase_Factory implements Factory<SaveWorkoutUseCase> {
  private final Provider<WorkoutRepository> repositoryProvider;

  public SaveWorkoutUseCase_Factory(Provider<WorkoutRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveWorkoutUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveWorkoutUseCase_Factory create(Provider<WorkoutRepository> repositoryProvider) {
    return new SaveWorkoutUseCase_Factory(repositoryProvider);
  }

  public static SaveWorkoutUseCase newInstance(WorkoutRepository repository) {
    return new SaveWorkoutUseCase(repository);
  }
}
