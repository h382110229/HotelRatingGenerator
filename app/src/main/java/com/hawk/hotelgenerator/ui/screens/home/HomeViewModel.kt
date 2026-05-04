package com.hawk.hotelgenerator.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawk.hotelgenerator.data.model.HotelRatingParams
import com.hawk.hotelgenerator.data.model.ReviewStyle
import com.hawk.hotelgenerator.data.repository.HistoryRepository
import com.hawk.hotelgenerator.data.repository.LlmRepository
import com.hawk.hotelgenerator.data.repository.MapRepository
import com.hawk.hotelgenerator.data.repository.ReviewHistory
import com.hawk.hotelgenerator.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 首页 UI 状态数据类
 */
data class HomeUiState(
    val hotelName: String = "",
    val roomType: String = "",
    val stayDate: String = "",
    val hygiene: Float = 5f,
    val environment: Float = 5f,
    val facilities: Float = 5f,
    val service: Float = 5f,
    val selectedStyle: ReviewStyle = ReviewStyle.ELEGANT,
    val selectedImages: List<Uri> = emptyList(),
    val isGenerating: Boolean = false,
    val generatedResult: String? = null,
    val userLocation: String? = null, // 经纬度 "lon,lat"
    val error: String? = null
)

/**
 * 首页 ViewModel
 * 
 * 负责管理酒店点评生成的核心流程，包括：
 * 1. 酒店模糊搜索建议的实时拉取（带防抖）。
 * 2. 设备地理位置的权限校验与获取。
 * 3. 驱动大模型生成评价并持久化存储至历史记录。
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val llmRepository: LlmRepository,
    private val mapRepository: MapRepository,
    private val historyRepository: HistoryRepository,
    private val userPrefsRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _customRoomTypes = MutableStateFlow<Set<String>>(emptySet())
    val customRoomTypes: StateFlow<Set<String>> = _customRoomTypes.asStateFlow()

    init {
        viewModelScope.launch {
            userPrefsRepository.customRoomTypes.collect { types ->
                _customRoomTypes.value = types
            }
        }
    }

    private val _searchResults = MutableStateFlow<List<com.hawk.hotelgenerator.data.model.HotelPoi>>(emptyList())
    val searchResults: StateFlow<List<com.hawk.hotelgenerator.data.model.HotelPoi>> = _searchResults.asStateFlow()

    private var searchJob: kotlinx.coroutines.Job? = null

    fun updateHotelName(name: String) {
        _uiState.value = _uiState.value.copy(hotelName = name)
        
        // 防抖处理：取消之前的搜索任务
        searchJob?.cancel()
        
        if (name.length >= 2) {
            searchJob = viewModelScope.launch {
                // 延迟 500ms 触发搜索
                kotlinx.coroutines.delay(500)
                searchHotels(name)
            }
        } else {
            _searchResults.value = emptyList()
        }
    }

    private fun searchHotels(keyword: String) {
        viewModelScope.launch {
            val results = mapRepository.searchHotels(
                keyword = keyword,
                userLocation = _uiState.value.userLocation
            )
            _searchResults.value = results
        }
    }

    fun selectHotel(poi: com.hawk.hotelgenerator.data.model.HotelPoi) {
        _uiState.value = _uiState.value.copy(hotelName = poi.title)
        _searchResults.value = emptyList()
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }

    fun useCurrentLocation() {
        viewModelScope.launch {
            // 给用户一个即时反馈
            _uiState.value = _uiState.value.copy(error = "正在定位并搜索附近酒店...")
            
            val (pois, locationOrError) = mapRepository.getNearbyHotels()
            if (pois.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(
                    userLocation = locationOrError,
                    error = null
                )
                _searchResults.value = pois
            } else {
                _uiState.value = _uiState.value.copy(error = locationOrError ?: "附近未找到酒店")
            }
        }
    }

    fun updateRoomType(type: String) {
        _uiState.value = _uiState.value.copy(roomType = type)
        // If it's a new custom type (and not empty), save it to preferences
        if (type.isNotBlank()) {
            viewModelScope.launch {
                userPrefsRepository.addCustomRoomType(type)
            }
        }
    }

    fun updateStayDate(date: String) {
        _uiState.value = _uiState.value.copy(stayDate = date)
    }

    fun updateHygiene(value: Float) {
        _uiState.value = _uiState.value.copy(hygiene = value)
    }

    fun updateEnvironment(value: Float) {
        _uiState.value = _uiState.value.copy(environment = value)
    }

    fun updateFacilities(value: Float) {
        _uiState.value = _uiState.value.copy(facilities = value)
    }

    fun updateService(value: Float) {
        _uiState.value = _uiState.value.copy(service = value)
    }

    fun updateStyle(style: ReviewStyle) {
        _uiState.value = _uiState.value.copy(selectedStyle = style)
    }

    fun addImages(uris: List<Uri>) {
        val current = _uiState.value.selectedImages.toMutableList()
        current.addAll(uris)
        _uiState.value = _uiState.value.copy(selectedImages = current.take(9))
    }

    fun removeImage(index: Int) {
        val current = _uiState.value.selectedImages.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            _uiState.value = _uiState.value.copy(selectedImages = current)
        }
    }

    /**
     * 调用 AI 生成点评
     */
    fun generateReview() {
        val state = _uiState.value
        if (state.hotelName.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "请输入酒店名称")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, error = null, generatedResult = null)
            
            val params = HotelRatingParams(
                hotelName = state.hotelName,
                roomType = state.roomType,
                stayDate = state.stayDate,
                hygiene = state.hygiene.toInt(),
                environment = state.environment.toInt(),
                facilities = state.facilities.toInt(),
                service = state.service.toInt(),
                style = state.selectedStyle,
                wordCount = 350 // 确保 300+
            )
            
            val result = llmRepository.generateHotelReview(params, state.selectedImages)
            
            result.onSuccess { content ->
                _uiState.value = _uiState.value.copy(isGenerating = false, generatedResult = content)
                // 保存到历史记录
                viewModelScope.launch {
                    historyRepository.addHistory(
                        ReviewHistory(
                            id = java.util.UUID.randomUUID().toString(),
                            hotelName = state.hotelName,
                            date = System.currentTimeMillis(),
                            content = content
                        )
                    )
                }
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(isGenerating = false, error = exception.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
