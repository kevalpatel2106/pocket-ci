package com.kevalpatel2106.registration.ciSelection

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.resource.SPACING_HUGE
import com.kevalpatel2106.core.ui.resource.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.SPACING_SMALL
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.LoadingState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.SuccessState

@Composable
internal fun CISelectionScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: CISelectionViewModel = viewModel(),
) {
    val state = viewModel.viewState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        when (val currentState = state.value) {
            LoadingState -> LoadingView()

            is ErrorState -> ErrorView(
                error = displayErrorMapper(currentState.error),
                onClose = { viewModel.close() },
                onRetry = { viewModel.reload() }
            )

            is SuccessState -> LazyColumn {
                itemsIndexed(currentState.listOfCi) { _, item ->
                    CISelectionRow(item) { viewModel.onCISelected(item) }
                }
            }
        }
    }
}

@Composable
private fun CISelectionRow(
    ciInfo: CIInfo,
    onItemClick: () -> Unit
) = Box(
    modifier = Modifier.padding(horizontal = SPACING_REGULAR, vertical = SPACING_SMALL),
) {
    CIInfoCard(
        onItemClick = onItemClick,
        ciName = ciInfo.name,
        ciIcon = ciInfo.icon,
        infoUrl = ciInfo.infoUrl,
    )
}

@Composable
private fun CIInfoCard(
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    infoUrl: Url,
    onItemClick: () -> Unit,
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick() },
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(SPACING_SMALL)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = ciIcon),
            contentDescription = stringResource(id = ciName),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(SPACING_HUGE)
                .height(SPACING_HUGE)
                .clip(CircleShape),
        )
        CIInfoDetail(
            ciName = ciName,
            ciUrl = infoUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_REGULAR),
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
        color = Color.Blue,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACING_SMALL)
            .clickable { uriHandler.openUri(ciUrl.value) },
    )
}
