package com.kevalpatel2106.pocketci.bottomDrawer

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.component.DragHandle
import com.kevalpatel2106.core.ui.resource.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.SPACING_SMALL
import com.kevalpatel2106.pocketci.R

@Composable
internal fun BottomDrawerDialogScreen(events: BottomDrawerViewEvents) {
    PocketCITheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DragHandle()
            BottomDrawerRow(
                icon = Icons.Filled.Person,
                text = R.string.navigation_title_accounts,
                onClick = { events.onAccountsClicked() },
            )
            BottomDrawerRow(
                icon = Icons.Filled.Settings,
                text = R.string.navigation_title_settings,
                onClick = { events.onSettingsClicked() },
            )
        }
    }
}

@Composable
private fun BottomDrawerRow(
    icon: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BottomDrawerDialogPreView() {
    BottomDrawerDialogScreen(
        events = object : BottomDrawerViewEvents {
            override fun onSettingsClicked() = Unit
            override fun onAccountsClicked() = Unit
        },
    )
}
