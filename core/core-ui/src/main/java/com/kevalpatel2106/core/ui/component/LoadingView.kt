package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_XLARGE

@Composable
private fun BaseLoadingView(modifier: Modifier = Modifier) = Box(modifier = modifier) {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
            .width(SPACING_XLARGE)
            .height(SPACING_XLARGE),
    )
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) = BaseLoadingView(modifier = modifier.fillMaxSize())

@Composable
fun HorizontalPagingLoadingItem(modifier: Modifier = Modifier) =
    BaseLoadingView(modifier = modifier.fillMaxWidth())

@Composable
fun VerticalPagingLoadingItem(modifier: Modifier = Modifier) =
    BaseLoadingView(modifier = modifier.fillMaxHeight())

@Preview(
    name = "Normal",
    group = "Loading View",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Loading View",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Tablet",
    group = "Loading View",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
@Composable
fun LoadingViewPreview() = PocketCITheme {
    Column {
        LoadingView(modifier = Modifier.size(48.dp))
        HorizontalPagingLoadingItem()
        VerticalPagingLoadingItem()
    }
}