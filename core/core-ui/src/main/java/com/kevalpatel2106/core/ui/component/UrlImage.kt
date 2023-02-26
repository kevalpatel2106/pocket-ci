package com.kevalpatel2106.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.kevalpatel2106.entity.Url

@Composable
fun UrlImage(
    url: Url?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes placeHolder: Int? = null,
) = AsyncImage(
    model = url?.value,
    contentDescription = contentDescription,
    modifier = modifier,
    alignment = alignment,
    contentScale = contentScale,
    error = if (placeHolder != null) painterResource(id = placeHolder) else null,
    placeholder = if (placeHolder != null) painterResource(id = placeHolder) else null,
)
