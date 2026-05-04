package com.hawk.hotelgenerator.data.remote

import com.google.gson.annotations.SerializedName

data class AmapSearchResponse(
    val status: String,
    val count: String,
    val info: String,
    val infocode: String,
    val pois: List<AmapPoi> = emptyList()
)

data class AmapPoi(
    val id: String,
    val name: String,
    val type: Any?,
    val location: String, // "116.481488,39.990464"
    val address: Any?,
    val cityname: Any?,
    val adname: Any?
) {
    fun getSafeAddress(): String = if (address is String) address else ""
    fun getSafeCity(): String = if (cityname is String) cityname else ""
    fun getSafeName(): String = name
}

data class AmapRegeoResponse(
    val status: String,
    val info: String,
    val regeocode: AmapRegeocode? = null
)

data class AmapRegeocode(
    @SerializedName("addressComponent")
    val addressComponent: AmapAddressComponent
)

data class AmapAddressComponent(
    val city: Any?, // Can be string or list if empty
    val province: String,
    val district: String
)
