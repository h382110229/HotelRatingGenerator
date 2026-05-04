package com.hawk.hotelgenerator.di;

import com.hawk.hotelgenerator.data.remote.AmapApiService;
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
public final class NetworkModule_ProvideAmapApiServiceFactory implements Factory<AmapApiService> {
  private final Provider<OkHttpClient> clientProvider;

  public NetworkModule_ProvideAmapApiServiceFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public AmapApiService get() {
    return provideAmapApiService(clientProvider.get());
  }

  public static NetworkModule_ProvideAmapApiServiceFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new NetworkModule_ProvideAmapApiServiceFactory(clientProvider);
  }

  public static AmapApiService provideAmapApiService(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideAmapApiService(client));
  }
}
