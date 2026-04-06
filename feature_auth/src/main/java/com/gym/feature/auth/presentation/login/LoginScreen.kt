package com.gym.feature.auth.presentation.login

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.gym.feature.auth.presentation.FormValidator

private const val GOOGLE_WEB_CLIENT_ID =
    "777269708097-te9jc1ulpfacqqugrs6br08c5cfna7r8.apps.googleusercontent.com"

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,           // verified + setup done → Home
    onLoginNeedsSetup: () -> Unit,        // verified + setup not done → Setup
    onLoginNeedsVerification: () -> Unit, // email not verified → EmailVerification
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Derived validity — reacts immediately as user types/clears
    val formValid = email.isNotBlank() && password.isNotBlank()
            && emailError == null && passwordError == null

    LaunchedEffect(uiState.successUser, uiState.requiresEmailVerification, uiState.isSetupCompleted) {
        val user = uiState.successUser ?: return@LaunchedEffect
        when {
            uiState.requiresEmailVerification -> onLoginNeedsVerification()
            uiState.isSetupCompleted          -> onLoginSuccess()
            else                              -> onLoginNeedsSetup()
        }
    }

    GymScaffold(scrollable = false) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.ScreenHorizontal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Log In",
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

            Text(
                text = "Welcome",
                style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Đăng nhập để tiếp tục hành trình\nthể dục của bạn.",
                style = AppTypography.bodySmall,
                color = AppColors.TonalLavender,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Email field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Username or email", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailTouched = true
                        emailError = FormValidator.validateEmail(it)
                    },
                    placeholder = "example@example.com",
                    errorText = if (emailTouched) emailError else null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Password field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Password", style = AppTypography.bodyMedium, color = AppColors.OnSurface)
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordTouched = true
                        passwordError = FormValidator.validatePassword(it)
                    },
                    placeholder = "••••••••••••",
                    errorText = if (passwordTouched) passwordError else null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot Password?",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onNavigateToForgotPassword() }
                    .padding(vertical = 4.dp)
            )

            // Firebase error (e.g. wrong credentials)
            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.error!!,
                    style = AppTypography.bodySmall,
                    color = AppColors.Error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = AppColors.ElectricLime)
            } else {
                GymButton(
                    text = "Log In",
                    onClick = {
                        // Force-validate all fields on submit
                        emailTouched = true
                        passwordTouched = true
                        emailError = FormValidator.validateEmail(email)
                        passwordError = FormValidator.validatePassword(password)

                        if (FormValidator.isLoginValid(emailError, passwordError)) {
                            viewModel.login(email, password)
                        }
                    },
                    variant = GymButtonVariant.GHOST,
                    modifier = Modifier.fillMaxWidth(0.65f),
                    enabled = formValid
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "or sign in with",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialCircleIcon("G", onClick = {
                    viewModel.signInWithGoogle(context, GOOGLE_WEB_CLIENT_ID)
                })
                SocialCircleIcon("f", onClick = { /* TODO: Facebook SDK */ })
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurfaceVariant
                )
                Text(
                    text = "Sign Up",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.ElectricLime,
                    modifier = Modifier.clickable { onNavigateToSignUp() }.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun SocialCircleIcon(symbol: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .border(1.5.dp, AppColors.TonalLavender, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = AppColors.TonalLavender
        )
    }
}
