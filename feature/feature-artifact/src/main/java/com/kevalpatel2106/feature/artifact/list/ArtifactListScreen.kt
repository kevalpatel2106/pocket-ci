package com.kevalpatel2106.feature.artifact.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.kevalpatel2106.core.R
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.ArtifactCard
import com.kevalpatel2106.core.ui.extension.handleLoadState
import com.kevalpatel2106.core.ui.extension.setUpPullRefresh
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListItem
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListItem.ArtifactItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ArtifactListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: ArtifactListListViewModel = viewModel(),
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val (refreshing, pullRefreshState) = lazyItems.setUpPullRefresh()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .padding(horizontal = GUTTER),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(lazyItems) { index, item ->
                if (item == null) return@itemsIndexed
                if (index != 0) Spacer(modifier = Modifier.padding(top = Spacing.SPACING_SMALL))
                ArtifactItemRow(item, viewModel)
            }
            handleLoadState(
                lazyItems = lazyItems,
                displayErrorMapper = displayErrorMapper,
                emptyViewTitle = R.string.artifacts_list_empty_state_title,
                emptyViewMessage = R.string.artifacts_list_empty_state_message,
                onClose = viewModel::close,
            )
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
internal fun ArtifactItemRow(
    item: ArtifactListItem,
    viewModel: ArtifactListListViewModel,
) = when (item) {
    is ArtifactItem -> ArtifactCard(
        artifactIcon = item.artifactIcon,
        contentDescription = item.contentDescription,
        name = item.artifact.name,
        size = item.humanReadableSize,
        time = item.createdTime,
        onItemClick = { viewModel.onDownloadClicked(item.artifact) },
    )
}
