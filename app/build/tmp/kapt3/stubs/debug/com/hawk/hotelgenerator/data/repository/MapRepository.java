package com.hawk.hotelgenerator.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u000b\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\b0\fH\u0087@\u00a2\u0006\u0002\u0010\u000fJ2\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0011\u001a\u00020\b2\b\b\u0002\u0010\u0012\u001a\u00020\b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/hawk/hotelgenerator/data/repository/MapRepository;", "", "context", "Landroid/content/Context;", "amapApiService", "Lcom/hawk/hotelgenerator/data/remote/AmapApiService;", "(Landroid/content/Context;Lcom/hawk/hotelgenerator/data/remote/AmapApiService;)V", "apiKey", "", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "getNearbyHotels", "Lkotlin/Pair;", "", "Lcom/hawk/hotelgenerator/data/model/HotelPoi;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchHotels", "keyword", "city", "userLocation", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MapRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.remote.AmapApiService amapApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiKey = "4bd0f180646b62102c49f6c0186c85bb";
    
    @javax.inject.Inject()
    public MapRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.remote.AmapApiService amapApiService) {
        super();
    }
    
    /**
     * 模糊搜索酒店名称 (支持位置权重)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object searchHotels(@org.jetbrains.annotations.NotNull()
    java.lang.String keyword, @org.jetbrains.annotations.NotNull()
    java.lang.String city, @org.jetbrains.annotations.Nullable()
    java.lang.String userLocation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hawk.hotelgenerator.data.model.HotelPoi>> $completion) {
        return null;
    }
    
    /**
     * 获取当前位置并返回周边酒店列表
     */
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getNearbyHotels(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Pair<? extends java.util.List<com.hawk.hotelgenerator.data.model.HotelPoi>, java.lang.String>> $completion) {
        return null;
    }
}