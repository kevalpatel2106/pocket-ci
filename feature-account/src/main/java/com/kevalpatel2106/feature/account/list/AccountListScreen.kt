package com.kevalpatel2106.feature.account.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR

@Composable
internal fun AccountListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: AccountListViewModel = viewModel(),
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val viewState = viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = GUTTER),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(lazyItems) { _, item ->
                if (item == null) return@itemsIndexed
                AccountListItemRow(item = item, viewState.value) { accountId ->
                    viewModel.onAccountSelected(accountId)
                }
            }
            handleLoadState(lazyItems, displayErrorMapper) { viewModel.close() }
        }
        FloatingActionButton(
            onClick = { viewModel.onAddAccountClicked() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SPACING_REGULAR),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.register_add_account_button_title),
            )
        }
    }
}

private fun LazyListScope.handleLoadState(
    lazyItems: LazyPagingItems<AccountsListItem>,
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
                title = stringResource(id = R.string.accounts_empty_state_headline),
                message = stringResource(id = R.string.accounts_empty_state_description),
            )
        }
    }
}
