package com.gym.feature.home.data.importer;

import com.gym.feature.home.data.VideoRepository;
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
public final class BulkImporterService_Factory implements Factory<BulkImporterService> {
  private final Provider<VideoRepository> videoRepositoryProvider;

  public BulkImporterService_Factory(Provider<VideoRepository> videoRepositoryProvider) {
    this.videoRepositoryProvider = videoRepositoryProvider;
  }

  @Override
  public BulkImporterService get() {
    return newInstance(videoRepositoryProvider.get());
  }

  public static BulkImporterService_Factory create(
      Provider<VideoRepository> videoRepositoryProvider) {
    return new BulkImporterService_Factory(videoRepositoryProvider);
  }

  public static BulkImporterService newInstance(VideoRepository videoRepository) {
    return new BulkImporterService(videoRepository);
  }
}
