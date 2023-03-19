package com.kevalpatel2106.feature.account.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.kevalpatel2106.core.R
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.ui.component.AccountInfoCard
import com.kevalpatel2106.core.ui.component.ListSeparatorHeading
import com.kevalpatel2106.core.ui.extension.handleLoadState
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.list.model.AccountListViewState
import com.kevalpatel2106.feature.account.list.model.AccountsListItem
import com.kevalpatel2106.feature.account.list.model.AccountsListItem.AccountItem
import com.kevalpatel2106.feature.account.list.model.AccountsListItem.HeaderItem

@Composable
internal fun AccountListScreen(
    displayErrorMapper: DisplayErrorMapper,
    viewModel: AccountListViewModel = viewModel(),
) {
    val lazyItems = viewModel.pageViewState.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = GUTTER),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(lazyItems) { _, item ->
                if (item == null) return@itemsIndexed
                AccountListItemRow(
                    item = item,
                    viewState = viewState,
                    onItemClick = { accountId -> viewModel.onAccountSelected(accountId) },
                    onItemDelete = { account -> viewModel.onAccountRemoved(account) },
                )
            }
            handleLoadState(
                lazyItems = lazyItems,
                displayErrorMapper = displayErrorMapper,
                emptyViewTitle = R.string.accounts_empty_state_headline,
                emptyViewMessage = R.string.accounts_empty_state_description,
                onClose = viewModel::close,
            )
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

@Composable
private fun AccountListItemRow(
    item: AccountsListItem,
    viewState: AccountListViewState,
    onItemClick: (accountId: AccountId) -> Unit,
    onItemDelete: (account: Account) -> Unit,
) = Box(modifier = Modifier.fillMaxWidth()) {
    when (item) {
        is AccountItem -> AccountInfoCard(
            avatar = item.account.avatar,
            name = item.account.name,
            email = item.account.email,
            ciName = item.ciName,
            ciIcon = item.ciIcon,
            modifier = Modifier.fillMaxWidth(),
            isSelected = item.account.isSelected,
            showDeleteIcon = viewState.isEditModeOn,
            onItemClick = { onItemClick(item.account.localId) },
            onItemDelete = { onItemDelete(item.account) },
        )

        is HeaderItem -> ListSeparatorHeading(item.ciName)
    }
}