package com.hawk.hotelgenerator.ui.screens.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.hawk.hotelgenerator.R
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.hawk.hotelgenerator.ui.theme.*

/**
 * 酒店点评首页 - 实现 Google Stich 的 "Imperial Nocturne" 静谧奢华设计风格
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            viewModel.useCurrentLocation()
        } else {
            // Permission denied
        }
    }

    Scaffold(
        topBar = { HawkTopBar() },
        bottomBar = { 
            HawkBottomNavBar(
                onHistoryClick = onNavigateToHistory,
                onSettingsClick = onNavigateToSettings
            ) 
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBackground
    ) { padding ->
        val searchResults by viewModel.searchResults.collectAsState()
        val customRoomTypes by viewModel.customRoomTypes.collectAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // 标题
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "撰写您的点评",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnSurfaceWhite
                    )
                    Text(
                        text = "详细描述您的入住体验。我们的引擎将为您生成专业、高水准的点评。",
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnSurfaceWhite.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
            }

            // 1. 酒店基本信息 (Luxury Card)
            item {
                LuxuryCard {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // 酒店名称 (必填) + 定位
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            LuxuryInputField(
                                value = uiState.hotelName,
                                onValueChange = { viewModel.updateHotelName(it) },
                                label = "酒店名称",
                                placeholder = "例如：巴黎半岛酒店",
                                icon = Icons.Default.Domain,
                                isMandatory = true,
                                trailingIcon = {
                                    val context = androidx.compose.ui.platform.LocalContext.current
                                    IconButton(onClick = { 
                                        val hasFine = androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
                                        val hasCoarse = androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
                                        
                                        if (hasFine || hasCoarse) {
                                            viewModel.useCurrentLocation()
                                        } else {
                                            locationPermissionLauncher.launch(
                                                arrayOf(
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                                                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                                                )
                                            )
                                        }
                                    }) {
                                        Icon(Icons.Default.MyLocation, null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                                    }
                                }
                            )
                            
                            // 嵌入式搜索结果列表 (In-line Results)
                            // 嵌入式搜索结果列表 (In-line Results)
                            AnimatedVisibility(
                                visible = searchResults.isNotEmpty(),
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(OnSurfaceWhite.copy(alpha = 0.05f))
                                        .border(1.dp, GoldPrimary.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                                        .padding(vertical = 8.dp)
                                ) {
                                    searchResults.take(5).forEach { poi ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { viewModel.selectHotel(poi) }
                                                .padding(horizontal = 16.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.LocationOn,
                                                null,
                                                tint = GoldPrimary.copy(alpha = 0.6f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = poi.title,
                                                    color = GoldPrimary,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                                if (poi.address.isNotBlank()) {
                                                    Text(
                                                        text = poi.address,
                                                        color = OnSurfaceWhite.copy(alpha = 0.5f),
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }
                                        }
                                        if (poi != searchResults.take(5).lastOrNull()) {
                                            HorizontalDivider(
                                                modifier = Modifier.padding(horizontal = 16.dp),
                                                thickness = 0.5.dp,
                                                color = OnSurfaceWhite.copy(alpha = 0.1f)
                                            )
                                        }
                                    }
                                    
                                    // 关闭按钮
                                    TextButton(
                                        onClick = { viewModel.clearSearchResults() },
                                        modifier = Modifier.align(Alignment.End).padding(horizontal = 8.dp)
                                    ) {
                                        Text("关闭", color = OnSurfaceWhite.copy(alpha = 0.4f), fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            // 房型
                            Box(modifier = Modifier.weight(1f)) {
                                var roomTypeExpanded by remember { mutableStateOf(false) }
                                val defaultRoomTypes = listOf("大床房", "双床房", "高级大床房", "豪华套房", "行政套房", "总统套房")
                                val allRoomTypes = (defaultRoomTypes + customRoomTypes).distinct()

                                ExposedDropdownMenuBox(
                                    expanded = roomTypeExpanded,
                                    onExpandedChange = { roomTypeExpanded = it }
                                ) {
                                    LuxuryInputField(
                                        value = uiState.roomType,
                                        onValueChange = { viewModel.updateRoomType(it) },
                                        label = "房型",
                                        placeholder = "例如：行政套房",
                                        icon = Icons.Default.Bed,
                                        modifier = Modifier.menuAnchor(),
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = roomTypeExpanded)
                                        }
                                    )
                                    ExposedDropdownMenu(
                                        expanded = roomTypeExpanded,
                                        onDismissRequest = { roomTypeExpanded = false },
                                        modifier = Modifier.background(DarkBackground)
                                    ) {
                                        allRoomTypes.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption, color = OnSurfaceWhite) },
                                                onClick = {
                                                    viewModel.updateRoomType(selectionOption)
                                                    roomTypeExpanded = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                            )
                                        }
                                    }
                                }
                            }
                            // 日期 (点击弹出 DatePickerDialog)
                            Box(modifier = Modifier.weight(1f)) {
                                var showDatePicker by remember { mutableStateOf(false) }

                                LuxuryInputField(
                                    value = uiState.stayDate,
                                    onValueChange = { },
                                    readOnly = true,
                                    label = "入住日期",
                                    placeholder = "2024-05-01",
                                    icon = Icons.Default.CalendarMonth,
                                    onClick = { showDatePicker = true }
                                )

                                val datePickerState = rememberDatePickerState(
                                    initialDisplayMode = DisplayMode.Picker
                                )
                                if (showDatePicker) {
                                    DatePickerDialog(
                                        onDismissRequest = { showDatePicker = false },
                                        confirmButton = {
                                            TextButton(onClick = {
                                                datePickerState.selectedDateMillis?.let { millis ->
                                                    val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(millis))
                                                    viewModel.updateStayDate(dateStr)
                                                }
                                                showDatePicker = false
                                            }) {
                                                Text("确定", color = GoldPrimary)
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = { showDatePicker = false }) {
                                                Text("取消", color = OnSurfaceWhite)
                                            }
                                        },
                                        colors = DatePickerDefaults.colors(
                                            containerColor = DarkBackground,
                                        )
                                    ) {
                                        DatePicker(
                                            state = datePickerState,
                                            colors = DatePickerDefaults.colors(
                                                titleContentColor = GoldPrimary,
                                                headlineContentColor = OnSurfaceWhite,
                                                weekdayContentColor = OnSurfaceWhite.copy(alpha = 0.6f),
                                                navigationContentColor = GoldPrimary,
                                                yearContentColor = OnSurfaceWhite,
                                                currentYearContentColor = GoldPrimary,
                                                selectedYearContentColor = DarkBackground,
                                                selectedYearContainerColor = GoldPrimary,
                                                dayContentColor = OnSurfaceWhite,
                                                selectedDayContentColor = DarkBackground,
                                                selectedDayContainerColor = GoldPrimary,
                                                todayContentColor = GoldPrimary,
                                                todayDateBorderColor = GoldPrimary
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2. 评价焦点 (Sliders)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "评价焦点",
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurfaceWhite
                    )
                    LuxuryCard {
                        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                            LuxurySlider(label = "卫生与清洁", value = uiState.hygiene, onValueChange = { viewModel.updateHygiene(it) })
                            LuxurySlider(label = "氛围与环境", value = uiState.environment, onValueChange = { viewModel.updateEnvironment(it) })
                            LuxurySlider(label = "设施与设备", value = uiState.facilities, onValueChange = { viewModel.updateFacilities(it) })
                            LuxurySlider(label = "服务与人员", value = uiState.service, onValueChange = { viewModel.updateService(it) })
                        }
                    }
                }
            }

            // 3. 视觉内容 (Photo Grid)
            item {
                PhotoSection(
                    images = uiState.selectedImages,
                    onAddImages = { viewModel.addImages(it) },
                    onRemove = { viewModel.removeImage(it) }
                )
            }

            // 4. 生成按钮
            item {
                GenerateButton(
                    isGenerating = uiState.isGenerating,
                    onClick = { viewModel.generateReview() }
                )
            }

            // 5. 生成结果
            if (uiState.generatedResult != null || uiState.isGenerating) {
                item {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically()
                    ) {
                        ResultCard(
                            result = uiState.generatedResult,
                            onCopy = { 
                                uiState.generatedResult?.let { 
                                    clipboardManager.setText(AnnotatedString(it))
                                }
                            }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        if (uiState.error != null) {
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                title = { Text("服务提醒", color = GoldPrimary) },
                text = { Text(uiState.error ?: "未知错误", color = OnSurfaceWhite) },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("确定", color = GoldPrimary)
                    }
                },
                containerColor = SurfaceLuxury
            )
        }
    }
}

@Composable
fun HawkTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(64.dp)
            .background(DarkBackground.copy(alpha = 0.95f))
            .drawBehind {
                // 顶部极其细微的金色边线
                drawLine(
                    color = GoldPrimary.copy(alpha = 0.2f),
                    start = androidx.compose.ui.geometry.Offset(0f, size.height),
                    end = androidx.compose.ui.geometry.Offset(size.width, size.height),
                    strokeWidth = 0.5.dp.toPx()
                )
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.MenuOpen, 
            null, 
            tint = GoldPrimary.copy(alpha = 0.7f), 
            modifier = Modifier.size(22.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Text(
                text = "酒店评价生成器",
                style = MaterialTheme.typography.titleMedium,
                color = OnSurfaceWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            )
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.05f))
                .border(0.5.dp, GoldPrimary.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(
                Icons.Outlined.Person, 
                null, 
                tint = GoldPrimary, 
                modifier = Modifier.size(18.dp).align(Alignment.Center)
            )
        }
    }
}

@Composable
fun LuxuryCard(content: @Composable () -> Unit) {
    Surface(
        color = SurfaceLuxury.copy(alpha = 0.6f), // 玻璃质感
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)), // 极细边框
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                // 模拟极其微弱的金色光泽
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    ),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx())
                )
            },
        content = content
    )
}

@Composable
fun LuxuryInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    isMandatory: Boolean = false,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = if (isMandatory) GoldPrimary else OnSurfaceWhite.copy(alpha = 0.6f)
            )
            if (isMandatory) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary)
                )
            }
        }
        Box(modifier = modifier) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                readOnly = readOnly,
                placeholder = { Text(placeholder, color = OnSurfaceWhite.copy(alpha = 0.2f), fontSize = 14.sp) },
                leadingIcon = { Icon(icon, null, tint = GoldPrimary.copy(alpha = 0.4f), modifier = Modifier.size(18.dp)) },
                trailingIcon = trailingIcon,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.04f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.02f),
                    focusedIndicatorColor = GoldPrimary,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.05f),
                    cursorColor = GoldPrimary,
                    focusedTextColor = OnSurfaceWhite,
                    unfocusedTextColor = OnSurfaceWhite.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(onClick = onClick)
                )
            }
        }
    }
}

@Composable
fun LuxurySlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceWhite)
            Text(
                text = when(value.toInt()) {
                    5 -> "完美"
                    4 -> "卓越"
                    3 -> "出色"
                    2 -> "一般"
                    else -> "欠佳"
                },
                style = MaterialTheme.typography.labelSmall,
                color = GoldPrimary
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 1f..5f,
            steps = 3,
            colors = SliderDefaults.colors(
                thumbColor = GoldPrimary,
                activeTrackColor = GoldPrimary,
                inactiveTrackColor = Color.White.copy(alpha = 0.1f)
            )
        )
    }
}

@Composable
fun PhotoSection(images: List<Uri>, onAddImages: (List<Uri>) -> Unit, onRemove: (Int) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris -> onAddImages(uris) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            Text("视觉内容", style = MaterialTheme.typography.titleMedium, color = OnSurfaceWhite)
            Text("可选", style = MaterialTheme.typography.labelSmall, color = OnSurfaceWhite.copy(alpha = 0.4f))
        }
        
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // 上传按钮
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AddPhotoAlternate, null, tint = OnSurfaceWhite.copy(alpha = 0.4f))
                    Text("上传照片", style = MaterialTheme.typography.labelSmall, color = OnSurfaceWhite.copy(alpha = 0.4f))
                }
            }

            images.forEachIndexed { index, uri ->
                Box(modifier = Modifier.size(100.dp)) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { onRemove(index) },
                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(20.dp).background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, null, tint = Color.White, modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun GenerateButton(isGenerating: Boolean, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -500f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    val gradient = Brush.linearGradient(
        colors = listOf(
            GoldPrimary,
            Color(0xFFF5E1A4), // 更亮的亮金
            GoldPrimary
        ),
        start = androidx.compose.ui.geometry.Offset(shimmerOffset, 0f),
        end = androidx.compose.ui.geometry.Offset(shimmerOffset + 300f, 300f)
    )

    Button(
        onClick = onClick,
        enabled = !isGenerating,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawBehind {
                if (!isGenerating) {
                    drawRoundRect(
                        color = GoldPrimary.copy(alpha = 0.2f),
                        size = size,
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx()),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
                    )
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = DeepNavy,
            disabledContainerColor = GoldPrimary.copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isGenerating) SolidColor(GoldPrimary.copy(alpha = 0.5f)) else gradient)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isGenerating) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = DeepNavy, strokeWidth = 2.dp)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(18.dp))
                    Text(
                        "立即生成奢华点评",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                        color = DeepNavy
                    )
                }
            }
        }
    }
}

@Composable
fun ResultCard(result: String?, onCopy: () -> Unit) {
    LuxuryCard {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("生成的点评", style = MaterialTheme.typography.labelSmall, color = GoldPrimary)
                IconButton(onClick = onCopy, modifier = Modifier.size(32.dp).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))) {
                    Icon(Icons.Default.ContentCopy, null, tint = OnSurfaceWhite, modifier = Modifier.size(16.dp))
                }
            }
            if (result == null) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = GoldPrimary, trackColor = Color.White.copy(alpha = 0.05f))
            } else {
                Text(
                    text = result,
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceWhite,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun HawkBottomNavBar(
    onHomeClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    selectedItem: Int = 0
) {
    NavigationBar(
        containerColor = DarkBackground.copy(alpha = 0.95f),
        modifier = Modifier.height(80.dp),
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = onHomeClick,
            icon = { Icon(if (selectedItem == 0) Icons.Default.EditNote else Icons.Default.EditNote, null) },
            label = { Text("点评", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = GoldPrimary.copy(alpha = 0.1f),
                unselectedIconColor = OnSurfaceWhite.copy(alpha = 0.4f),
                unselectedTextColor = OnSurfaceWhite.copy(alpha = 0.4f)
            )
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = onHistoryClick,
            icon = { Icon(Icons.Default.History, null) },
            label = { Text("历史", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = GoldPrimary.copy(alpha = 0.1f),
                unselectedIconColor = OnSurfaceWhite.copy(alpha = 0.4f),
                unselectedTextColor = OnSurfaceWhite.copy(alpha = 0.4f)
            )
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = onSettingsClick,
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("设置", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                indicatorColor = GoldPrimary.copy(alpha = 0.1f),
                unselectedIconColor = OnSurfaceWhite.copy(alpha = 0.4f),
                unselectedTextColor = OnSurfaceWhite.copy(alpha = 0.4f)
            )
        )
    }
}
