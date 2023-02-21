package com.kevalpatel2106.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DragHandle(modifier: Modifier = Modifier) = Box(
    modifier.padding(vertical = SPACING_MICRO),
) {
    Icon(
        imageVector = Icons.Filled.DragHandle,
        contentDescription = stringResource(id = R.string.close_drawer),
        tint = MaterialTheme.colorScheme.onSurface,
    )
}
