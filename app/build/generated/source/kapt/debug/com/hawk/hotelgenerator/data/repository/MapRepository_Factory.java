package com.hawk.hotelgenerator.data.repository;

import android.content.Context;
import com.hawk.hotelgenerator.data.remote.AmapApiService;
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
public final class MapRepository_Factory implements Factory<MapRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<AmapApiService> amapApiServiceProvider;

  public MapRepository_Factory(Provider<Context> contextProvider,
      Provider<AmapApiService> amapApiServiceProvider) {
    this.contextProvider = contextProvider;
    this.amapApiServiceProvider = amapApiServiceProvider;
  }

  @Override
  public MapRepository get() {
    return newInstance(contextProvider.get(), amapApiServiceProvider.get());
  }

  public static MapRepository_Factory create(Provider<Context> contextProvider,
      Provider<AmapApiService> amapApiServiceProvider) {
    return new MapRepository_Factory(contextProvider, amapApiServiceProvider);
  }

  public static MapRepository newInstance(Context context, AmapApiService amapApiService) {
    return new MapRepository(context, amapApiService);
  }
}
