package com.kevalpatel2106.feature.artifact.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.kevalpatel2106.core.R
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.ArtifactCard
import com.kevalpatel2106.core.ui.component.EmptyView
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.component.VerticalPagingErrorItem
import com.kevalpatel2106.core.ui.component.VerticalPagingLoadingItem
import com.kevalpatel2106.core.ui.extension.isEmptyList
import com.kevalpatel2106.core.ui.extension.isErrorInFirstPage
import com.kevalpatel2106.core.ui.extension.isErrorLoadingNextPage
import com.kevalpatel2106.core.ui.extension.isLoadingFirstPage
import com.kevalpatel2106.core.ui.extension.isLoadingNextPage
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ArtifactListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: ArtifactListListViewModel = viewModel(),
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewState.isRefreshing,
        onRefresh = { viewModel.reload() },
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
            .padding(horizontal = GUTTER),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(lazyItems) { index, item ->
                if (item == null) return@itemsIndexed
                if (index != 0) Spacer(modifier = Modifier.padding(top = Spacing.SPACING_SMALL))
                ArtifactItemRow(item, viewModel)
            }
            handleLoadState(lazyItems, displayErrorMapper) { viewModel.close() }
        }
        PullRefreshIndicator(
            refreshing = viewState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

private fun LazyListScope.handleLoadState(
    lazyItems: LazyPagingItems<ArtifactListItem>,
    displayErrorMapper: DisplayErrorMapper,
    onClose: () -> Unit,
) = with(lazyItems) {
    when {
        isLoadingFirstPage() -> item { LoadingView() }

        isLoadingNextPage() -> item { VerticalPagingLoadingItem() }

        isErrorInFirstPage() -> with(loadState.refresh as LoadState.Error) {
            item {
                ErrorView(
                    error = displayErrorMapper(error),
                    onClose = { onClose() },
                    onRetry = { refresh() },
                )
            }
        }

        lazyItems.isErrorLoadingNextPage() -> with(loadState.refresh as LoadState.Error) {
            item {
                VerticalPagingErrorItem(
                    error = displayErrorMapper(error),
                    onClose = { onClose() },
                    onRetry = { retry() },
                )
            }
        }

        isEmptyList() -> item {
            EmptyView(
                title = stringResource(id = R.string.artifacts_list_empty_state_title),
                message = stringResource(id = R.string.artifacts_list_empty_state_message),
            )
        }
    }
}

@Composable
internal fun ArtifactItemRow(
    item: ArtifactListItem,
    viewModel: ArtifactListListViewModel,
) = when (item) {
    is ArtifactItem -> ArtifactCard(
        artifactIcon = item.artifactIcon,
        name = item.artifact.name,
        size = item.humanReadableSize,
        time = item.createdTime,
        onItemClick = { viewModel.onDownloadClicked(item.artifact) },
    )
}
