package com.gym.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors

@Composable
fun GymAvatar(
    initial: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    gradientColors: List<Color> = listOf(AppColors.PrimaryKinetic, AppColors.TonalLavender)
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Brush.linearGradient(gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial.take(1).uppercase(),
            color = AppColors.OnSurface,
            fontWeight = FontWeight.Bold
        )
    }
}
