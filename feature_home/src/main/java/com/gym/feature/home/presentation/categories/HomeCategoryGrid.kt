package com.gym.feature.home.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.R
import com.gym.core.designsystem.theme.AppColors
import com.gym.core.designsystem.theme.AppSpacing

private data class CategoryItem(
    val label: String,
    val icon: ImageVector,
    val iconTint: Color,
    val onClick: () -> Unit
)

@Composable
fun HomeCategoryGrid(
    onWorkoutClick: () -> Unit,
    onProgressClick: () -> Unit,
    onNutritionClick: () -> Unit,
    onCommunityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryItem(stringResource(R.string.cat_workout), Icons.Default.FitnessCenter, AppColors.ElectricLime, onWorkoutClick),
        CategoryItem(stringResource(R.string.cat_progress), Icons.Default.Timeline, AppColors.TonalLavender, onProgressClick),
        CategoryItem(stringResource(R.string.cat_nutrition), Icons.Default.Restaurant, Color(0xFFFF8A65), onNutritionClick),
        CategoryItem(stringResource(R.string.cat_community), Icons.Default.Groups, Color(0xFF4FC3F7), onCommunityClick)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ScreenHorizontal),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { item ->
            CategoryButton(
                label = item.label,
                icon = item.icon,
                iconTint = item.iconTint,
                onClick = item.onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategoryButton(
    label: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.SurfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconTint.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            color = AppColors.OnSurface,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
