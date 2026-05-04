package com.hawk.hotelgenerator.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawk.hotelgenerator.data.repository.ProviderConfig
import com.hawk.hotelgenerator.ui.screens.home.HawkBottomNavBar
import com.hawk.hotelgenerator.ui.screens.home.HawkTopBar
import com.hawk.hotelgenerator.ui.screens.home.LuxuryCard
import com.hawk.hotelgenerator.ui.screens.home.LuxuryInputField
import com.hawk.hotelgenerator.ui.theme.*

/**
 * 设置页面 - 允许用户自定义 AI 模型与 API 配置
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val config by viewModel.config.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { HawkTopBar() },
        bottomBar = {
            HawkBottomNavBar(
                onHomeClick = onNavigateToHome,
                onHistoryClick = onNavigateToHistory,
                selectedItem = 2
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    text = "配置与模型",
                    style = MaterialTheme.typography.titleLarge,
                    color = OnSurfaceWhite
                )
            }

            // API 配置卡片
            item {
                LuxuryCard {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        LuxuryInputField(
                            value = config.apiKey,
                            onValueChange = { viewModel.updateConfig(config.copy(apiKey = it)) },
                            label = "API Key",
                            placeholder = "输入您的 API 密钥",
                            icon = Icons.Default.Key
                        )
                        LuxuryInputField(
                            value = config.baseUrl,
                            onValueChange = { viewModel.updateConfig(config.copy(baseUrl = it)) },
                            label = "Base URL",
                            placeholder = "https://api.example.com/v1",
                            icon = Icons.Default.Link
                        )
                        LuxuryInputField(
                            value = config.model,
                            onValueChange = { viewModel.updateConfig(config.copy(model = it)) },
                            label = "模型名称",
                            placeholder = "例如：gpt-4o",
                            icon = Icons.Default.ModelTraining
                        )
                    }
                }
            }

            // 预设重置
            item {
                Button(
                    onClick = { viewModel.resetToDefault() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.05f),
                        contentColor = GoldPrimary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("重置为 LongCat 默认配置")
                }
            }

            // 关于
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Hawk Hotel Reviewer v1.0.8",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceWhite.copy(alpha = 0.4f)
                    )
                }
            }
        }
    }
}
