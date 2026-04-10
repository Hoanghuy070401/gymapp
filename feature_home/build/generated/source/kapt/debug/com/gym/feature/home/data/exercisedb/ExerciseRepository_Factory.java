package com.gym.feature.home.data.exercisedb;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ExerciseRepository_Factory implements Factory<ExerciseRepository> {
  private final Provider<ExerciseFirebaseCache> cacheProvider;

  public ExerciseRepository_Factory(Provider<ExerciseFirebaseCache> cacheProvider) {
    this.cacheProvider = cacheProvider;
  }

  @Override
  public ExerciseRepository get() {
    return newInstance(cacheProvider.get());
  }

  public static ExerciseRepository_Factory create(Provider<ExerciseFirebaseCache> cacheProvider) {
    return new ExerciseRepository_Factory(cacheProvider);
  }

  public static ExerciseRepository newInstance(ExerciseFirebaseCache cache) {
    return new ExerciseRepository(cache);
  }
}
