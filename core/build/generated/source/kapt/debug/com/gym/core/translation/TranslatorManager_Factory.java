package com.gym.core.translation;

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
public final class TranslatorManager_Factory implements Factory<TranslatorManager> {
  @Override
  public TranslatorManager get() {
    return newInstance();
  }

  public static TranslatorManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static TranslatorManager newInstance() {
    return new TranslatorManager();
  }

  private static final class InstanceHolder {
    private static final TranslatorManager_Factory INSTANCE = new TranslatorManager_Factory();
  }
}
