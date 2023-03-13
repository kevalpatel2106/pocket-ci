package com.kevalpatel2106.feature.project.list

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
import com.kevalpatel2106.core.ui.component.ListHeaderItemRow
import com.kevalpatel2106.core.ui.component.ProjectCard
import com.kevalpatel2106.core.ui.extension.handleLoadState
import com.kevalpatel2106.core.ui.extension.setUpPullRefresh
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.feature.project.list.model.ProjectListItem
import com.kevalpatel2106.feature.project.list.model.ProjectListItem.HeaderItem
import com.kevalpatel2106.feature.project.list.model.ProjectListItem.ProjectItem

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun ProjectListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: ProjectListViewModel = viewModel(),
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
                ProjectListItem(
                    item = item,
                    onItemClick = viewModel::onProjectSelected,
                    onItemPinned = { project, pinned -> viewModel.togglePin(project, pinned) },
                )
                Spacer(modifier = Modifier.padding(vertical = SPACING_MICRO))
            }
            handleLoadState(
                lazyItems = lazyItems,
                displayErrorMapper = displayErrorMapper,
                emptyViewTitle = R.string.projects_empty_view_headline,
                emptyViewMessage = R.string.projects_empty_view_description,
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
private inline fun ProjectListItem(
    item: ProjectListItem,
    crossinline onItemClick: (project: Project) -> Unit,
    crossinline onItemPinned: (project: Project, pinned: Boolean) -> Unit,
) = when (item) {
    is HeaderItem -> ListHeaderItemRow(item.title)
    is ProjectItem -> ProjectCard(
        projectImageUrl = item.project.image,
        projectOwner = item.project.owner,
        projectName = item.project.name,
        isPinned = item.project.isPinned,
        isEnabled = !item.project.isDisabled,
        onItemClick = { onItemClick(item.project) },
        onItemPinned = { pinned -> onItemPinned(item.project, pinned) },
    )
}
