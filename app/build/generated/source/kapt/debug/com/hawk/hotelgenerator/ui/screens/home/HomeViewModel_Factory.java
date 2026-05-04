package com.hawk.hotelgenerator.ui.screens.home;

import com.hawk.hotelgenerator.data.repository.HistoryRepository;
import com.hawk.hotelgenerator.data.repository.LlmRepository;
import com.hawk.hotelgenerator.data.repository.MapRepository;
import com.hawk.hotelgenerator.data.repository.UserPreferencesRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<LlmRepository> llmRepositoryProvider;

  private final Provider<MapRepository> mapRepositoryProvider;

  private final Provider<HistoryRepository> historyRepositoryProvider;

  private final Provider<UserPreferencesRepository> userPrefsRepositoryProvider;

  public HomeViewModel_Factory(Provider<LlmRepository> llmRepositoryProvider,
      Provider<MapRepository> mapRepositoryProvider,
      Provider<HistoryRepository> historyRepositoryProvider,
      Provider<UserPreferencesRepository> userPrefsRepositoryProvider) {
    this.llmRepositoryProvider = llmRepositoryProvider;
    this.mapRepositoryProvider = mapRepositoryProvider;
    this.historyRepositoryProvider = historyRepositoryProvider;
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(llmRepositoryProvider.get(), mapRepositoryProvider.get(), historyRepositoryProvider.get(), userPrefsRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<LlmRepository> llmRepositoryProvider,
      Provider<MapRepository> mapRepositoryProvider,
      Provider<HistoryRepository> historyRepositoryProvider,
      Provider<UserPreferencesRepository> userPrefsRepositoryProvider) {
    return new HomeViewModel_Factory(llmRepositoryProvider, mapRepositoryProvider, historyRepositoryProvider, userPrefsRepositoryProvider);
  }

  public static HomeViewModel newInstance(LlmRepository llmRepository, MapRepository mapRepository,
      HistoryRepository historyRepository, UserPreferencesRepository userPrefsRepository) {
    return new HomeViewModel(llmRepository, mapRepository, historyRepository, userPrefsRepository);
  }
}
