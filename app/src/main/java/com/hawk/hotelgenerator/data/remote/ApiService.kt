package com.hawk.hotelgenerator.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * 远程 API 接口
 * 采用双方法设计以支持标准 OpenAI 格式和 Hawk 代理格式
 */
interface ApiService {
    
    /**
     * 标准 OpenAI 兼容接口 (使用 Bearer Token)
     */
    @POST
    suspend fun chatCompletions(
        @Url url: String,
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): ChatResponse

    /**
     * Hawk 代理专用接口 (使用 X-Hawk-Token)
     */
    @POST
    suspend fun chatCompletionsHawk(
        @Url url: String,
        @Header("X-Hawk-Token") hawkToken: String,
        @Body request: ChatRequest
    ): ChatResponse
}
