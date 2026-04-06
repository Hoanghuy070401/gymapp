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
public final class SyncWorkoutsUseCase_Factory implements Factory<SyncWorkoutsUseCase> {
  private final Provider<WorkoutRepository> repositoryProvider;

  public SyncWorkoutsUseCase_Factory(Provider<WorkoutRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SyncWorkoutsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SyncWorkoutsUseCase_Factory create(Provider<WorkoutRepository> repositoryProvider) {
    return new SyncWorkoutsUseCase_Factory(repositoryProvider);
  }

  public static SyncWorkoutsUseCase newInstance(WorkoutRepository repository) {
    return new SyncWorkoutsUseCase(repository);
  }
}
