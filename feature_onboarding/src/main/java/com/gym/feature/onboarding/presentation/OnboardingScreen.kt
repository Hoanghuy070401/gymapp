package com.gym.feature.onboarding.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview

// ── Page data ───────────────────────────────────────────────────────────────

private data class OnboardingPage(
    val emoji: String,
    val title: String,
    val highlight: String,    // part of title to render in ElectricLime
    val description: String,
    val bgRes: Int,
    val accentColor: Color
)

private val PAGES = listOf(
    OnboardingPage(
        emoji = "🏋️",
        title = "Start Your Journey\nTowards A More",
        highlight = "Active Lifestyle",
        description = "Track workouts, build habits,\nand crush your fitness goals.",
        bgRes = com.gym.feature.auth.R.drawable.onboarding_1,
        accentColor = Color(0xFFBCF24D)  // ElectricLime
    ),
    OnboardingPage(
        emoji = "🥗",
        title = "Find Nutrition Tips\nThat Fit Your",
        highlight = "Lifestyle",
        description = "Personalized meal plans\nand macro tracking made simple.",
        bgRes = com.gym.feature.auth.R.drawable.onboarding_2,
        accentColor = Color(0xFFB39DDB)  // TonalLavender
    ),
    OnboardingPage(
        emoji = "🏆",
        title = "A Community Built\nJust For",
        highlight = "You",
        description = "Join challenges, share progress,\nand stay motivated together.",
        bgRes = com.gym.feature.auth.R.drawable.onboarding_1,  // shared asset
        accentColor = Color(0xFF80DEEA)  // Cyan tint for variety
    )
)

// ── Screen ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Onboarding Screen", widthDp = 390, heightDp = 844)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { PAGES.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == PAGES.size - 1
    val currentPage = PAGES[pagerState.currentPage]

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // ── Full-screen background pager ──────────────────────────────────
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(PAGES[page].bgRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Per-page tinted gradient — makes each slide feel distinct
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0f to Color.Transparent,
                                    0.35f to Color.Black.copy(alpha = 0.35f),
                                    1f to Color.Black.copy(alpha = 0.92f)
                                )
                            )
                        )
                )
            }
        }

        // ── Overlay UI ────────────────────────────────────────────────────
        Column(modifier = Modifier.fillMaxSize()) {

            // Skip button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp, end = AppSpacing.ScreenHorizontal),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isLastPage) {
                    Text(
                        text = "Skip ›",
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.ElectricLime,
                        modifier = Modifier
                            .clickable { onFinished() }
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Content card at bottom ────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.ScreenHorizontal)
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Emoji badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(text = currentPage.emoji, fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title: normal + accent highlight
                Text(
                    text = currentPage.title,
                    style = AppTypography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 34.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = currentPage.highlight,
                    style = AppTypography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 34.sp
                    ),
                    color = currentPage.accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Text(
                    text = currentPage.description,
                    style = AppTypography.bodyMedium,
                    color = Color.White.copy(alpha = 0.72f),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ── Bottom row: dots + action button ─────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dot indicators
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(PAGES.size) { index ->
                            val isActive = index == pagerState.currentPage
                            val dotWidth by animateDpAsState(
                                targetValue = if (isActive) 24.dp else 8.dp,
                                animationSpec = tween(250, easing = FastOutSlowInEasing),
                                label = "dotWidth$index"
                            )
                            val dotColor by animateColorAsState(
                                targetValue = if (isActive) currentPage.accentColor else Color.White.copy(alpha = 0.35f),
                                animationSpec = tween(250),
                                label = "dotColor$index"
                            )
                            Box(
                                modifier = Modifier
                                    .width(dotWidth)
                                    .height(8.dp)
                                    .clip(CircleShape)
                                    .background(dotColor)
                            )
                        }
                    }

                    // Next / Get Started button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(currentPage.accentColor)
                            .clickable {
                                if (isLastPage) {
                                    onFinished()
                                } else {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                            .padding(horizontal = 28.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = if (isLastPage) "Get Started" else "Next  →",
                            style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
