package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.ui.theme.PocketCITheme
import com.kevalpatel2106.core.ui.extension.LONG_STRING
import com.kevalpatel2106.core.ui.extension.SAMPLE_IMAGE
import com.kevalpatel2106.core.ui.extension.SAMPLE_STRING_RES
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.entity.Url

@Composable
fun CIInfoCard(
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    infoUrl: Url,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) = Card(
    modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick() },
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(Spacing.SPACING_SMALL)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = ciIcon),
            contentDescription = stringResource(id = ciName),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(Spacing.SPACING_HUGE)
                .height(Spacing.SPACING_HUGE)
                .clip(CircleShape),
        )
        CIInfoDetail(
            ciName = ciName,
            ciUrl = infoUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Spacing.SPACING_REGULAR),
        )
    }
}

@Composable
private fun CIInfoDetail(
    @StringRes ciName: Int,
    ciUrl: Url,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = stringResource(id = ciName),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        text = ciUrl.value,
        style = MaterialTheme.typography.labelMedium,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacing.SPACING_SMALL)
            .clickable { uriHandler.openUri(ciUrl.value) },
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
fun CIInfoCardPreview() = PocketCITheme {
    Column {
        CIInfoCard(
            ciName = SAMPLE_STRING_RES,
            ciIcon = SAMPLE_IMAGE,
            infoUrl = Url("https://example.com"),
            modifier = Modifier.padding(vertical = Spacing.SPACING_SMALL),  // Spacing between cards
        )
        CIInfoCard(
            ciName = SAMPLE_STRING_RES,
            ciIcon = SAMPLE_IMAGE,
            infoUrl = Url("https://$LONG_STRING.com"),
            modifier = Modifier.padding(vertical = Spacing.SPACING_SMALL),  // Spacing between cards
        )
    }
}

