package com.gym.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// "Don't use standard 4px rounded corners. This system requires Heavy Round (lg to xl) to feel premium."
object AppShape {
    val Small = RoundedCornerShape(12.dp)
    val Medium = RoundedCornerShape(16.dp)
    val Large = RoundedCornerShape(24.dp) // xl size from DESIGN.md
    val Pill = RoundedCornerShape(50)
}
