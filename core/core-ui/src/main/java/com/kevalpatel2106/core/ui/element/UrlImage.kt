package com.kevalpatel2106.core.ui.element

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevalpatel2106.core.ui.extension.AVATAR_IMAGE
import com.kevalpatel2106.core.ui.extension.LONG_STRING
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UrlImagePreview() = Column {
    UrlImage(
        url = Url("https://placekitten.com/200/300"),
        contentDescription = LONG_STRING,
        modifier = Modifier.size(48.dp),
    )
    UrlImage(
        url = Url("https://placekitten.com/200/300"),
        contentDescription = LONG_STRING,
        modifier = Modifier.size(48.dp),
        contentScale = ContentScale.Crop
    )
    UrlImage(
        url = Url("https://invalid.url.com"),
        contentDescription = LONG_STRING,
        modifier = Modifier.size(48.dp),
        contentScale = ContentScale.Crop,
        placeHolder = AVATAR_IMAGE
    )
    UrlImage(
        url = null,
        contentDescription = LONG_STRING,
        modifier = Modifier.size(48.dp),
        contentScale = ContentScale.Crop,
        placeHolder = AVATAR_IMAGE
    )
}
