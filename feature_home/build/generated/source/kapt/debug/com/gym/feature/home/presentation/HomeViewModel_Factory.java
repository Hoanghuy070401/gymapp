package com.gym.feature.home.presentation;

import com.gym.feature.home.data.VideoRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<VideoRepository> videoRepositoryProvider;

  public HomeViewModel_Factory(Provider<VideoRepository> videoRepositoryProvider) {
    this.videoRepositoryProvider = videoRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(videoRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<VideoRepository> videoRepositoryProvider) {
    return new HomeViewModel_Factory(videoRepositoryProvider);
  }

  public static HomeViewModel newInstance(VideoRepository videoRepository) {
    return new HomeViewModel(videoRepository);
  }
}
