package com.gym.feature.home.presentation.admin;

import com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache;
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
public final class AdminExerciseImportViewModel_Factory implements Factory<AdminExerciseImportViewModel> {
  private final Provider<ExerciseRepository> repositoryProvider;

  private final Provider<ExerciseFirebaseCache> cacheProvider;

  public AdminExerciseImportViewModel_Factory(Provider<ExerciseRepository> repositoryProvider,
      Provider<ExerciseFirebaseCache> cacheProvider) {
    this.repositoryProvider = repositoryProvider;
    this.cacheProvider = cacheProvider;
  }

  @Override
  public AdminExerciseImportViewModel get() {
    return newInstance(repositoryProvider.get(), cacheProvider.get());
  }

  public static AdminExerciseImportViewModel_Factory create(
      Provider<ExerciseRepository> repositoryProvider,
      Provider<ExerciseFirebaseCache> cacheProvider) {
    return new AdminExerciseImportViewModel_Factory(repositoryProvider, cacheProvider);
  }

  public static AdminExerciseImportViewModel newInstance(ExerciseRepository repository,
      ExerciseFirebaseCache cache) {
    return new AdminExerciseImportViewModel(repository, cache);
  }
}
