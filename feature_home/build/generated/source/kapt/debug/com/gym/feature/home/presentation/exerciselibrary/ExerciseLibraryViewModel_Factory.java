package com.gym.feature.home.presentation.exerciselibrary;

import com.gym.core.translation.TranslatorManager;
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

  private final Provider<TranslatorManager> translatorManagerProvider;

  public ExerciseLibraryViewModel_Factory(Provider<ExerciseRepository> repositoryProvider,
      Provider<TranslatorManager> translatorManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.translatorManagerProvider = translatorManagerProvider;
  }

  @Override
  public ExerciseLibraryViewModel get() {
    return newInstance(repositoryProvider.get(), translatorManagerProvider.get());
  }

  public static ExerciseLibraryViewModel_Factory create(
      Provider<ExerciseRepository> repositoryProvider,
      Provider<TranslatorManager> translatorManagerProvider) {
    return new ExerciseLibraryViewModel_Factory(repositoryProvider, translatorManagerProvider);
  }

  public static ExerciseLibraryViewModel newInstance(ExerciseRepository repository,
      TranslatorManager translatorManager) {
    return new ExerciseLibraryViewModel(repository, translatorManager);
  }
}
