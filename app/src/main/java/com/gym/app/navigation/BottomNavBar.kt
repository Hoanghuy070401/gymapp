package com.gym.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gym.core.designsystem.theme.AppColors

/**
 * Bottom navigation bar for the main app shell.
 * Items: Home | Favorites | Workout | Support
 */
@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToWorkout: () -> Unit,
    onNavigateToSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("home", Screen.Home.route, Icons.Default.Home, "Home", onNavigateToHome),
        BottomNavItem("favorites", "favorites", Icons.Default.Star, "Favorites", onNavigateToFavorites),
        BottomNavItem("workout", Screen.Workout.route, Icons.Default.FitnessCenter, "Workout", onNavigateToWorkout),
        BottomNavItem("support", "support", Icons.Default.Headset, "Support", onNavigateToSupport)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.SurfaceContainerHigh)
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavBarItemView(
                item = item,
                isSelected = isSelected,
                onClick = item.onClick
            )
        }
    }
}

@Composable
private fun NavBarItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) AppColors.TonalLavender.copy(alpha = 0.2f)
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (isSelected) AppColors.TonalLavender else AppColors.OnSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) AppColors.TonalLavender else AppColors.OnSurfaceVariant
        )
    }
}

private data class BottomNavItem(
    val id: String,
    val route: String,
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)
