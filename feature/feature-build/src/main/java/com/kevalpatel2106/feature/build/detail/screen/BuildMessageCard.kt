package com.kevalpatel2106.feature.build.detail.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commit
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.kevalpatel2106.core.ui.component.ItemPropertyView
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.resources.R

@Composable
internal fun BuildMessageCard(
    message: String,
    showFullScreenButton: Boolean,
    border: BorderStroke = CardDefaults.outlinedCardBorder(),
    onFullScreenButtonClick: () -> Unit,
) = OutlinedCard(
    border = border,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = Spacing.SPACING_REGULAR),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Spacing.SPACING_SMALL, bottom = Spacing.SPACING_SMALL, end = Spacing.SPACING_REGULAR),
    ) {
        ItemPropertyView(
            icon = Icons.Filled.Commit,
            label = R.string.build_detail_commit_title,
            value = "",
            labelColor = MaterialTheme.colorScheme.primary,
            labelFontWeight = FontWeight.Bold,
        )
        Box {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
            if (showFullScreenButton) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(Spacing.SPACING_REGULAR),
                    onClick = onFullScreenButtonClick,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Fullscreen,
                        contentDescription = stringResource(
                            id = R.string.build_detail_full_screen_content_description,
                        ),
                    )
                }
            }
        }
    }
}