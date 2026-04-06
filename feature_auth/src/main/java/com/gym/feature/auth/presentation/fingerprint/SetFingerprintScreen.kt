package com.gym.feature.auth.presentation.fingerprint

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

@Composable
fun SetFingerprintScreen(
    onSkip: () -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    GymScaffold(scrollable = false) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "◀",
                style = AppTypography.bodyLarge,
                color = AppColors.ElectricLime,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Set Your Fingerprint",
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Subtitle
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing\nelit, sed do eiusmod tempor incididunt ut labore et\ndolore.",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Fingerprint Icon Circle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(2.dp, AppColors.TonalLavender, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83E\uDDB6", // fingerprint emoji placeholder
                    fontSize = 80.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Skip Button
            GymButton(
                text = "Skip",
                onClick = onSkip,
                variant = GymButtonVariant.GHOST,
                modifier = Modifier.fillMaxWidth(0.65f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Continue Button
            GymButton(
                text = "Continue",
                onClick = onContinue,
                variant = GymButtonVariant.GHOST,
                modifier = Modifier.fillMaxWidth(0.65f)
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
