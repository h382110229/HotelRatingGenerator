package com.hawk.hotelgenerator.data.remote;

/**
 * 远程 API 接口
 * 采用双方法设计以支持标准 OpenAI 格式和 Hawk 代理格式
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ,\u0010\n\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u000b\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\f"}, d2 = {"Lcom/hawk/hotelgenerator/data/remote/ApiService;", "", "chatCompletions", "Lcom/hawk/hotelgenerator/data/remote/ChatResponse;", "url", "", "auth", "request", "Lcom/hawk/hotelgenerator/data/remote/ChatRequest;", "(Ljava/lang/String;Ljava/lang/String;Lcom/hawk/hotelgenerator/data/remote/ChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "chatCompletionsHawk", "hawkToken", "app_debug"})
public abstract interface ApiService {
    
    /**
     * 标准 OpenAI 兼容接口 (使用 Bearer Token)
     */
    @retrofit2.http.POST()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object chatCompletions(@retrofit2.http.Url()
    @org.jetbrains.annotations.NotNull()
    java.lang.String url, @retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String auth, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.remote.ChatRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hawk.hotelgenerator.data.remote.ChatResponse> $completion);
    
    /**
     * Hawk 代理专用接口 (使用 X-Hawk-Token)
     */
    @retrofit2.http.POST()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object chatCompletionsHawk(@retrofit2.http.Url()
    @org.jetbrains.annotations.NotNull()
    java.lang.String url, @retrofit2.http.Header(value = "X-Hawk-Token")
    @org.jetbrains.annotations.NotNull()
    java.lang.String hawkToken, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.remote.ChatRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hawk.hotelgenerator.data.remote.ChatResponse> $completion);
}