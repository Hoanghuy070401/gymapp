package com.gym.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.GymTextField
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.auth.presentation.login.SocialCircleIcon

// ─────────────────────────────────────────────────────────────────────────────
// Preview composables – pure UI, no ViewModel, safe for Android Studio Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Login Screen", widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val formValid = email.isNotBlank() && password.isNotBlank()

    GymScaffold(scrollable = false) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
            Spacer(Modifier.weight(1f))
            Text("Log In", style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Text("Welcome", style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
            Spacer(Modifier.height(16.dp))
            Text("Đăng nhập để tiếp tục hành trình\nthể dục của bạn.", style = AppTypography.bodySmall, color = AppColors.TonalLavender, textAlign = TextAlign.Center)
            Spacer(Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Username or email", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = email, onValueChange = { email = it }, placeholder = "example@example.com",
                    errorText = emailError, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Password", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = password, onValueChange = { password = it }, placeholder = "••••••••",
                    errorText = passwordError, visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
            }
            Spacer(Modifier.height(8.dp))
            Text("Forgot Password?", style = AppTypography.bodySmall, color = AppColors.OnSurfaceVariant,
                modifier = Modifier.align(Alignment.End))
            Spacer(Modifier.height(32.dp))
            GymButton(text = "Log In", onClick = {}, variant = GymButtonVariant.GHOST,
                modifier = Modifier.fillMaxWidth(0.65f), enabled = formValid)
            Spacer(Modifier.height(32.dp))
            Text("or sign in with", style = AppTypography.bodySmall, color = AppColors.OnSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SocialCircleIcon("G")
                SocialCircleIcon("f")
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, name = "Login — Filled Valid", widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenFilledPreview() {
    GymScaffold(scrollable = false) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))
            Text("Log In", style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
        }
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Text("Welcome", style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
            Spacer(Modifier.height(48.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Username or email", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "user@email.com", onValueChange = {}, placeholder = "")
            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Password", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "password", onValueChange = {}, placeholder = "",
                    visualTransformation = PasswordVisualTransformation())
            }
            Spacer(Modifier.height(32.dp))
            GymButton(text = "Log In", onClick = {}, variant = GymButtonVariant.GHOST,
                modifier = Modifier.fillMaxWidth(0.65f), enabled = true)
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator(color = AppColors.ElectricLime)
        }
    }
}

@Preview(showBackground = true, name = "SignUp Screen", widthDp = 390, heightDp = 844)
@Composable
fun SignUpScreenPreview() {
    GymScaffold(scrollable = false) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
            Text("Create Account", style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Text("Let's Start!", style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.OnSurface)
            Spacer(Modifier.height(8.dp))
            Text("Create your account", style = AppTypography.bodySmall, color = AppColors.TonalLavender)
            Spacer(Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth().background(AppColors.TonalLavender)
                    .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 28.dp)
            ) {
                Text("Full name", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = AppColors.Surface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "John Doe", onValueChange = {}, placeholder = "John Doe")
                Spacer(Modifier.height(16.dp))
                Text("Email", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = AppColors.Surface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "user@email.com", onValueChange = {}, placeholder = "example@example.com", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                Spacer(Modifier.height(16.dp))
                Text("Password", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = AppColors.Surface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "password123", onValueChange = {}, placeholder = "••••••••", visualTransformation = PasswordVisualTransformation())
                Spacer(Modifier.height(16.dp))
                Text("Confirm Password", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = AppColors.Surface)
                Spacer(Modifier.height(8.dp))
                GymTextField(value = "password123", onValueChange = {}, placeholder = "••••••••", visualTransformation = PasswordVisualTransformation())
            }
            Spacer(Modifier.height(24.dp))
            GymButton(text = "Sign Up", onClick = {}, variant = GymButtonVariant.GHOST, modifier = Modifier.fillMaxWidth(0.65f), enabled = true)
        }
    }
}

@Preview(showBackground = true, name = "Email Verification Screen", widthDp = 390, heightDp = 844)
@Composable
fun EmailVerificationScreenPreview() {
    GymScaffold(scrollable = false) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
            Text("Xác Minh Email", style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold), color = AppColors.ElectricLime)
            Spacer(Modifier.weight(1f))
            Text("◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.weight(0.8f))
            Box(
                modifier = Modifier.padding(16.dp).background(AppColors.TonalLavender, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✉", modifier = Modifier.padding(32.dp), style = AppTypography.headlineLarge)
            }
            Spacer(Modifier.height(36.dp))
            Text("Kiểm tra hộp thư của bạn", style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface, textAlign = TextAlign.Center)
            Spacer(Modifier.height(14.dp))
            Text("Chúng tôi đã gửi email xác minh đến", style = AppTypography.bodyMedium, color = AppColors.OnSurfaceVariant)
            Text("user@example.com", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime)
            Spacer(Modifier.height(8.dp))
            Text("Thay đổi địa chỉ email", style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = AppColors.TonalLavender, modifier = Modifier.padding(8.dp))
            Spacer(Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth().background(AppColors.TonalLavender).padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 18.dp)) {
                Text("Nhấp vào liên kết trong email để xác minh tài khoản.\nKiểm tra cả thư mục Spam nếu không thấy.",
                    style = AppTypography.bodySmall, color = AppColors.Surface, textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.weight(1f))
            GymButton(text = "Xác nhận ngay", onClick = {}, variant = GymButtonVariant.GHOST, modifier = Modifier.fillMaxWidth().padding(horizontal = AppSpacing.ScreenHorizontal))
            Spacer(Modifier.height(16.dp))
            Text("Gửi lại email", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime, modifier = Modifier.padding(8.dp))
            Spacer(Modifier.height(AppSpacing.ExtraLarge))
        }
    }
}
