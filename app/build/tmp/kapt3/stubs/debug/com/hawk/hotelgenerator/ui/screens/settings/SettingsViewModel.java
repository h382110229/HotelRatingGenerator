package com.hawk.hotelgenerator.ui.screens.settings;

/**
 * 设置页 ViewModel - 处理自定义模型配置逻辑
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/hawk/hotelgenerator/ui/screens/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "providerRepository", "Lcom/hawk/hotelgenerator/data/repository/ProviderRepository;", "(Lcom/hawk/hotelgenerator/data/repository/ProviderRepository;)V", "config", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/hawk/hotelgenerator/data/repository/ProviderConfig;", "getConfig", "()Lkotlinx/coroutines/flow/StateFlow;", "resetToDefault", "", "updateConfig", "newConfig", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.ProviderRepository providerRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hawk.hotelgenerator.data.repository.ProviderConfig> config = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.ProviderRepository providerRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hawk.hotelgenerator.data.repository.ProviderConfig> getConfig() {
        return null;
    }
    
    public final void updateConfig(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.ProviderConfig newConfig) {
    }
    
    public final void resetToDefault() {
    }
}