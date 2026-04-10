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
public final class ExerciseFirebaseCache_Factory implements Factory<ExerciseFirebaseCache> {
  @Override
  public ExerciseFirebaseCache get() {
    return newInstance();
  }

  public static ExerciseFirebaseCache_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ExerciseFirebaseCache newInstance() {
    return new ExerciseFirebaseCache();
  }

  private static final class InstanceHolder {
    private static final ExerciseFirebaseCache_Factory INSTANCE = new ExerciseFirebaseCache_Factory();
  }
}
