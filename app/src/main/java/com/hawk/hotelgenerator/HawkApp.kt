package com.hawk.hotelgenerator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HawkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 高德地图原生 SDK 已弃用，改用 Web 服务 API，无需在此初始化隐私合规
    }
}
