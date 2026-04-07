package com.gym.feature.home.presentation.video;

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
public final class AddVideoViewModel_Factory implements Factory<AddVideoViewModel> {
  private final Provider<VideoRepository> videoRepositoryProvider;

  public AddVideoViewModel_Factory(Provider<VideoRepository> videoRepositoryProvider) {
    this.videoRepositoryProvider = videoRepositoryProvider;
  }

  @Override
  public AddVideoViewModel get() {
    return newInstance(videoRepositoryProvider.get());
  }

  public static AddVideoViewModel_Factory create(
      Provider<VideoRepository> videoRepositoryProvider) {
    return new AddVideoViewModel_Factory(videoRepositoryProvider);
  }

  public static AddVideoViewModel newInstance(VideoRepository videoRepository) {
    return new AddVideoViewModel(videoRepository);
  }
}
