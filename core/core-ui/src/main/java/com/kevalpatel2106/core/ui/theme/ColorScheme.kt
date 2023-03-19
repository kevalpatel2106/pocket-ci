package com.kevalpatel2106.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.kevalpatel2106.core.ui.resource.md_theme_dark_background
import com.kevalpatel2106.core.ui.resource.md_theme_dark_error
import com.kevalpatel2106.core.ui.resource.md_theme_dark_errorContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_inverseOnSurface
import com.kevalpatel2106.core.ui.resource.md_theme_dark_inversePrimary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_inverseSurface
import com.kevalpatel2106.core.ui.resource.md_theme_dark_link
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onBackground
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onError
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onErrorContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onPrimary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onPrimaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onSecondary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onSecondaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onSurface
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onSurfaceVariant
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onTertiary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_onTertiaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_outline
import com.kevalpatel2106.core.ui.resource.md_theme_dark_outlineVariant
import com.kevalpatel2106.core.ui.resource.md_theme_dark_primary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_primaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_scrim
import com.kevalpatel2106.core.ui.resource.md_theme_dark_secondary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_secondaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_dark_surface
import com.kevalpatel2106.core.ui.resource.md_theme_dark_surfaceTint
import com.kevalpatel2106.core.ui.resource.md_theme_dark_surfaceVariant
import com.kevalpatel2106.core.ui.resource.md_theme_dark_tertiary
import com.kevalpatel2106.core.ui.resource.md_theme_dark_tertiaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_background
import com.kevalpatel2106.core.ui.resource.md_theme_light_error
import com.kevalpatel2106.core.ui.resource.md_theme_light_errorContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_inverseOnSurface
import com.kevalpatel2106.core.ui.resource.md_theme_light_inversePrimary
import com.kevalpatel2106.core.ui.resource.md_theme_light_inverseSurface
import com.kevalpatel2106.core.ui.resource.md_theme_light_link
import com.kevalpatel2106.core.ui.resource.md_theme_light_onBackground
import com.kevalpatel2106.core.ui.resource.md_theme_light_onError
import com.kevalpatel2106.core.ui.resource.md_theme_light_onErrorContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_onPrimary
import com.kevalpatel2106.core.ui.resource.md_theme_light_onPrimaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_onSecondary
import com.kevalpatel2106.core.ui.resource.md_theme_light_onSecondaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_onSurface
import com.kevalpatel2106.core.ui.resource.md_theme_light_onSurfaceVariant
import com.kevalpatel2106.core.ui.resource.md_theme_light_onTertiary
import com.kevalpatel2106.core.ui.resource.md_theme_light_onTertiaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_outline
import com.kevalpatel2106.core.ui.resource.md_theme_light_outlineVariant
import com.kevalpatel2106.core.ui.resource.md_theme_light_primary
import com.kevalpatel2106.core.ui.resource.md_theme_light_primaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_scrim
import com.kevalpatel2106.core.ui.resource.md_theme_light_secondary
import com.kevalpatel2106.core.ui.resource.md_theme_light_secondaryContainer
import com.kevalpatel2106.core.ui.resource.md_theme_light_surface
import com.kevalpatel2106.core.ui.resource.md_theme_light_surfaceTint
import com.kevalpatel2106.core.ui.resource.md_theme_light_surfaceVariant
import com.kevalpatel2106.core.ui.resource.md_theme_light_tertiary
import com.kevalpatel2106.core.ui.resource.md_theme_light_tertiaryContainer
import com.kevalpatel2106.core.ui.resource.orange

/**
 * Generated from https://m3.material.io/theme-builder#/custom.
 */
internal val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    outline = md_theme_light_outline,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    inverseSurface = md_theme_light_inverseSurface,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

internal val DarkColorsScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    outline = md_theme_dark_outline,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    inverseSurface = md_theme_dark_inverseSurface,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

// Extra colors
val ColorScheme.colorOrange
    @Composable
    get() = orange

val ColorScheme.colorLink
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_link else md_theme_light_link