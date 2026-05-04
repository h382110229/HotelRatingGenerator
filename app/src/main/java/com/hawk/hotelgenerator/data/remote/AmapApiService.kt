package com.hawk.hotelgenerator.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AmapApiService {
    @GET("v3/place/text")
    suspend fun searchPlace(
        @Query("keywords") keywords: String,
        @Query("types") types: String = "",
        @Query("city") city: String = "",
        @Query("location") location: String = "", // 增加此参数
        @Query("key") key: String,
        @Query("offset") offset: Int = 20,
        @Query("page") page: Int = 1,
        @Query("extensions") extensions: String = "base"
    ): AmapSearchResponse

    @GET("v3/place/around")
    suspend fun searchAround(
        @Query("location") location: String, // "lon,lat"
        @Query("keywords") keywords: String = "",
        @Query("types") types: String = "住宿服务",
        @Query("radius") radius: Int = 2000,
        @Query("key") key: String,
        @Query("offset") offset: Int = 20,
        @Query("page") page: Int = 1,
        @Query("extensions") extensions: String = "base"
    ): AmapSearchResponse

    @GET("v3/geocode/regeo")
    suspend fun reverseGeocode(
        @Query("location") location: String, // "lon,lat"
        @Query("key") key: String,
        @Query("extensions") extensions: String = "base"
    ): AmapRegeoResponse
}
