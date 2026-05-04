package com.hawk.hotelgenerator.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawk.hotelgenerator.data.repository.ProviderConfig
import com.hawk.hotelgenerator.data.repository.ProviderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 设置页 ViewModel - 处理自定义模型配置逻辑
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val providerRepository: ProviderRepository
) : ViewModel() {

    val config: StateFlow<ProviderConfig> = providerRepository.currentConfig
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProviderConfig())

    fun updateConfig(newConfig: ProviderConfig) {
        viewModelScope.launch {
            providerRepository.saveConfig(newConfig)
        }
    }

    fun resetToDefault() {
        updateConfig(ProviderConfig())
    }
}
