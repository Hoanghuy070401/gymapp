package com.gym.feature.auth.presentation.emailverification

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.gym.feature.auth.presentation.AuthViewModel

@Composable
fun EmailVerificationScreen(
    viewModel: AuthViewModel,
    onVerified: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Dialog state for "change email"
    var showChangeEmailDialog by remember { mutableStateOf(false) }
    var newEmailInput by remember { mutableStateOf("") }
    var newEmailError by remember { mutableStateOf<String?>(null) }

    // Kick off polling when screen enters composition
    LaunchedEffect(Unit) {
        viewModel.startEmailVerificationPolling()
    }

    // Auto-navigate when polling / manual check confirms verification
    LaunchedEffect(uiState.isEmailVerified) {
        if (uiState.isEmailVerified) onVerified()
    }

    // ── Animations ───────────────────────────────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "emailVerify")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulseScale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.12f, targetValue = 0.48f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
        label = "glowAlpha"
    )
    val dot0Alpha by infiniteTransition.animateFloat(0.25f, 1f,
        infiniteRepeatable(tween(600, delayMillis = 0), RepeatMode.Reverse), label = "dot0")
    val dot1Alpha by infiniteTransition.animateFloat(0.25f, 1f,
        infiniteRepeatable(tween(600, delayMillis = 200), RepeatMode.Reverse), label = "dot1")
    val dot2Alpha by infiniteTransition.animateFloat(0.25f, 1f,
        infiniteRepeatable(tween(600, delayMillis = 400), RepeatMode.Reverse), label = "dot2")

    // ── Change Email Dialog ───────────────────────────────────────────────
    if (showChangeEmailDialog) {
        AlertDialog(
            onDismissRequest = { showChangeEmailDialog = false },
            containerColor = AppColors.SurfaceContainerHigh,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    "Thay đổi địa chỉ email",
                    style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.OnSurface
                )
            },
            text = {
                Column {
                    Text(
                        "Nhập email mới. Chúng tôi sẽ gửi link xác minh đến địa chỉ mới.",
                        style = AppTypography.bodySmall,
                        color = AppColors.OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GymTextField(
                        value = newEmailInput,
                        onValueChange = {
                            newEmailInput = it
                            newEmailError = if (it.contains("@") && it.contains(".")) null
                            else "Email không hợp lệ"
                        },
                        placeholder = "email@example.com",
                        errorText = newEmailError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    if (uiState.error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.error!!,
                            style = AppTypography.bodySmall,
                            color = AppColors.Error
                        )
                    }
                }
            },
            confirmButton = {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = AppColors.ElectricLime, modifier = Modifier.size(24.dp))
                } else {
                    TextButton(
                        onClick = {
                            val err = if (newEmailInput.contains("@") && newEmailInput.contains(".")) null
                            else "Email không hợp lệ"
                            newEmailError = err
                            if (err == null) {
                                viewModel.changeEmailAndResendVerification(newEmailInput) {
                                    showChangeEmailDialog = false
                                    newEmailInput = ""
                                }
                            }
                        }
                    ) {
                        Text(
                            "Xác nhận",
                            color = AppColors.ElectricLime,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangeEmailDialog = false; viewModel.clearError() }) {
                    Text("Hủy", color = AppColors.OnSurfaceVariant)
                }
            }
        )
    }

    // ── Main UI ──────────────────────────────────────────────────────────
    GymScaffold(scrollable = false) {
        // Top bar
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
                text = "Xác Minh Email",
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.8f))

            // Animated envelope icon
            Box(
                modifier = Modifier
                    .size(148.dp)
                    .graphicsLayer { scaleX = pulseScale; scaleY = pulseScale },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(AppColors.ElectricLime.copy(alpha = glowAlpha))
                )
                Box(
                    modifier = Modifier
                        .size(108.dp)
                        .clip(CircleShape)
                        .background(AppColors.TonalLavender),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✉", fontSize = 46.sp)
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Kiểm tra hộp thư của bạn",
                style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Chúng tôi đã gửi email xác minh đến",
                style = AppTypography.bodyMedium,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.verificationEmail.ifBlank { "email của bạn" },
                style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Change email link
            Text(
                text = "Thay đổi địa chỉ email",
                style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = AppColors.TonalLavender,
                modifier = Modifier
                    .clickable {
                        newEmailInput = ""
                        newEmailError = null
                        viewModel.clearError()
                        showChangeEmailDialog = true
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Instruction banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.TonalLavender)
                    .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nhấp vào liên kết trong email để xác minh tài khoản.\nKiểm tra cả thư mục Spam nếu không thấy.",
                    style = AppTypography.bodySmall,
                    color = AppColors.Surface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Inline error (for manual check)
            if (uiState.error != null && !showChangeEmailDialog) {
                Text(
                    text = uiState.error!!,
                    style = AppTypography.bodySmall,
                    color = AppColors.Error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = AppSpacing.ScreenHorizontal)
                        .padding(bottom = 12.dp)
                )
            }

            // 3-dot waiting indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(dot0Alpha, dot1Alpha, dot2Alpha).forEach { alpha ->
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(AppColors.ElectricLime.copy(alpha = alpha))
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Đang chờ xác minh...",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Manual check button
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = AppColors.ElectricLime,
                    modifier = Modifier.size(36.dp)
                )
            } else {
                GymButton(
                    text = "Xác nhận ngay",
                    onClick = { viewModel.checkEmailVerification() },
                    variant = GymButtonVariant.GHOST,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppSpacing.ScreenHorizontal)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend button with cooldown
            val cooldown = uiState.resendCooldown
            Text(
                text = if (cooldown > 0) "Gửi lại email (${cooldown}s)" else "Gửi lại email",
                style = AppTypography.bodyMedium.copy(
                    fontWeight = if (cooldown == 0) FontWeight.Bold else FontWeight.Normal
                ),
                color = if (cooldown == 0) AppColors.ElectricLime else AppColors.OnSurfaceVariant,
                modifier = Modifier
                    .clickable(enabled = cooldown == 0) { viewModel.resendVerificationEmail() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
        }
    }
}
