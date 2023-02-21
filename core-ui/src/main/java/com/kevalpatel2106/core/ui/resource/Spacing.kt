package com.kevalpatel2106.core.ui.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val SPACING_MICRO = 4.dp
    val SPACING_SMALL = 8.dp
    val SPACING_REGULAR = 16.dp
    val SPACING_LARGE = 24.dp
    val SPACING_XLARGE = 36.dp
    val SPACING_XXLARGE = 48.dp
    val SPACING_HUGE = 64.dp

    val GUTTER: Dp
        @Composable get() = when (LocalConfiguration.current.screenWidthDp) {
            in 0..599 -> 16.dp
            in 600..904 -> 32.dp
            else -> 64.dp
        }
}
