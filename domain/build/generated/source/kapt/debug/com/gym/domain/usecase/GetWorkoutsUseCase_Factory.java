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
public final class GetWorkoutsUseCase_Factory implements Factory<GetWorkoutsUseCase> {
  private final Provider<WorkoutRepository> repositoryProvider;

  public GetWorkoutsUseCase_Factory(Provider<WorkoutRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetWorkoutsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetWorkoutsUseCase_Factory create(Provider<WorkoutRepository> repositoryProvider) {
    return new GetWorkoutsUseCase_Factory(repositoryProvider);
  }

  public static GetWorkoutsUseCase newInstance(WorkoutRepository repository) {
    return new GetWorkoutsUseCase(repository);
  }
}
