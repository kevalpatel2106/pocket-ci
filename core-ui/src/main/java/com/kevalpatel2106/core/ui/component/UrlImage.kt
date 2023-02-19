package com.kevalpatel2106.core.ui.component

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.kevalpatel2106.entity.Url

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UrlImage(
    url: Url,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    requestBuilderTransform: RequestBuilderTransform<Drawable> = { it },
) = GlideImage(
    model = url,
    contentDescription = contentDescription,
    modifier = modifier,
    alignment = alignment,
    contentScale = contentScale,
    alpha = DefaultAlpha,
    colorFilter = null,
    requestBuilderTransform = requestBuilderTransform,
)
