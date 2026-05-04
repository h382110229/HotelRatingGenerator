package com.hawk.hotelgenerator.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.hawk.hotelgenerator.data.model.HotelRatingParams
import com.hawk.hotelgenerator.data.remote.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 大模型仓库 - 处理酒店点评生成的业务逻辑
 *
 * 该类负责组装提示词、处理图片压缩、调用远程 API 以及过滤响应内容。
 * 重点围绕：卫生、环境、设施、服务 四个核心维度。
 */
/**
 * 大模型仓库 - 处理酒店点评生成的业务逻辑
 *
 * 该类负责组装提示词、处理图片压缩、调用远程 API 以及过滤响应内容。
 * 重点围绕：卫生、环境、设施、服务 四个核心维度，并支持多模态视觉识别。
 * 
 * @property apiService 大模型请求服务接口
 * @property providerRepository 模型配置仓库
 * @property context 应用程序上下文，用于图片处理
 */
@Singleton
class LlmRepository @Inject constructor(
    private val apiService: com.hawk.hotelgenerator.data.remote.ApiService,
    private val providerRepository: com.hawk.hotelgenerator.data.repository.ProviderRepository,
    @ApplicationContext private val context: Context
) {
    /**
     * 生成酒店点评
     */
    suspend fun generateHotelReview(
        params: HotelRatingParams,
        imageUris: List<Uri>
    ): Result<String> {
        val config = providerRepository.currentConfig.first()
        return try {
            val messages = buildHotelMessages(params, imageUris)
            val baseUrl = if (config.baseUrl.isNotBlank()) config.baseUrl else Secrets.longcatBaseUrl
            val url = "${baseUrl.trimEnd('/')}/chat/completions"
            
            val isHawkProxy = baseUrl.contains("hawk-ai-proxy")
            val effectiveKey = if (config.apiKey.isNotBlank()) config.apiKey else {
                if (isHawkProxy) Secrets.builtinToken else Secrets.longcatApiKey
            }
            val auth = if (isHawkProxy) effectiveKey else "Bearer $effectiveKey"
            
            val request = ChatRequest(
                model = if (config.model.isNotBlank()) config.model else Secrets.longcatModel,
                messages = messages,
                temperature = 0.8,
                maxTokens = 2500
            )
            
            val response = if (isHawkProxy) {
                apiService.chatCompletionsHawk(url, auth, request)
            } else {
                apiService.chatCompletions(url, auth, request)
            }
            
            if (response.error != null) {
                Result.failure(Exception(response.error.message))
            } else {
                val rawContent = response.choices?.firstOrNull()?.message?.content
                if (rawContent != null) {
                    // 过滤思考标签并清理空白
                    val content = rawContent
                        .replace(Regex("<thought>[\\s\\S]*?</thought>"), "")
                        .replace(Regex("<think>[\\s\\S]*?</think>"), "")
                        .trim()
                    Result.success(content)
                } else {
                    Result.failure(Exception("AI 未返回有效内容"))
                }
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception("API 错误 (${e.code()}): $errorBody"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 组装酒店点评提示词
     *
     * 强制围绕卫生、环境、设施和服务四个维度，并确保字数在 300 字以上。
     */
    private fun buildHotelMessages(
        params: HotelRatingParams,
        imageUris: List<Uri>
    ): List<ChatMessage> {
        val systemMsg = ChatMessage(
            role = "system",
            content = """
                你是一位资深的豪华酒店体验官和旅行博主。你擅长以真实、细腻且富有美感的文字描述酒店入住体验。
                
                【重要：多模态视觉识别任务】
                如果用户提供了图片，请你化身为视觉侦探，捕捉图片中的关键信息并融入评价：
                1. 材质与品牌：识别大理石、黄铜、实木等材质，以及戴森、宝格丽、Diptyque 等品牌细节。
                2. 构图与氛围：描述窗外的景观（如江景、山景）、房间的光影、欢迎礼的质感。
                3. 卫生线索：观察浴室的透亮感、床品的平整度、金属件的闪亮程度。
                
                将这些视觉细节自然融入以下四个核心维度：
                1. 卫生 (Hygiene)：包括床品、卫浴、死角的清洁程度。
                2. 环境 (Environment)：包括隔音、视野、公区氛围、周边环境。
                3. 设施 (Facilities)：包括智能控制、健身房/泳池、家具质感。
                4. 服务 (Service)：包括前台响应、礼宾服务、打扫细节（如夜床服务的精致度）。
                
                要求：
                - 评价字数必须在 300 字以上，且内容必须与图片高度契合。
                - 严禁使用“图片1显示了...”这种机械表述，应使用“推开门，落地窗外的江景瞬间治愈...”等博主语调。
                - 语言要自然、有温度，结合用户选择的风格（${params.style.displayName}）和评分调整语气。
                - 适当使用 emoji 增强可读性。
            """.trimIndent()
        )

        val promptBuilder = StringBuilder()
        promptBuilder.appendLine("🏨 酒店名称：${params.hotelName}")
        if (params.roomType.isNotBlank()) promptBuilder.appendLine("🛏️ 入住房型：${params.roomType}")
        if (params.stayDate.isNotBlank()) promptBuilder.appendLine("📅 入住日期：${params.stayDate}")
        promptBuilder.appendLine()
        promptBuilder.appendLine("📊 核心评分：")
        promptBuilder.appendLine("- 卫生：${params.hygiene}/5")
        promptBuilder.appendLine("- 环境：${params.environment}/5")
        promptBuilder.appendLine("- 设施：${params.facilities}/5")
        promptBuilder.appendLine("- 服务：${params.service}/5")
        promptBuilder.appendLine()
        promptBuilder.appendLine("🎨 点评风格：${params.style.displayName}")
        promptBuilder.appendLine("💡 风格引导：${params.style.promptHint}")
        
        val userContent: Any = if (imageUris.isNotEmpty()) {
            val parts = mutableListOf(ContentPart(type = "text", text = promptBuilder.toString()))
            for (uri in imageUris) {
                val base64 = compressAndEncodeImage(uri)
                if (base64 != null) {
                    parts.add(ContentPart(
                        type = "image_url",
                        imageUrl = ImageUrl(url = "data:image/jpeg;base64,$base64")
                    ))
                }
            }
            parts
        } else {
            promptBuilder.toString()
        }

        return listOf(systemMsg, ChatMessage(role = "user", content = userContent))
    }

    /**
     * 图片本地压缩与 Base64 编码
     *
     * 为了提高 AI 识别效率，图片会被缩放到最大 800px 宽度，并以 70% 质量进行 JPEG 压缩。
     */
    private fun compressAndEncodeImage(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            
            // 缩放到最大 800px
            val maxW = 800
            val scaled = if (bitmap.width > maxW) {
                val ratio = maxW.toFloat() / bitmap.width
                Bitmap.createScaledBitmap(bitmap, maxW, (bitmap.height * ratio).toInt(), true)
            } else bitmap
            
            val baos = ByteArrayOutputStream()
            scaled.compress(Bitmap.CompressFormat.JPEG, 70, baos)
            Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }
}
