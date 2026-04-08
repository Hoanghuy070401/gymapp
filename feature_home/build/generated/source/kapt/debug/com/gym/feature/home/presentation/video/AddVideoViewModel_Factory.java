package com.gym.feature.home.presentation.video;

import com.gym.feature.home.data.VideoRepository;
import com.gym.feature.home.data.importer.YoutubeVideoValidator;
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

  private final Provider<YoutubeVideoValidator> youtubeValidatorProvider;

  public AddVideoViewModel_Factory(Provider<VideoRepository> videoRepositoryProvider,
      Provider<YoutubeVideoValidator> youtubeValidatorProvider) {
    this.videoRepositoryProvider = videoRepositoryProvider;
    this.youtubeValidatorProvider = youtubeValidatorProvider;
  }

  @Override
  public AddVideoViewModel get() {
    return newInstance(videoRepositoryProvider.get(), youtubeValidatorProvider.get());
  }

  public static AddVideoViewModel_Factory create(Provider<VideoRepository> videoRepositoryProvider,
      Provider<YoutubeVideoValidator> youtubeValidatorProvider) {
    return new AddVideoViewModel_Factory(videoRepositoryProvider, youtubeValidatorProvider);
  }

  public static AddVideoViewModel newInstance(VideoRepository videoRepository,
      YoutubeVideoValidator youtubeValidator) {
    return new AddVideoViewModel(videoRepository, youtubeValidator);
  }
}
