package com.gym.feature.home.presentation.exerciselibrary;

import com.gym.feature.home.data.exercisedb.ExerciseRepository;
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
public final class ExerciseLibraryViewModel_Factory implements Factory<ExerciseLibraryViewModel> {
  private final Provider<ExerciseRepository> repositoryProvider;

  public ExerciseLibraryViewModel_Factory(Provider<ExerciseRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExerciseLibraryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExerciseLibraryViewModel_Factory create(
      Provider<ExerciseRepository> repositoryProvider) {
    return new ExerciseLibraryViewModel_Factory(repositoryProvider);
  }

  public static ExerciseLibraryViewModel newInstance(ExerciseRepository repository) {
    return new ExerciseLibraryViewModel(repository);
  }
}
