package com.kevalpatel2106.feature.job.list

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
import androidx.paging.compose.items
import com.kevalpatel2106.core.R
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.JobCard
import com.kevalpatel2106.core.ui.extension.handleLoadState
import com.kevalpatel2106.core.ui.extension.setUpPullRefresh
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.feature.job.list.model.JobListItem
import com.kevalpatel2106.feature.job.list.model.JobListItem.JobItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun JobListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: JobListViewModel = viewModel(),
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val (refreshing, pullRefreshState) = lazyItems.setUpPullRefresh()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .padding(horizontal = Spacing.GUTTER),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = lazyItems,
                key = { item -> item.key },
            ) { item ->
                if (item == null) return@items
                JobListItem(
                    item = item,
                )
                Spacer(modifier = Modifier.padding(vertical = Spacing.SPACING_MICRO))
            }
            handleLoadState(
                lazyItems = lazyItems,
                displayErrorMapper = displayErrorMapper,
                emptyViewTitle = R.string.jobs_list_empty_view_headline,
                emptyViewMessage = R.string.jobs_list_empty_view_description,
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
private fun JobListItem(item: JobListItem) = when (item) {
    is JobItem -> JobCard(
        buildStatus = item.job.status,
        jobName = item.job.name,
        executionTime = item.executionTime,
        triggerTime = item.triggeredTime,
    )
}