package com.gym.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

@Composable
fun GymTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    helperText: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val isError = errorText != null
    val bottomBarColor = when {
        isError -> AppColors.Error
        else -> AppColors.PrimaryKinetic
    }

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = AppTypography.bodyLarge.copy(color = AppColors.OnSurface),
            cursorBrush = SolidColor(AppColors.PrimaryKinetic),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (enabled) AppColors.SurfaceContainerHigh
                            else AppColors.SurfaceContainerHigh.copy(alpha = 0.6f),
                            AppShape.Medium
                        )
                        .padding(AppSpacing.Medium)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = AppTypography.bodyLarge,
                            color = AppColors.OnSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            }
        )

        // Bottom bar — red on error, primary otherwise
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(bottomBarColor)
        )

        // Animated error message
        AnimatedVisibility(visible = isError) {
            Text(
                text = errorText ?: "",
                style = AppTypography.labelMedium,
                color = AppColors.Error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Helper text (only shown when no error)
        if (helperText != null && !isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = helperText,
                style = AppTypography.labelMedium,
                color = AppColors.OnSurfaceVariant
            )
        }
    }
}
