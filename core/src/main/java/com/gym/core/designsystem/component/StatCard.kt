package com.gym.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppShape
import com.gym.core.designsystem.theme.AppTypography

@Composable
fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    isHighlight: Boolean = false
) {
    GymCard(
        modifier = modifier,
        containerColor = if (isHighlight) AppColors.SurfaceContainerHigh else AppColors.SurfaceContainerLow,
        shape = AppShape.Large
    ) {
        Column {
            Text(
                text = value,
                style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = if (isHighlight) AppColors.ElectricLime else AppColors.PrimaryKinetic
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = AppTypography.labelMedium,
                color = AppColors.OnSurfaceVariant
            )
        }
    }
}
