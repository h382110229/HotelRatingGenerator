package com.hawk.hotelgenerator.ui.screens.settings;

import com.hawk.hotelgenerator.data.repository.ProviderRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  public SettingsViewModel_Factory(Provider<ProviderRepository> providerRepositoryProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(providerRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new SettingsViewModel_Factory(providerRepositoryProvider);
  }

  public static SettingsViewModel newInstance(ProviderRepository providerRepository) {
    return new SettingsViewModel(providerRepository);
  }
}
