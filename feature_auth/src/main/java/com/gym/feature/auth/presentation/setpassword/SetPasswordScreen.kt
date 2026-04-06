package com.gym.feature.auth.presentation.setpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.GymTextField
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

@Composable
fun SetPasswordScreen(
    onResetPassword: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

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
                text = "Set Password",
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Subtitle
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore\nmagna aliqua.",
                style = AppTypography.bodySmall,
                color = AppColors.TonalLavender,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Password field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Password",
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••••••"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Password field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Confirm Password",
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "••••••••••••"
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Reset Password Button
            GymButton(
                text = "Reset Password",
                onClick = { onResetPassword(password, confirmPassword) },
                variant = GymButtonVariant.GHOST,
                modifier = Modifier.fillMaxWidth(0.65f)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
