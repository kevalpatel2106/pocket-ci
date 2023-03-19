package com.kevalpatel2106.feature.build.markdownView.screen

import android.content.res.Configuration
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.kevalpatel2106.core.ui.theme.PocketCITheme
import com.kevalpatel2106.core.ui.resource.Spacing
import io.noties.markwon.Markwon

private const val MARKDWON_TEXT_SIZE_SP = 14
private const val MARKDWON_TEXT_PREVIEW = """
    ## Heading 2
    - **Bold**
    - Normal text
    - [Link](#link)
"""

@Composable
internal fun MarkdownViewerScreen(
    rawMarkdown: String
) {
    val context = LocalContext.current
    val markdown = remember(key1 = rawMarkdown) {
        val markwon = Markwon.create(context)
        markwon.toMarkdown(rawMarkdown)
    }
    val scrollState = rememberScrollState()
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.GUTTER)
            .verticalScroll(scrollState),
        factory = { ctx ->
            TextView(ctx).apply {
                text = markdown
                textSize = MARKDWON_TEXT_SIZE_SP.sp.value
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setTextColor(textColor)
            }
        },
    )
}


@Composable
@Preview(
    name = "Normal",
    group = "Markdown Viewer",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Markdown Viewer",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Markdown Viewer",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Markdown Viewer",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
private fun MarkdownViewerScreenPreview() = PocketCITheme {
    MarkdownViewerScreen(MARKDWON_TEXT_PREVIEW)
}