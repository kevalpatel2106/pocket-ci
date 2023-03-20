package com.kevalpatel2106.feature.build.list.screen

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
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.extension.handleLoadState
import com.kevalpatel2106.core.ui.extension.setUpPullRefresh
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.feature.build.list.BuildListViewModel
import com.kevalpatel2106.feature.build.list.model.BuildListItem
import com.kevalpatel2106.feature.build.list.model.BuildListItem.BuildItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BuildListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: BuildListViewModel = viewModel()
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val (refreshing, pullRefreshState) = lazyItems.setUpPullRefresh()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .padding(horizontal = Spacing.GUTTER),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(lazyItems) { index, item ->
                if (item == null) return@itemsIndexed
                if (index != 0) Spacer(modifier = Modifier.padding(top = Spacing.SPACING_SMALL))
                BuildItemRow(item, viewModel)
            }
            handleLoadState(
                lazyItems = lazyItems,
                displayErrorMapper = displayErrorMapper,
                emptyViewTitle = R.string.builds_empty_view_headline,
                emptyViewMessage = R.string.builds_empty_view_description,
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
internal fun BuildItemRow(
    item: BuildListItem,
    viewModel: BuildListViewModel,
) = when (item) {
    is BuildItem -> BuildCard(
        buildItem = item,
        onItemClick = viewModel::onBuildSelected,
    )
}