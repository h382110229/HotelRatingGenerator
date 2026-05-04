package com.hawk.hotelgenerator.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ^\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u000b\u001a\u00020\u00052\b\b\u0003\u0010\f\u001a\u00020\u00052\b\b\u0003\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u000f\u001a\u00020\u000e2\b\b\u0003\u0010\u0010\u001a\u00020\u000e2\b\b\u0003\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011J^\u0010\u0012\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\u00052\b\b\u0003\u0010\f\u001a\u00020\u00052\b\b\u0003\u0010\u0013\u001a\u00020\u00052\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u000f\u001a\u00020\u000e2\b\b\u0003\u0010\u0010\u001a\u00020\u000e2\b\b\u0003\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/hawk/hotelgenerator/data/remote/AmapApiService;", "", "reverseGeocode", "Lcom/hawk/hotelgenerator/data/remote/AmapRegeoResponse;", "location", "", "key", "extensions", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchAround", "Lcom/hawk/hotelgenerator/data/remote/AmapSearchResponse;", "keywords", "types", "radius", "", "offset", "page", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchPlace", "city", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface AmapApiService {
    
    @retrofit2.http.GET(value = "v3/place/text")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchPlace(@retrofit2.http.Query(value = "keywords")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keywords, @retrofit2.http.Query(value = "types")
    @org.jetbrains.annotations.NotNull()
    java.lang.String types, @retrofit2.http.Query(value = "city")
    @org.jetbrains.annotations.NotNull()
    java.lang.String city, @retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.NotNull()
    java.lang.String location, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @retrofit2.http.Query(value = "offset")
    int offset, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "extensions")
    @org.jetbrains.annotations.NotNull()
    java.lang.String extensions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hawk.hotelgenerator.data.remote.AmapSearchResponse> $completion);
    
    @retrofit2.http.GET(value = "v3/place/around")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchAround(@retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.NotNull()
    java.lang.String location, @retrofit2.http.Query(value = "keywords")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keywords, @retrofit2.http.Query(value = "types")
    @org.jetbrains.annotations.NotNull()
    java.lang.String types, @retrofit2.http.Query(value = "radius")
    int radius, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @retrofit2.http.Query(value = "offset")
    int offset, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "extensions")
    @org.jetbrains.annotations.NotNull()
    java.lang.String extensions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hawk.hotelgenerator.data.remote.AmapSearchResponse> $completion);
    
    @retrofit2.http.GET(value = "v3/geocode/regeo")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object reverseGeocode(@retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.NotNull()
    java.lang.String location, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @retrofit2.http.Query(value = "extensions")
    @org.jetbrains.annotations.NotNull()
    java.lang.String extensions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hawk.hotelgenerator.data.remote.AmapRegeoResponse> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}