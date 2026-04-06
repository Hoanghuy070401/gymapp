package com.gym.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// In a real app we load Poppins and League Spartan from resources.
// Using default for scaffolding, to be replaced with R.font.poppins and R.font.league_spartan
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default, 
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = (-2).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default, 
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        letterSpacing = (-1).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, 
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default, 
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)
