package com.gym.app.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.auth.presentation.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    authViewModel: AuthViewModel,
    /** User has no session or explicitly logged out — show onboarding */
    onNavigateToOnboarding: () -> Unit,
    /** Persisted session, email verified, setup completed → Home */
    onLoggedIn: () -> Unit,
    /** Persisted session, email verified, setup NOT done → Setup */
    onNeedsSetup: () -> Unit,
    /** Persisted session, email NOT verified → EmailVerification */
    onNeedsVerification: () -> Unit
) {
    // Animation state
    val logoScale = remember { Animatable(0.7f) }
    val logoAlpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animate logo in
        logoScale.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        logoAlpha.animateTo(1f, animationSpec = tween(500))
        delay(200)
        taglineAlpha.animateTo(1f, animationSpec = tween(400))

        // Hold for a moment, then check session
        delay(800)
        authViewModel.checkPersistedSession(
            onNotLoggedIn   = onNavigateToOnboarding,
            onNeedsVerification = onNeedsVerification,
            onNeedsSetup    = onNeedsSetup,
            onLoggedIn      = onLoggedIn
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(AppColors.Surface)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Logo icon
            Image(
                painter = painterResource(id = com.gym.feature.auth.R.drawable.logo_icon),
                contentDescription = "FITBODY Logo",
                modifier = Modifier
                    .size(100.dp)
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // "Welcome to" above brand name
            Text(
                text = "Welcome to",
                style = AppTypography.bodyLarge,
                color = AppColors.ElectricLime,
                modifier = Modifier.alpha(logoAlpha.value)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Brand name
            Text(
                text = "FITBODY",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.OnSurface,
                letterSpacing = 4.sp,
                modifier = Modifier.alpha(logoAlpha.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tagline
            Text(
                text = "Track. Train. Transform.",
                style = AppTypography.bodyMedium,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(taglineAlpha.value)
            )

            Spacer(modifier = Modifier.weight(1.4f))
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    name = "Splash Screen",
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun SplashScreenPreview() {
    // Skips ViewModel — just renders the static brand layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = com.gym.feature.auth.R.drawable.logo_icon),
                contentDescription = "FITBODY Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Welcome to", style = AppTypography.bodyLarge, color = AppColors.ElectricLime)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "FITBODY", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold,
                color = AppColors.OnSurface, letterSpacing = 4.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Track. Train. Transform.", style = AppTypography.bodyMedium,
                color = AppColors.OnSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.weight(1.4f))
        }
    }
}
