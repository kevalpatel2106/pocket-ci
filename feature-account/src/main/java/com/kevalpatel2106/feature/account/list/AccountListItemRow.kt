package com.kevalpatel2106.feature.account.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.kevalpatel2106.core.ui.component.AccountInfoCard
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId

@Composable
internal fun AccountListItemRow(
    item: AccountsListItem,
    viewState: AccountListViewState,
    onItemClick: (accountId: AccountId) -> Unit,
    onItemDelete: (account: Account) -> Unit,
) = Box(modifier = Modifier.fillMaxWidth()) {
    when (item) {
        is AccountsListItem.AccountItem -> AccountItemRow(
            account = item.account,
            ciName = item.ciName,
            ciIcon = item.ciIcon,
            showDeleteIcon = viewState.isEditModeOn,
            onItemClick = { onItemClick(item.account.localId) },
            onItemDelete = { onItemDelete(item.account) },
        )

        is AccountsListItem.HeaderItem -> HeaderItemRow(item.ciName)
    }
}

@Composable
private fun AccountItemRow(
    account: Account,
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    @SuppressWarnings("UnusedPrivateMember") showDeleteIcon: Boolean,
    onItemClick: () -> Unit,
    onItemDelete: () -> Unit
) = AccountInfoCard(
    avatar = account.avatar,
    name = account.name,
    email = account.email,
    ciName = ciName,
    ciIcon = ciIcon,
    modifier = Modifier.fillMaxWidth(),
    isSelected = account.isSelected,
    showDeleteIcon = showDeleteIcon,
    onItemClick = onItemClick,
    onItemDelete = onItemDelete,
)


@Composable
private fun HeaderItemRow(@StringRes title: Int) = Column {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1,
        modifier = Modifier
            .padding(vertical = SPACING_SMALL)
            .fillMaxWidth(),
    )
}
