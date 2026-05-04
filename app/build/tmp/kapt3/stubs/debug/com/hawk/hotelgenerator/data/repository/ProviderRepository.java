package com.hawk.hotelgenerator.data.repository;

/**
 * 设置仓库 - 管理自定义模型与 API 配置
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/hawk/hotelgenerator/data/repository/ProviderRepository;", "", "context", "Landroid/content/Context;", "gson", "Lcom/google/gson/Gson;", "(Landroid/content/Context;Lcom/google/gson/Gson;)V", "CONFIG_KEY", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "currentConfig", "Lkotlinx/coroutines/flow/Flow;", "Lcom/hawk/hotelgenerator/data/repository/ProviderConfig;", "getCurrentConfig", "()Lkotlinx/coroutines/flow/Flow;", "saveConfig", "", "config", "(Lcom/hawk/hotelgenerator/data/repository/ProviderConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProviderRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> CONFIG_KEY = null;
    
    /**
     * 获取当前配置
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.hawk.hotelgenerator.data.repository.ProviderConfig> currentConfig = null;
    
    @javax.inject.Inject()
    public ProviderRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    /**
     * 获取当前配置
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.hawk.hotelgenerator.data.repository.ProviderConfig> getCurrentConfig() {
        return null;
    }
    
    /**
     * 保存新配置
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveConfig(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.ProviderConfig config, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}