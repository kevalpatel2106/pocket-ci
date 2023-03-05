package com.kevalpatel2106.registration.ciSelection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.CIInfoCard
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.LoadingState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.SuccessState

@Composable
internal fun CISelectionScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: CISelectionViewModel = viewModel(),
) {
    val state = viewModel.viewState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = GUTTER)
    ) {
        when (val currentState = state.value) {
            LoadingState -> LoadingView()

            is ErrorState -> ErrorView(
                error = displayErrorMapper(currentState.error),
                onClose = { viewModel.close() },
                onRetry = { viewModel.reload() },
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
