package com.gym.feature.auth.presentation.setup;

import com.gym.feature.auth.data.AuthRepository;
import com.gym.feature.auth.data.ProfileImageStorage;
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
public final class SetupViewModel_Factory implements Factory<SetupViewModel> {
  private final Provider<AuthRepository> repositoryProvider;

  private final Provider<ProfileImageStorage> imageStorageProvider;

  public SetupViewModel_Factory(Provider<AuthRepository> repositoryProvider,
      Provider<ProfileImageStorage> imageStorageProvider) {
    this.repositoryProvider = repositoryProvider;
    this.imageStorageProvider = imageStorageProvider;
  }

  @Override
  public SetupViewModel get() {
    return newInstance(repositoryProvider.get(), imageStorageProvider.get());
  }

  public static SetupViewModel_Factory create(Provider<AuthRepository> repositoryProvider,
      Provider<ProfileImageStorage> imageStorageProvider) {
    return new SetupViewModel_Factory(repositoryProvider, imageStorageProvider);
  }

  public static SetupViewModel newInstance(AuthRepository repository,
      ProfileImageStorage imageStorage) {
    return new SetupViewModel(repository, imageStorage);
  }
}
