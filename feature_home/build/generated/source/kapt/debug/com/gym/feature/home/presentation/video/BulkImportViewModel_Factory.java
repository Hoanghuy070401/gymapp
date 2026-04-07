package com.gym.feature.home.presentation.video;

import com.gym.feature.home.data.importer.BulkImporterService;
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
public final class BulkImportViewModel_Factory implements Factory<BulkImportViewModel> {
  private final Provider<BulkImporterService> serviceProvider;

  public BulkImportViewModel_Factory(Provider<BulkImporterService> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  @Override
  public BulkImportViewModel get() {
    return newInstance(serviceProvider.get());
  }

  public static BulkImportViewModel_Factory create(Provider<BulkImporterService> serviceProvider) {
    return new BulkImportViewModel_Factory(serviceProvider);
  }

  public static BulkImportViewModel newInstance(BulkImporterService service) {
    return new BulkImportViewModel(service);
  }
}
