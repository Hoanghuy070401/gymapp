package com.gym.data.di;

import com.gym.data.local.GymDatabase;
import com.gym.data.local.WorkoutDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideWorkoutDaoFactory implements Factory<WorkoutDao> {
  private final Provider<GymDatabase> databaseProvider;

  public DatabaseModule_ProvideWorkoutDaoFactory(Provider<GymDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WorkoutDao get() {
    return provideWorkoutDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWorkoutDaoFactory create(
      Provider<GymDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWorkoutDaoFactory(databaseProvider);
  }

  public static WorkoutDao provideWorkoutDao(GymDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWorkoutDao(database));
  }
}
