package com.hawk.hotelgenerator.data.model

/**
 * 点评风格枚举
 *
 * @property displayName 显示名称
 * @property promptHint 对应的提示词引导
 */
enum class ReviewStyle(val displayName: String, val promptHint: String) {
    ELEGANT("从容优雅", "用辞讲究，突出酒店的格调与氛围感，给人一种高级感。"),
    PROFESSIONAL("专业严谨", "客观分析各项细节，逻辑清晰，适合作为深度商旅参考。"),
    CRITICAL("真实锐评", "直击痛点，不避讳缺点，但也给予公正的优点评价，极具参考价值。"),
    RELAXED("悠闲随性", "口语化，像朋友圈分享一样亲切自然。")
}

/**
 * 酒店点评核心维度数据类
 */
data class HotelRatingParams(
    val hotelName: String,
    val roomType: String = "",
    val stayDate: String = "",
    val hygiene: Int = 5,      // 卫生 (1-5)
    val environment: Int = 5,  // 环境 (1-5)
    val facilities: Int = 5,   // 设施 (1-5)
    val service: Int = 5,       // 服务 (1-5)
    val style: ReviewStyle = ReviewStyle.ELEGANT,
    val wordCount: Int = 300
)

/**
 * 酒店 POI 信息
 */
data class HotelPoi(
    val id: String,
    val title: String,
    val address: String = "",
    val cityName: String = "",
    val location: String = "" // "lon,lat"
)
