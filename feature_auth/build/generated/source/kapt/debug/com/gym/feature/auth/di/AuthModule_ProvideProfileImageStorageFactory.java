package com.gym.feature.auth.di;

import android.content.Context;
import com.gym.feature.auth.data.ProfileImageStorage;
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
public final class AuthModule_ProvideProfileImageStorageFactory implements Factory<ProfileImageStorage> {
  private final Provider<Context> contextProvider;

  public AuthModule_ProvideProfileImageStorageFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ProfileImageStorage get() {
    return provideProfileImageStorage(contextProvider.get());
  }

  public static AuthModule_ProvideProfileImageStorageFactory create(
      Provider<Context> contextProvider) {
    return new AuthModule_ProvideProfileImageStorageFactory(contextProvider);
  }

  public static ProfileImageStorage provideProfileImageStorage(Context context) {
    return Preconditions.checkNotNullFromProvides(AuthModule.INSTANCE.provideProfileImageStorage(context));
  }
}
