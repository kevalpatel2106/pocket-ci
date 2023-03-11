package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.R
import com.kevalpatel2106.core.ui.resource.Spacing

@Composable
fun ListHeaderItemRow(@StringRes title: Int) = Column {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1,
        modifier = Modifier
            .padding(vertical = Spacing.SPACING_SMALL)
            .fillMaxWidth(),
    )
}


@Composable
@Preview(
    name = "Normal",
    group = "Project List Header",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Project List Header",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Project List Header",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Project List Header",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
private fun ListHeaderItemRowPreview() {
    ListHeaderItemRow(title = R.string.list_header_pinned)
}