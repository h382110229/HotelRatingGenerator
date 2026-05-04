package com.hawk.hotelgenerator.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawk.hotelgenerator.ui.screens.home.HawkBottomNavBar
import com.hawk.hotelgenerator.ui.screens.home.HawkTopBar
import com.hawk.hotelgenerator.ui.screens.home.LuxuryCard
import com.hawk.hotelgenerator.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 历史记录页面 - 展示过往生成的酒店点评
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val historyItems by viewModel.historyItems.collectAsState()
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    Scaffold(
        topBar = { HawkTopBar() },
        bottomBar = {
            HawkBottomNavBar(
                onHomeClick = onNavigateToHome,
                onSettingsClick = onNavigateToSettings,
                selectedItem = 1
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "历史记录",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnSurfaceWhite
                    )
                    if (historyItems.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearAll() }) {
                            Icon(Icons.Default.DeleteSweep, null, tint = OnSurfaceWhite.copy(alpha = 0.4f))
                        }
                    }
                }
            }

            if (historyItems.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxHeight(0.7f), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.History, null, modifier = Modifier.size(64.dp), tint = OnSurfaceWhite.copy(alpha = 0.1f))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("暂无历史记录", color = OnSurfaceWhite.copy(alpha = 0.3f))
                        }
                    }
                }
            } else {
                items(historyItems) { item ->
                    LuxuryCard {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = item.hotelName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = GoldPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = dateFormat.format(Date(item.date)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceWhite.copy(alpha = 0.4f)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceWhite.copy(alpha = 0.8f),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
