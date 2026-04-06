package com.gym.feature.home.data;

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
public final class VideoRepository_Factory implements Factory<VideoRepository> {
  @Override
  public VideoRepository get() {
    return newInstance();
  }

  public static VideoRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static VideoRepository newInstance() {
    return new VideoRepository();
  }

  private static final class InstanceHolder {
    private static final VideoRepository_Factory INSTANCE = new VideoRepository_Factory();
  }
}
