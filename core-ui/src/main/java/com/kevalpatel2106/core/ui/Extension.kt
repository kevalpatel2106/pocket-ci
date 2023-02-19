package com.kevalpatel2106.core.ui

import android.app.Activity
import android.view.View
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.internal.managers.FragmentComponentManager
import dagger.hilt.android.internal.managers.ViewComponentManager

@Composable
internal fun View.setUpStatusAndNavigationBarColor(
    colorScheme: ColorScheme,
    isDarkTheme: Boolean,
) {
    if (!isInEditMode) {
        SideEffect {
            // Set up navigation and status bar
            val window = when (val ctx = context) {
                is ViewComponentManager.FragmentContextWrapper -> {
                    (FragmentComponentManager.findActivity(context) as Activity).window
                }

                is Activity -> ctx.window
                else -> error("Unsupported view context $ctx.")
            }
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars = isDarkTheme
        }
    }
}

fun Fragment.setContent(content: @Composable () -> Unit) = ComposeView(requireContext()).apply {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent { PocketCITheme { content() } }
}

fun Activity.setContent(content: @Composable () -> Unit) = ComposeView(this).apply {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
    setContent { PocketCITheme { content() } }
}
