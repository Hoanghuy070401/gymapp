package com.gym.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing

@Composable
fun GymCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AppColors.SurfaceContainerLow,
    shape: Shape = AppShape.Large,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(containerColor, shape)
            .padding(AppSpacing.Medium),
        content = content
    )
}
