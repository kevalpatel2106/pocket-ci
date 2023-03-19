package com.kevalpatel2106.registration.ciSelection.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.registration.ciSelection.CISelectionViewModel
import com.kevalpatel2106.registration.ciSelection.model.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.model.CISelectionViewState.LoadingState
import com.kevalpatel2106.registration.ciSelection.model.CISelectionViewState.SuccessState

@Composable
internal fun CISelectionScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: CISelectionViewModel = viewModel(),
) {
    val state = viewModel.viewState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = GUTTER),
    ) {
        when (val currentState = state.value) {
            LoadingState -> LoadingView()

            is ErrorState -> ErrorView(
                title = stringResource(id = R.string.error_unknown_title),
                message = stringResource(id = R.string.error_unknown_message),
                error = displayErrorMapper(currentState.error),
                onRetry = viewModel::reload,
                onClose = viewModel::close,
            )

            is SuccessState -> LazyColumn {
                itemsIndexed(currentState.listOfCi) { index, item ->
                    if (index != 0) Spacer(modifier = Modifier.padding(top = SPACING_SMALL))
                    CIInfoCard(
                        ciName = item.name,
                        ciIcon = item.icon,
                        infoUrl = item.infoUrl,
                    ) { viewModel.onCISelected(item) }
                }
            }
        }
    }
}
