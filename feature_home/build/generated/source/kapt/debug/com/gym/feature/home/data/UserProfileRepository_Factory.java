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
public final class UserProfileRepository_Factory implements Factory<UserProfileRepository> {
  @Override
  public UserProfileRepository get() {
    return newInstance();
  }

  public static UserProfileRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static UserProfileRepository newInstance() {
    return new UserProfileRepository();
  }

  private static final class InstanceHolder {
    private static final UserProfileRepository_Factory INSTANCE = new UserProfileRepository_Factory();
  }
}
