package com.hawk.hotelgenerator.data.remote

import com.hawk.hotelgenerator.BuildConfig

/**
 * 敏感信息存储类
 * 密钥存储在本地 local.properties 中，通过 build.gradle.kts 动态注入。
 */
object Secrets {
    val builtinToken: String = BuildConfig.HAWK_BUILTIN_TOKEN
    val longcatApiKey: String = BuildConfig.LONGCAT_API_KEY
    val longcatBaseUrl: String = BuildConfig.LONGCAT_BASE_URL
    val longcatModel: String = BuildConfig.LONGCAT_MODEL
}
