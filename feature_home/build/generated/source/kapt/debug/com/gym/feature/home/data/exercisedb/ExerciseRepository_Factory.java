package com.gym.feature.home.data.exercisedb;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
  @Override
  public ExerciseRepository get() {
    return newInstance();
  }

  public static ExerciseRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ExerciseRepository newInstance() {
    return new ExerciseRepository();
  }

  private static final class InstanceHolder {
    private static final ExerciseRepository_Factory INSTANCE = new ExerciseRepository_Factory();
  }
}
