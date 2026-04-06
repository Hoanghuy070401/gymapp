package com.gym.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gym.core.designsystem.theme.AppColors

@Composable
fun GymScaffold(
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val base = Modifier
        .fillMaxSize()
        .background(AppColors.Surface)
        .padding(top = 48.dp)

    // imePadding() must come AFTER verticalScroll so the scroll area shrinks
    // when the keyboard appears and content can scroll above it.
    val scrollMod = if (scrollable)
        base.verticalScroll(rememberScrollState()).imePadding()
    else
        base.imePadding()

    Column(
        modifier = modifier.then(scrollMod),
        content = content
    )
}
