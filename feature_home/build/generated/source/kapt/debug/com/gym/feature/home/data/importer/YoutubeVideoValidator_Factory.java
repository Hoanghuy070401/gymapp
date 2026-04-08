package com.gym.feature.home.data.importer;

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
public final class YoutubeVideoValidator_Factory implements Factory<YoutubeVideoValidator> {
  @Override
  public YoutubeVideoValidator get() {
    return newInstance();
  }

  public static YoutubeVideoValidator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static YoutubeVideoValidator newInstance() {
    return new YoutubeVideoValidator();
  }

  private static final class InstanceHolder {
    private static final YoutubeVideoValidator_Factory INSTANCE = new YoutubeVideoValidator_Factory();
  }
}
