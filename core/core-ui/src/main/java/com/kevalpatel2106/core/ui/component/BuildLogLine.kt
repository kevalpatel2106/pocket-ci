package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.extension.LONG_STRING
import com.kevalpatel2106.core.ui.extension.NAME
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL

private const val MAX_LINE_NUMBER_LETTER = 5

@Composable
@OptIn(ExperimentalUnitApi::class)
fun BuildLogLine(
    text: String,
    lineNumber: Int,
    fontScale: Float,
    modifier: Modifier = Modifier,
) {
    val lineCountContainerWidth =
        (MaterialTheme.typography.labelMedium.fontSize.value * LocalConfiguration.current.fontScale * MAX_LINE_NUMBER_LETTER).dp

    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(lineCountContainerWidth)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.tertiary),
        ) {
            Text(
                text = lineNumber.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(horizontal = SPACING_SMALL)
                    .fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.width(SPACING_SMALL))
        Text(
            text = text,
            fontSize = TextUnit(
                MaterialTheme.typography.bodyMedium.fontSize.value * fontScale,
                TextUnitType.Sp,
            ),
            overflow = TextOverflow.Clip,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(SPACING_REGULAR))
    }
}

@Composable
@Preview(
    name = "Normal",
    group = "Build Log Line",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Build Log Line",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Build Log Line",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Build Log Line",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
fun BuildLogLinePreview() = PocketCITheme {
    Column {
        BuildLogLine(text = NAME, lineNumber = 1, fontScale = 0.5f)
        BuildLogLine(text = LONG_STRING, lineNumber = 1204, fontScale = 1f)
        BuildLogLine(
            text = "$LONG_STRING $LONG_STRING $LONG_STRING",
            lineNumber = 99999,
            fontScale = 2f,
        )
    }
}
