package com.kevalpatel2106.core.ui.element

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.extension.LONG_STRING
import com.kevalpatel2106.core.ui.extension.SHORT_STRING
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.theme.PocketCITheme


@Composable
fun ItemPropertyView(
    icon: ImageVector,
    @StringRes label: Int,
    value: String,
) = Row(
    modifier = Modifier
        .padding(top = Spacing.SPACING_MICRO)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(id = label),
        modifier = Modifier.padding(end = Spacing.SPACING_SMALL),
    )
    Text(
        text = stringResource(id = label),
        modifier = Modifier.padding(end = Spacing.SPACING_SMALL),
        style = MaterialTheme.typography.titleMedium,
    )
    Text(
        text = value,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
@Preview(
    name = "Normal",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
private fun ItemPropertyViewPreview() = PocketCITheme {
    Column {
        ItemPropertyView(
            icon = Icons.Filled.History,
            label = R.string.build_list_triggered_time_label,
            value = SHORT_STRING,
        )
        ItemPropertyView(
            icon = Icons.Filled.History,
            label = R.string.build_list_execution_time_label,
            value = LONG_STRING,
        )
    }
}