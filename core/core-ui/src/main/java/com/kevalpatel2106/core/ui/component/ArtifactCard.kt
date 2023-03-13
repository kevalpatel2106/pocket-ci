package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.extension.ARTIFACT_NAME
import com.kevalpatel2106.core.ui.extension.ARTIFACT_NAME_LONG
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_LARGE
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL

@Composable
fun ArtifactCard(
    name: String,
    size: String?,
    time: String?,
    @DrawableRes artifactIcon: Int,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) = Card(
    modifier = modifier.clickable { onItemClick() },
) {
    Column(
        Modifier
            .padding(SPACING_SMALL)
            .fillMaxWidth(),
    ) {
        Row {
            Image(
                painter = painterResource(id = artifactIcon),
                contentDescription = stringResource(id = contentDescription),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .width(SPACING_LARGE * LocalConfiguration.current.fontScale)
                    .height(SPACING_LARGE * LocalConfiguration.current.fontScale),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SPACING_SMALL),
            ) {
                Text(
                    text = name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )
                if (size != null) {
                    ArtifactPropertyView(
                        icon = Icons.Filled.DataUsage,
                        label = R.string.artifact_list_size_label,
                        value = size,
                    )
                }
                if (time != null) {
                    ArtifactPropertyView(
                        icon = Icons.Filled.WatchLater,
                        label = R.string.artifact_list_create_label,
                        value = time,
                    )
                }
            }
        }
    }
}

@Composable
private fun ArtifactPropertyView(
    icon: ImageVector, @StringRes label: Int, value: String
) = Row(
    modifier = Modifier
        .padding(top = SPACING_MICRO)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(id = label),
        modifier = Modifier.padding(end = SPACING_SMALL),
    )
    Text(
        text = stringResource(id = label),
        modifier = Modifier.padding(end = SPACING_SMALL),
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
    group = "Artifact Card",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Artifact Card",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Artifact Card",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Artifact Card",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
fun ArtifactCardPreview() = PocketCITheme {
    Column {
        ArtifactCard(
            artifactIcon = R.drawable.ic_android_apk,
            name = ARTIFACT_NAME,
            size = "123.00 MB",
            time = "12 hours ago",
            contentDescription = R.string.artifact_type_apk_content_description,
            modifier = Modifier.padding(vertical = SPACING_SMALL),  // Spacing between cards
        )
        ArtifactCard(
            artifactIcon = R.drawable.ic_text_file,
            name = ARTIFACT_NAME_LONG,
            size = "123.45 MB",
            time = "12 years ago",
            contentDescription = R.string.artifact_type_text_content_description,
            modifier = Modifier.padding(vertical = SPACING_SMALL),  // Spacing between cards
        )
    }
}