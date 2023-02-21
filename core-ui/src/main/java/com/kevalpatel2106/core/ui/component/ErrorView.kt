package com.kevalpatel2106.core.ui.component

import androidx.annotation.IntDef
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kevalpatel2106.core.baseUi.DebugInfoAlertDialogBuilder
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.ActionButtonMode.Companion.ACTION_CLOSE
import com.kevalpatel2106.core.ui.component.ActionButtonMode.Companion.ACTION_RETRY
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.DisplayError

@Composable
private fun BaseErrorView(
    title: String?,
    message: String,
    modifier: Modifier = Modifier,
    error: DisplayError? = null,
    onRetry: () -> Unit = {},
    onClose: () -> Unit = {},
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    if (title != null) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SPACING_SMALL),
        )
    }
    Text(
        text = message,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SPACING_SMALL),
    )
    Spacer(modifier = Modifier.padding(vertical = SPACING_SMALL))
    ErrorActionButton(
        error = error,
        onRetry = onRetry,
        onClose = onClose,
    )
    TechnicalMessageButton(error = error)
}

@Composable
private fun ErrorActionButton(
    error: DisplayError?,
    onRetry: () -> Unit,
    onClose: () -> Unit,
) {
    val actionButtonMode = if (error?.nonRecoverable == true) {
        ACTION_CLOSE
    } else {
        ACTION_RETRY
    }
    val btnTitle = when (actionButtonMode) {
        ACTION_CLOSE -> R.string.error_view_close_button_title
        ACTION_RETRY -> R.string.error_view_retry_button_title
        else -> error("Invalid action button mode $actionButtonMode")
    }
    Button(
        contentPadding = PaddingValues(SPACING_MICRO),
        modifier = Modifier.defaultMinSize(minWidth = 100.dp),
        onClick = {
            when (actionButtonMode) {
                ACTION_CLOSE -> onClose()
                ACTION_RETRY -> onRetry()
                else -> error("Invalid action button mode $actionButtonMode")
            }
        },
    ) {
        Text(
            text = stringResource(id = btnTitle),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TechnicalMessageButton(error: DisplayError?) {
    if (error != null && !error.technicalMessage.isNullOrBlank()) {
        val context = LocalContext.current
        TextButton(
            contentPadding = PaddingValues(SPACING_SMALL),
            onClick = {
                DebugInfoAlertDialogBuilder(context).show(error)
            },
        ) {
            Text(
                text = stringResource(id = R.string.error_view_show_technical_detail_button_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    title: String? = stringResource(id = R.string.error_unknown_title),
    message: String = stringResource(id = R.string.error_unknown_message),
    error: DisplayError? = null,
    onRetry: () -> Unit = {},
    onClose: () -> Unit = {},
) = BaseErrorView(
    modifier = modifier.fillMaxSize(),
    message = message,
    title = title,
    error = error,
    onRetry = onRetry,
    onClose = onClose,
)

@Composable
fun HorizontalPagingErrorItem(
    modifier: Modifier = Modifier,
    title: String? = stringResource(id = R.string.error_unknown_title),
    message: String = stringResource(id = R.string.error_unknown_message),
    error: DisplayError? = null,
    onRetry: () -> Unit = {},
    onClose: () -> Unit = {},
) = BaseErrorView(
    modifier = modifier.fillMaxWidth(),
    message = message,
    title = title,
    error = error,
    onRetry = onRetry,
    onClose = onClose,
)

@Composable
fun VerticalPagingErrorItem(
    modifier: Modifier = Modifier,
    title: String? = stringResource(id = R.string.error_unknown_title),
    message: String = stringResource(id = R.string.error_unknown_message),
    error: DisplayError? = null,
    onRetry: () -> Unit = {},
    onClose: () -> Unit = {},
) = BaseErrorView(
    modifier = modifier.fillMaxHeight(),
    message = message,
    title = title,
    error = error,
    onRetry = onRetry,
    onClose = onClose,
)

@Composable
fun EmptyView(
    title: String?,
    message: String,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
) = BaseErrorView(
    modifier = modifier.fillMaxHeight(),
    message = message,
    title = title,
    onClose = onClose,
)

@IntDef(ACTION_RETRY, ACTION_CLOSE)
private annotation class ActionButtonMode {
    companion object {
        const val ACTION_RETRY = 0
        const val ACTION_CLOSE = 1
    }
}
