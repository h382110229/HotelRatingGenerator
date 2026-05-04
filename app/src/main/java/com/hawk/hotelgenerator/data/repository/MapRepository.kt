package com.hawk.hotelgenerator.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.hawk.hotelgenerator.BuildConfig
import com.hawk.hotelgenerator.data.model.HotelPoi
import com.hawk.hotelgenerator.data.remote.AmapApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 高德地图服务仓库
 * 
 * 该类封装了高德 REST API 的调用逻辑，并结合 Android 原生定位服务实现位置感知的搜索。
 * 
 * 核心功能：
 * 1. 结合 FusedLocationProviderClient 获取设备精确位置。
 * 2. 酒店模糊搜索支持位置权重 (Proximity Bias)。
 * 3. 逆地理编码实现经纬度到街道地址的转换。
 */
@Singleton
class MapRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val amapApiService: AmapApiService
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val apiKey = BuildConfig.AMAP_API_KEY

    /**
     * 模糊搜索酒店名称 (支持位置权重)
     */
    suspend fun searchHotels(keyword: String, city: String = "", userLocation: String? = null): List<HotelPoi> = withContext(Dispatchers.IO) {
        try {
            Log.d("MapRepository", "Web API Search: $keyword in $city, location: $userLocation")
            val response = amapApiService.searchPlace(
                keywords = keyword,
                city = city,
                key = apiKey,
                location = userLocation ?: ""
            )
            if (response.status == "1") {
                response.pois.map { 
                    HotelPoi(
                        id = it.id,
                        title = it.name,
                        address = it.getSafeAddress(),
                        cityName = it.getSafeCity(),
                        location = it.location
                    )
                }
            } else {
                Log.e("MapRepository", "Search failed: ${response.info}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MapRepository", "Search error", e)
            emptyList()
        }
    }

    /**
     * 获取当前位置并返回周边酒店列表
     */
    @SuppressLint("MissingPermission")
    suspend fun getNearbyHotels(): Pair<List<HotelPoi>, String?> = withContext(Dispatchers.IO) {
        try {
            Log.d("MapRepository", "Starting system location request for nearby hotels...")
            
            var location: android.location.Location? = null
            try {
                location = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).await()
            } catch (e: Exception) {
                Log.w("MapRepository", "FusedLocation failed", e)
            }

            if (location == null) {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
                val providers = locationManager.getProviders(true)
                for (provider in providers) {
                    val lastKnown = locationManager.getLastKnownLocation(provider)
                    if (lastKnown != null) {
                        if (location == null || lastKnown.accuracy < location.accuracy) {
                            location = lastKnown
                        }
                    }
                }
            }

            if (location != null) {
                val lonLat = "${location.longitude},${location.latitude}"
                Log.d("MapRepository", "Location found: $lonLat")

                // 搜索周边酒店
                val searchResponse = amapApiService.searchAround(
                    location = lonLat,
                    types = "住宿服务",
                    key = apiKey,
                    radius = 5000 // 扩大到 5km，给用户更多选择
                )

                if (searchResponse.status == "1" && searchResponse.pois.isNotEmpty()) {
                    val list = searchResponse.pois.map { poi ->
                        HotelPoi(
                            id = poi.id,
                            title = poi.name,
                            address = poi.getSafeAddress(),
                            cityName = poi.getSafeCity(),
                            location = poi.location
                        )
                    }
                    list to lonLat // 返回列表和坐标字符串
                } else {
                    emptyList<HotelPoi>() to "附近未找到酒店"
                }
            } else {
                emptyList<HotelPoi>() to "定位失败：无法获取经纬度。请确认已开启 GPS 并授予权限。"
            }
        } catch (e: Exception) {
            Log.e("MapRepository", "Nearby search error", e)
            emptyList<HotelPoi>() to "定位服务异常: ${e.localizedMessage}"
        }
    }
}
