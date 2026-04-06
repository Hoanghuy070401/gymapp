package com.gym.core.designsystem.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val PrimaryKinetic = Color(0xFF896CFE)
    val ElectricLime = Color(0xFFE2F153)
    val TonalLavender = Color(0xFFB3A0FF)
    
    val Surface = Color(0xFF0E0E0E)
    val SurfaceContainerLow = Color(0xFF131313)
    val SurfaceContainerHigh = Color(0xFF1F2020)
    
    val OnSurface = Color(0xFFFFFFFF)
    val OnSurfaceVariant = Color(0xFFA0A0A0) // Gray text for body
    
    val Error = Color(0xFFB3261E)
    
    // Transparent primary for glows and ghost outlines
    val PrimaryGlow = PrimaryKinetic.copy(alpha = 0.08f)
    val OutlineGhost = Color(0xFFFFFFFF).copy(alpha = 0.15f)
}
