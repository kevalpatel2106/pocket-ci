package com.kevalpatel2106.core.ui.component

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.kevalpatel2106.core.ui.resource.Spacing

private const val MAX_VALUE_LENGTH = 30

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ItemPropertyChip(
    icon: ImageVector,
    value: String,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) = Row {
    val context = LocalContext.current
    AssistChip(
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = contentDescription),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        },
        label = {
            Text(
                text = value.take(MAX_VALUE_LENGTH),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        modifier = modifier.combinedClickable(
            onClick = onClick,
            onLongClick = {
                Toast.makeText(context, contentDescription, Toast.LENGTH_SHORT).show()
            },
        ),
    )
    Spacer(modifier = Modifier.width(Spacing.SPACING_SMALL))
}