package com.kevalpatel2106.core.ui.resource

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.kevalpatel2106.core.resources.R

private const val GOOGLE_FONTS_AUTHORITY = "com.google.android.gms.fonts"
private const val GOOGLE_FONTS_PACKAGE = "com.google.android.gms"

@OptIn(ExperimentalTextApi::class)
private val fontProvider = GoogleFont.Provider(
    providerAuthority = GOOGLE_FONTS_AUTHORITY,
    providerPackage = GOOGLE_FONTS_PACKAGE,
    certificates = R.array.com_google_android_gms_fonts_certs,
)

@OptIn(ExperimentalTextApi::class)
private val latoLight = Font(
    googleFont = GoogleFont("Lato"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.Light,
)

@OptIn(ExperimentalTextApi::class)
private val latoNormal = Font(
    googleFont = GoogleFont("Lato"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.Normal,
)

@OptIn(ExperimentalTextApi::class)
private val latoItalicsNormal = Font(
    googleFont = GoogleFont("Lato"),
    fontProvider = fontProvider,
    style = FontStyle.Italic,
    weight = FontWeight.Normal,
)

@OptIn(ExperimentalTextApi::class)
private val latoSemiBold = Font(
    googleFont = GoogleFont("Lato"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.SemiBold,
)

@OptIn(ExperimentalTextApi::class)
private val latoBold = Font(
    googleFont = GoogleFont("Lato"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.Bold,
)

@OptIn(ExperimentalTextApi::class)
private val JetBrainsMonoNormal = Font(
    googleFont = GoogleFont("JetBrains Mono"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.Normal,
)

@OptIn(ExperimentalTextApi::class)
private val JetBrainsMonoBold = Font(
    googleFont = GoogleFont("JetBrains Mono"),
    fontProvider = fontProvider,
    style = FontStyle.Normal,
    weight = FontWeight.Bold,
)


val latoFontFamily = FontFamily(
    latoLight,
    latoNormal,
    latoSemiBold,
    latoBold,
    latoItalicsNormal,
)

val jetBrainsMonoFamily = FontFamily(
    JetBrainsMonoNormal,
    JetBrainsMonoBold,
)
