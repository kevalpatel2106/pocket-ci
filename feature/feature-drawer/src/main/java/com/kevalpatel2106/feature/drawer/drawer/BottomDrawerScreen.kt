package com.kevalpatel2106.feature.drawer.drawer

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.element.DragHandle
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem

@Composable
internal fun BottomDrawerDialogScreen(
    events: BottomDrawerViewEvents,
    viewModel: BottomDrawerViewModel = viewModel(),
) {
    val viewState by viewModel.viewStateFlow.collectAsState()

    PocketCITheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DragHandle()

            viewState.forEach { drawerItem ->
                BottomDrawerRow(
                    icon = drawerItem.icon,
                    text = drawerItem.title,
                    onClick = { events.onDrawerItemClicked(drawerItem) },
                )
            }
        }
    }
}

@Composable
private fun BottomDrawerRow(
    icon: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit = {},
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = SPACING_SMALL)
        .clickable { onClick() },
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically,
) {
    Icon(
        icon,
        contentDescription = stringResource(id = text),
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(end = SPACING_SMALL, start = SPACING_REGULAR),
    )
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(end = SPACING_REGULAR),
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
@Preview(
    name = "Normal",
    group = "Bottom Drawer Row",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Bottom Drawer Row",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Bottom Drawer Row",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Bottom Drawer Row",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
private fun BottomDrawerRowPreView() = PocketCITheme {
    Column(modifier = Modifier.fillMaxWidth()) {
        BottomDrawerItem.values().forEach { BottomDrawerRow(it.icon, it.title) }
    }
}
