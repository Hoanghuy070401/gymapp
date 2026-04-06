package com.gym.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppSpacing
import com.gym.core.designsystem.theme.AppTypography

enum class GymButtonVariant { PRIMARY, HIGH_ALERT, GHOST }

@Composable
fun GymButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: GymButtonVariant = GymButtonVariant.PRIMARY,
    enabled: Boolean = true
) {
    val (bgColor, textColor, borderColor) = when (variant) {
        GymButtonVariant.PRIMARY -> Triple(
            AppColors.PrimaryKinetic,
            AppColors.OnSurface,
            Color.Transparent
        )
        GymButtonVariant.HIGH_ALERT -> Triple(
            AppColors.ElectricLime,
            AppColors.Surface,
            Color.Transparent
        )
        GymButtonVariant.GHOST -> Triple(
            Color.Transparent,
            AppColors.PrimaryKinetic,
            AppColors.OutlineGhost
        )
    }

    val alpha = if (enabled) 1f else 0.4f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppShape.Pill)
            .background(bgColor.copy(alpha = alpha))
            .then(
                if (variant == GymButtonVariant.GHOST)
                    Modifier.border(1.dp, borderColor, AppShape.Pill)
                else Modifier
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = AppSpacing.Medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = textColor.copy(alpha = alpha)
        )
    }
}
