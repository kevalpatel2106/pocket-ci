package com.kevalpatel2106.feature.log

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.BuildLogLineView
import com.kevalpatel2106.core.ui.component.EmptyView
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.feature.log.BuildLogViewState.Empty
import com.kevalpatel2106.feature.log.BuildLogViewState.Error
import com.kevalpatel2106.feature.log.BuildLogViewState.Loading
import com.kevalpatel2106.feature.log.BuildLogViewState.Success

@Composable
internal fun BuildLogScreen(
    viewModel: BuildLogViewModel = viewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    val horizontalScrollState = rememberScrollState()

    when (val state = viewState.value) {
        Empty -> EmptyView(
            title = stringResource(id = R.string.build_log_empty_state_title),
            message = stringResource(id = R.string.build_log_empty_state_message),
        )

        is Error -> ErrorView(
            error = state.error,
            onRetry = { viewModel.reload() },
            onClose = { viewModel.close() },
        )

        Loading -> LoadingView()

        is Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(horizontalScrollState),
            ) {
                itemsIndexed(state.logs) { index, item ->
                    BuildLogLineView(
                        text = item,
                        lineNumber = index + 1,
                        fontScale = state.textScale,
                    )
                }
            }
        }
    }
}
