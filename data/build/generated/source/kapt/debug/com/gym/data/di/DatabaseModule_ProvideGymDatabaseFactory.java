package com.gym.data.di;

import android.content.Context;
import com.gym.data.local.GymDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideGymDatabaseFactory implements Factory<GymDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideGymDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GymDatabase get() {
    return provideGymDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideGymDatabaseFactory create(Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideGymDatabaseFactory(contextProvider);
  }

  public static GymDatabase provideGymDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGymDatabase(context));
  }
}
