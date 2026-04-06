package com.gym.feature.auth.presentation.setup

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.component.GymAvatar
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymTextField
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.auth.R
import kotlinx.coroutines.launch

/** Creates a MediaStore URI for a camera capture (image only). */

private fun createImageUri(context: Context): Uri {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/GymApp")
        }
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
}

@Composable
fun StepHeader(title: String, subtitle: String? = null, subtitleHasBackground: Boolean = true) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, 
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title, 
            style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold), 
            color = AppColors.OnSurface,
            modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(16.dp))
            if (subtitleHasBackground) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.TonalLavender)
                        .padding(vertical = 24.dp, horizontal = AppSpacing.ScreenHorizontal)
                ) {
                    Text(
                        text = subtitle, 
                        style = AppTypography.labelMedium, 
                        color = AppColors.Surface, 
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                Text(
                    text = subtitle,
                    style = AppTypography.bodySmall,
                    color = AppColors.OnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// 4-A: Setup Intro
@Composable
fun SetupIntroStep() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Top half image area with gradient and text overlaid
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.setup_intro_bg),
                contentDescription = "Setup Intro Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Simulating the gradient overlay from the mockup
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color.Transparent, AppColors.SurfaceContainerHigh)
                        )
                    )
            )
            Text(
                text = "Consistency Is\nThe Key To Progress.\nDon't Give Up!", 
                style = AppTypography.headlineMedium.copy(color = AppColors.ElectricLime, textAlign = TextAlign.Center),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        
        // Mockup Purple block
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(AppColors.TonalLavender)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Setup your profile to personalize your experience.", 
                style = AppTypography.labelMedium, 
                color = AppColors.Surface,
                textAlign = TextAlign.Center
            )
        }
    }
}

// 4.1-A: Gender
@Composable
fun GenderStep(selectedGender: String, onSelect: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        StepHeader("What's Your Gender", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        GenderCircle("Male", "♂", isSelected = selectedGender == "Male") { onSelect("Male") }
        Spacer(modifier = Modifier.height(24.dp))
        GenderCircle("Female", "♀", isSelected = selectedGender == "Female") { onSelect("Female") }
    }
}

@Composable
private fun GenderCircle(label: String, icon: String, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor = if (isSelected) AppColors.ElectricLime else AppColors.SurfaceContainerHigh
    val contentColor = if (isSelected) AppColors.Surface else AppColors.OnSurface
    val borderColor = if (isSelected) AppColors.ElectricLime else AppColors.TonalLavender
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(2.dp, borderColor, CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, style = AppTypography.displayLarge.copy(fontSize = 64.sp), color = contentColor)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = label, style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
    }
}

// 4.2-A: Age (Wheel simulation)
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun AgeStep(currentAge: Int, onUpdate: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    // Perfect center padding for Pager
    val itemWidth = 80.dp
    val horizontalPadding = (screenWidth - itemWidth) / 2

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        StepHeader("How Old Are You?", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
        
        Spacer(modifier = Modifier.height(64.dp))
        
        // Mockup big selected text
        Text(text = "$currentAge", style = AppTypography.displayLarge.copy(fontSize = 72.sp, fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
        Text(text = "▲", color = AppColors.ElectricLime)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Pager state
        val pagerState = rememberPagerState(
            initialPage = maxOf(0, currentAge - 10),
            pageCount = { 100 }
        )
        
        // Auto-select centered item when scrolling stops
        LaunchedEffect(pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                onUpdate(pagerState.currentPage + 10)
            }
        }
        
        // Continuous sync to make current item bold instantly
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onUpdate(page + 10)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(AppColors.TonalLavender),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(itemWidth),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                val age = page + 10
                val isCenter = page == pagerState.currentPage
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "$age", 
                        style = AppTypography.headlineMedium.copy(
                            fontWeight = if (isCenter) FontWeight.Bold else FontWeight.Normal,
                            fontSize = if (isCenter) 36.sp else 24.sp
                        ),
                        color = if (isCenter) AppColors.Surface else AppColors.Surface.copy(alpha = 0.5f),
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page)
                            }
                        }
                    )
                }
            }
        }
    }
}

// 4.3-A: Weight
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WeightStep(currentWeight: Float, onUpdate: (Float) -> Unit) {
    var isKg by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = 80.dp
    val horizontalPadding = (screenWidth - itemWidth) / 2

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        StepHeader(
            title = "What Is Your Weight?", 
            subtitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore\nmagna aliqua.",
            subtitleHasBackground = false
        )
        
        // KG | LB Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal)
                .height(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.ElectricLime),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { isKg = true }, 
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "KG", 
                    style = AppTypography.headlineSmall.copy(fontWeight = if (isKg) FontWeight.Bold else FontWeight.Normal), 
                    color = if (isKg) AppColors.SurfaceContainerHigh else AppColors.SurfaceContainerHigh.copy(alpha = 0.5f)
                )
            }
            Box(modifier = Modifier.width(2.dp).fillMaxHeight(0.5f).background(AppColors.SurfaceContainerHigh.copy(alpha = 0.5f)))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { isKg = false }, 
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LB", 
                    style = AppTypography.headlineSmall.copy(fontWeight = if (!isKg) FontWeight.Bold else FontWeight.Normal), 
                    color = if (!isKg) AppColors.SurfaceContainerHigh else AppColors.SurfaceContainerHigh.copy(alpha = 0.5f)
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Pager state for Wheel Simulator
        val baseDisplayWeight = if (isKg) 20 else 44 // lbs starts around 44
        val displayWeightValue = if (isKg) currentWeight.toInt() else (currentWeight * 2.20462f).toInt()
        
        val pagerState = rememberPagerState(
            initialPage = maxOf(0, displayWeightValue - baseDisplayWeight),
            pageCount = { 200 } // up to +200 units
        )
        
        LaunchedEffect(pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                val value = pagerState.currentPage + baseDisplayWeight
                onUpdate(if (isKg) value.toFloat() else value / 2.20462f)
            }
        }
        LaunchedEffect(pagerState, isKg) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                val value = page + baseDisplayWeight
                onUpdate(if (isKg) value.toFloat() else value / 2.20462f)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp) // height for numbers + strip
        ) {
            // Draw background purple strip just for the lower part
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(50.dp)) // space for numbers to jump out
                Box(modifier = Modifier.fillMaxWidth().height(80.dp).background(AppColors.TonalLavender))
            }
            
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(itemWidth),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                verticalAlignment = Alignment.Bottom
            ) { page ->
                val weight = page + baseDisplayWeight
                val isCenter = page == pagerState.currentPage
                
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "$weight", 
                        style = AppTypography.headlineMedium.copy(fontWeight = if (isCenter) FontWeight.Bold else FontWeight.Normal),
                        color = if (isCenter) AppColors.SurfaceContainerHigh else AppColors.SurfaceContainerHigh.copy(alpha = 0.5f),
                        modifier = Modifier.offset(y = (-4).dp).clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page)
                            }
                        }
                    )
                    
                    // Seamless Ruler marks
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp)
                    ) {
                        for (i in 0..4) {
                            val isMainTick = i == 2
                            val markColor = if (isCenter && isMainTick) AppColors.ElectricLime else AppColors.Surface.copy(alpha = 0.8f) // AppColors.Surface is a dark gray #0E0E0E
                            Box(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width( if(isCenter && isMainTick) 3.dp else 2.dp)
                                        .height(if (isMainTick) 40.dp else 24.dp)
                                        .background(markColor)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "▲", color = AppColors.ElectricLime)
        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(bottom = 32.dp)) {
            Text(text = "$displayWeightValue", style = AppTypography.displayLarge.copy(fontSize = 72.sp, fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
            Text(text = if (isKg) "kg" else "lb", style = AppTypography.titleLarge, color = AppColors.OnSurfaceVariant, modifier = Modifier.padding(bottom = 12.dp, start = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

// 4.4-A: Height
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HeightStep(currentHeight: Int, onUpdate: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val rulerHeight = 350.dp
    val itemHeight = 30.dp
    val verticalPadding = (rulerHeight - itemHeight) / 2
    
    val baseHeight = 100 // cm
    val pagerState = rememberPagerState(
        initialPage = maxOf(0, currentHeight - baseHeight),
        pageCount = { 150 } // up to 250 cm
    )
    
    LaunchedEffect(pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            onUpdate(pagerState.currentPage + baseHeight)
        }
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onUpdate(page + baseHeight)
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        StepHeader("What Is Your Height?", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", subtitleHasBackground = false)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Huge Center Text
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "${pagerState.currentPage + baseHeight}", style = AppTypography.displayLarge.copy(fontSize = 72.sp, fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
            Text(text = "Cm", style = AppTypography.headlineMedium, color = AppColors.OnSurfaceVariant, modifier = Modifier.padding(bottom = 12.dp, start = 8.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Pager scrolling ruler
        Row(
            modifier = Modifier.height(rulerHeight).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp) // 90dp for numbers, 70dp for purple box
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                // Background purple strip on the right
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(70.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColors.TonalLavender)
                )
                
                // Triangle pointing left TO the purple strip
                Text(
                    text = "◀", 
                    color = AppColors.ElectricLime,
                    modifier = Modifier.align(Alignment.CenterEnd).offset(x = 16.dp)
                )
                
                // Scrolling Content
                VerticalPager(
                    state = pagerState,
                    pageSize = PageSize.Fixed(itemHeight),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = verticalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    reverseLayout = true // Higher page = visually goes UP
                ) { page ->
                    val heightVal = page + baseHeight
                    val isCenter = page == pagerState.currentPage
                    val isMajorTick = heightVal % 5 == 0
                    
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Numbers Column
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                            if (isMajorTick) {
                                Text(
                                    text = "$heightVal", 
                                    style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isCenter) AppColors.OnSurface else AppColors.OnSurfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier.padding(end = 16.dp).clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(page)
                                        }
                                    }
                                )
                            }
                        }
                        
                        // Ruler Ticks Column (inside purple strip)
                        Box(
                            modifier = Modifier.width(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(if (isCenter) 3.dp else 2.dp)
                                    .width(if (isCenter) 50.dp else if (isMajorTick) 32.dp else 16.dp)
                                    .background(if (isCenter) AppColors.ElectricLime else AppColors.Surface.copy(alpha = 0.8f))
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(page)
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

// 4.5 & 4.6 (Goals and Physical levels)
@Composable
fun GoalStep(selectedGoal: String, onSelect: (String) -> Unit) {
    val goals = listOf("Lose Weight", "Gain Weight", "Muscle Mass Gain", "Shape Body", "Others")
    Column(modifier = Modifier.fillMaxSize()) {
        StepHeader("What Is Your Goal?", "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore\nmagna aliqua.", subtitleHasBackground = false)
        
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(AppColors.TonalLavender)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = AppSpacing.ScreenHorizontal, end = AppSpacing.ScreenHorizontal),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                goals.forEach { goal ->
                    val isSelected = goal == selectedGoal
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(Color.White)
                            .clickable { onSelect(goal) }
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = goal, style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Normal), color = AppColors.Surface)
                        
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(2.dp, AppColors.Surface, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(AppColors.Surface))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityLevelStep(selectedLevel: String, onSelect: (String) -> Unit) {
    val levels = listOf("Beginner", "Intermediate", "Advance")
    Column(modifier = Modifier.fillMaxSize()) {
        StepHeader("Physical Activity Level", "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore\nmagna aliqua.", subtitleHasBackground = false)
        
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            levels.forEach { level ->
                val isSelected = level == selectedLevel
                val bgColor = if (isSelected) AppColors.ElectricLime else Color.White
                val textColor = if (isSelected) AppColors.Surface else AppColors.TonalLavender
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(bgColor)
                        .clickable { onSelect(level) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = level,
                        style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.Normal),
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(text = label, style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = AppColors.TonalLavender)
        Spacer(modifier = Modifier.height(8.dp))
        GymTextField(value = value, onValueChange = onValueChange, placeholder = label, modifier = Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillProfileStep(
    state: SetupState,
    onUpdate: (nick: String, mobile: String, dob: String) -> Unit,
    onPickImage: (android.net.Uri) -> Unit = {}
) {
    var nick by remember { mutableStateOf(state.nickname) }
    var mobile by remember { mutableStateOf(state.mobileNumber) }
    var dob by remember { mutableStateOf(state.dateOfBirth) }
    var nickError by remember { mutableStateOf<String?>(null) }
    val scrollState = androidx.compose.foundation.rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Bottom sheet state (photo source picker)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    // Date picker state
    var showDatePicker by remember { mutableStateOf(false) }

    // Temp URI for camera capture
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraUri != null) {
            onPickImage(cameraUri!!)
        }
    }

    // Camera permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            coroutineScope.launch {
                val uri = createImageUri(context)
                cameraUri = uri
                cameraLauncher.launch(uri)
            }
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) onPickImage(uri)
    }

    // Load saved avatar bitmap
    val avatarBitmap = remember(state.avatarPath) {
        state.avatarPath?.let { path ->
            try {
                val bmp = android.graphics.BitmapFactory.decodeFile(path)
                bmp?.asImageBitmap()
            } catch (e: Exception) { null }
        }
    }

    // ── Date Picker ──────────────────────────────────────────────────────────
    if (showDatePicker) {
        // Parse existing dob "DD/MM/YYYY" if present
        val parts = dob.split("/")
        val initDay = parts.getOrNull(0)?.toIntOrNull() ?: 1
        val initMonth = parts.getOrNull(1)?.toIntOrNull() ?: 1
        val initYear = parts.getOrNull(2)?.toIntOrNull() ?: 2000
        com.gym.core.designsystem.component.GymDateTimePicker(
            mode = com.gym.core.designsystem.component.GymPickerMode.DATE,
            initialDay = initDay,
            initialMonth = initMonth,
            initialYear = initYear,
            onDateConfirmed = { d, m, y ->
                showDatePicker = false
                dob = "%02d/%02d/%04d".format(d, m, y)
                onUpdate(nick, mobile, dob)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    // ── Photo Source Bottom Sheet ─────────────────────────────────────────────
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = AppColors.SurfaceContainerHigh,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 40.dp)
            ) {
                Text(
                    text = "Profile Photo",
                    style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.OnSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppSpacing.ScreenHorizontal)
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.2f))

                // Camera option
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                showSheet = false
                                val hasCameraPermission = ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                                if (hasCameraPermission) {
                                    val uri = createImageUri(context)
                                    cameraUri = uri
                                    cameraLauncher.launch(uri)
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        }
                        .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("📷", fontSize = 24.sp)
                        Text(text = "Take Photo", style = AppTypography.bodyLarge, color = AppColors.OnSurface)
                    }
                }

                HorizontalDivider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.2f))

                // Gallery option
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                showSheet = false
                                galleryLauncher.launch("image/*")
                            }
                        }
                        .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("🖼️", fontSize = 24.sp)
                        Text(text = "Choose from Gallery", style = AppTypography.bodyLarge, color = AppColors.OnSurface)
                    }
                }

                HorizontalDivider(color = AppColors.OnSurfaceVariant.copy(alpha = 0.2f))

                // Cancel
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                showSheet = false
                            }
                        }
                        .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = AppColors.Error
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepHeader(
            "Fill Your Profile",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore\nmagna aliqua.",
            subtitleHasBackground = false
        )

        // Avatar area
        Box(
            modifier = Modifier.fillMaxWidth().height(160.dp).background(AppColors.TonalLavender),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    // Avatar circle — only open photo picker if nickname is filled
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .clickable {
                                if (nick.isBlank()) {
                                    nickError = "Vui lòng nhập biệt danh trước khi chọn ảnh"
                                } else {
                                    nickError = null
                                    showSheet = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatarBitmap != null) {
                            Image(
                                bitmap = avatarBitmap,
                                contentDescription = "Profile Photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            GymAvatar(
                                initial = nick.firstOrNull()?.toString() ?: "?",
                                size = 120.dp
                            )
                        }
                    }

                    // Edit icon badge
                    Box(
                        modifier = Modifier
                            .offset(x = (-4).dp, y = (-4).dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AppColors.ElectricLime)
                            .clickable {
                                if (nick.isBlank()) {
                                    nickError = "Vui lòng nhập biệt danh trước khi chọn ảnh"
                                } else {
                                    nickError = null
                                    showSheet = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✏️", fontSize = 16.sp)
                    }
                }
            }
        }

        // Nickname error shown below avatar
        androidx.compose.animation.AnimatedVisibility(visible = nickError != null) {
            Text(
                text = nickError ?: "",
                style = AppTypography.labelMedium,
                color = AppColors.Error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal)
                    .padding(top = 8.dp)
            )
        }

        // Profile form fields
        Column(modifier = Modifier.padding(AppSpacing.ScreenHorizontal).padding(top = 16.dp)) {

            // Nickname — required
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Text(
                    text = "Biệt danh (Nickname) *",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.TonalLavender
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = nick,
                    onValueChange = {
                        nick = it
                        if (it.isNotBlank()) nickError = null
                        onUpdate(nick, mobile, dob)
                    },
                    placeholder = "Nhập biệt danh",
                    errorText = if (nick.isBlank() && nickError != null) "Biệt danh không được để trống" else null,
                    modifier = Modifier
                )
            }

            // Date of Birth — required, tap to open date picker
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Text(
                    text = "Ngày sinh *",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.TonalLavender
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    GymTextField(
                        value = dob,
                        onValueChange = {},
                        placeholder = "Chọn ngày sinh",
                        modifier = Modifier,
                        enabled = false  // read-only, open picker via tap
                    )
                }
            }

            // Mobile number — required
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Text(
                    text = "Số điện thoại *",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.TonalLavender
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = mobile,
                    onValueChange = {
                        mobile = it
                        onUpdate(nick, mobile, dob)
                    },
                    placeholder = "Nhập số điện thoại",
                    modifier = Modifier
                )
            }
        }
    }
}


