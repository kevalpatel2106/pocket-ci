package com.kevalpatel2106.core.ui.extension

import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.EmptyView
import com.kevalpatel2106.core.ui.component.ErrorView
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.component.VerticalPagingErrorItem
import com.kevalpatel2106.core.ui.component.VerticalPagingLoadingItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun <T : Any> LazyPagingItems<T>.setUpPullRefresh(): Pair<Boolean, PullRefreshState> {
    val refreshScope = rememberCoroutineScope()
    var refreshing by rememberSaveable { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshScope.launch {
                refreshing = true
                refresh()
                delay(TimeUnit.SECONDS.toMillis(1))
                refreshing = false
            }
        },
    )
    return refreshing to pullRefreshState
}

fun <T : Any> LazyListScope.handleLoadState(
    lazyItems: LazyPagingItems<T>,
    displayErrorMapper: DisplayErrorMapper,
    @StringRes emptyViewTitle: Int,
    @StringRes emptyViewMessage: Int,
    @StringRes errorViewTitle: Int = R.string.error_unknown_title,
    @StringRes errorViewMessage: Int = R.string.error_unknown_message,
    onClose: () -> Unit,
) = with(lazyItems) {
    item {
        when {
            isLoadingFirstPage() -> LoadingView(modifier = Modifier.fillParentMaxSize())

            isLoadingNextPage() -> VerticalPagingLoadingItem()

            isErrorInFirstPage() -> with(loadState.refresh as LoadState.Error) {
                ErrorView(
                    title = stringResource(id = errorViewTitle),
                    message = stringResource(id = errorViewMessage),
                    error = displayErrorMapper(error),
                    onClose = { onClose() },
                    onRetry = { refresh() },
                )
            }

            lazyItems.isErrorLoadingNextPage() -> with(loadState.refresh as LoadState.Error) {
                VerticalPagingErrorItem(
                    title = stringResource(id = errorViewTitle),
                    message = stringResource(id = errorViewMessage),
                    error = displayErrorMapper(error),
                    onClose = { onClose() },
                    onRetry = { retry() },
                )
            }

            isEmptyList() -> EmptyView(
                title = stringResource(id = emptyViewTitle),
                message = stringResource(id = emptyViewMessage),
            )
        }
    }
}

private fun <T : Any> LazyPagingItems<T>.isLoadingFirstPage(): Boolean {
    return loadState.refresh is LoadState.Loading
}

private fun <T : Any> LazyPagingItems<T>.isErrorInFirstPage() = loadState.refresh is LoadState.Error

private fun <T : Any> LazyPagingItems<T>.isLoadingNextPage() = loadState.append is LoadState.Loading

private fun <T : Any> LazyPagingItems<T>.isErrorLoadingNextPage() =
    loadState.append is LoadState.Error

private fun <T : Any> LazyPagingItems<T>.isEmptyList() = with(loadState) {
    refresh is LoadState.NotLoading && append.endOfPaginationReached && itemCount < 1
}
