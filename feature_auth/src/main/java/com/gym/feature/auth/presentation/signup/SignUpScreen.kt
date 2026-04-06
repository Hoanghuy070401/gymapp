package com.gym.feature.auth.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.component.GymButton
import com.gym.core.designsystem.component.GymButtonVariant
import com.gym.core.designsystem.component.GymScaffold
import com.gym.core.designsystem.component.GymTextField
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography
import com.gym.feature.auth.presentation.AuthViewModel
import com.gym.feature.auth.presentation.FormValidator
import com.gym.feature.auth.presentation.login.SocialCircleIcon

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Per-field errors
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    // Touched state
    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var confirmTouched by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    // Derived form validity: all fields non-blank and no active errors
    val formValid = name.isNotBlank() && email.isNotBlank()
            && password.isNotBlank() && confirmPassword.isNotBlank()
            && nameError == null && emailError == null
            && passwordError == null && confirmError == null
            && password == confirmPassword

    LaunchedEffect(uiState.successUser) {
        if (uiState.successUser != null) onSignUpSuccess()
    }

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
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Create Account",
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.ElectricLime
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "◀", style = AppTypography.bodyLarge, color = AppColors.Surface)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Let's Start!",
                style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.OnSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Purple form area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.TonalLavender)
                    .padding(horizontal = AppSpacing.ScreenHorizontal, vertical = 28.dp)
            ) {
                // Full Name
                Text(
                    "Full name",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppColors.Surface
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameTouched = true
                        nameError = FormValidator.validateFullName(it)
                    },
                    placeholder = "John Doe",
                    errorText = if (nameTouched) nameError else null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                Text(
                    "Email",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppColors.Surface
                )
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

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Text(
                    "Password",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppColors.Surface
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordTouched = true
                        passwordError = FormValidator.validatePassword(it)
                        // Re-validate confirm if already touched
                        if (confirmTouched) {
                            confirmError = FormValidator.validateConfirmPassword(it, confirmPassword)
                        }
                    },
                    placeholder = "••••••••••••",
                    errorText = if (passwordTouched) passwordError else null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password
                Text(
                    "Confirm Password",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppColors.Surface
                )
                Spacer(modifier = Modifier.height(8.dp))
                GymTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmTouched = true
                        confirmError = FormValidator.validateConfirmPassword(password, it)
                    },
                    placeholder = "••••••••••••",
                    errorText = if (confirmTouched) confirmError else null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Firebase error
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    style = AppTypography.bodySmall,
                    color = AppColors.Error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Terms
            val termsText = buildAnnotatedString {
                withStyle(SpanStyle(color = AppColors.OnSurfaceVariant)) {
                    append("By continuing, you agree to\n")
                }
                withStyle(SpanStyle(color = AppColors.ElectricLime, fontWeight = FontWeight.Bold)) {
                    append("Terms of Use")
                }
                withStyle(SpanStyle(color = AppColors.OnSurfaceVariant)) { append(" and ") }
                withStyle(SpanStyle(color = AppColors.ElectricLime, fontWeight = FontWeight.Bold)) {
                    append("Privacy Policy.")
                }
            }
            Text(
                text = termsText,
                style = AppTypography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppSpacing.ScreenHorizontal)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = AppColors.ElectricLime)
            } else {
                GymButton(
                    text = "Sign Up",
                    onClick = {
                        // Force-validate all on submit
                        nameTouched = true; emailTouched = true
                        passwordTouched = true; confirmTouched = true
                        nameError = FormValidator.validateFullName(name)
                        emailError = FormValidator.validateEmail(email)
                        passwordError = FormValidator.validatePassword(password)
                        confirmError = FormValidator.validateConfirmPassword(password, confirmPassword)

                        if (FormValidator.isRegisterValid(nameError, emailError, passwordError, confirmError)) {
                            viewModel.register(email, password, confirmPassword, name)
                        }
                    },
                    variant = GymButtonVariant.GHOST,
                    modifier = Modifier.fillMaxWidth(0.65f),
                    enabled = formValid
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "or sign up with",
                style = AppTypography.bodySmall,
                color = AppColors.OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialCircleIcon("G", onClick = { /* TODO */ })
                SocialCircleIcon("f", onClick = { /* TODO FB SDK */ })
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account? ",
                    style = AppTypography.bodyMedium,
                    color = AppColors.OnSurfaceVariant
                )
                Text(
                    text = "Log in",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.ElectricLime,
                    modifier = Modifier.clickable { onNavigateToLogin() }.padding(4.dp)
                )
            }
        }
    }
}
