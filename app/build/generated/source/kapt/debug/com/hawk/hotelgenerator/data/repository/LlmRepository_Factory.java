package com.hawk.hotelgenerator.data.repository;

import android.content.Context;
import com.hawk.hotelgenerator.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class LlmRepository_Factory implements Factory<LlmRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<ProviderRepository> providerRepositoryProvider;

  private final Provider<Context> contextProvider;

  public LlmRepository_Factory(Provider<ApiService> apiServiceProvider,
      Provider<ProviderRepository> providerRepositoryProvider, Provider<Context> contextProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.providerRepositoryProvider = providerRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public LlmRepository get() {
    return newInstance(apiServiceProvider.get(), providerRepositoryProvider.get(), contextProvider.get());
  }

  public static LlmRepository_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<ProviderRepository> providerRepositoryProvider, Provider<Context> contextProvider) {
    return new LlmRepository_Factory(apiServiceProvider, providerRepositoryProvider, contextProvider);
  }

  public static LlmRepository newInstance(ApiService apiService,
      ProviderRepository providerRepository, Context context) {
    return new LlmRepository(apiService, providerRepository, context);
  }
}
