package com.kevalpatel2106.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
