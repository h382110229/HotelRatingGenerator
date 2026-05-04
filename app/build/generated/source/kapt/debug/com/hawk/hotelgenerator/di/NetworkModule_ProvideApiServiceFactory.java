package com.hawk.hotelgenerator.di;

import com.hawk.hotelgenerator.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideApiServiceFactory implements Factory<ApiService> {
  private final Provider<OkHttpClient> clientProvider;

  public NetworkModule_ProvideApiServiceFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public ApiService get() {
    return provideApiService(clientProvider.get());
  }

  public static NetworkModule_ProvideApiServiceFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new NetworkModule_ProvideApiServiceFactory(clientProvider);
  }

  public static ApiService provideApiService(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideApiService(client));
  }
}
