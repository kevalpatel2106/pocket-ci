package com.kevalpatel2106.feature.account.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kevalpatel2106.core.ui.component.AccountAvatar
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.AccountId

@Composable
internal fun AccountListItemRow(
    item: AccountsListItem,
    viewState: AccountListViewState,
    onItemClick: (accountId: AccountId) -> Unit,
) = Box(modifier = Modifier.fillMaxWidth()) {
    when (item) {
        is AccountsListItem.AccountItem -> AccountItemRow(
            account = item.account,
            ciName = item.ciName,
            ciIcon = item.ciIcon,
            showDeleteIcon = viewState.isEditModeOn,
        ) { onItemClick(item.account.localId) }

        is AccountsListItem.HeaderItem -> HeaderItemRow(item.ciName)
    }
}

@Composable
private fun AccountItemRow(
    account: Account,
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    @SuppressWarnings("UnusedPrivateMember") showDeleteIcon: Boolean,
    onItemClick: () -> Unit
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = SPACING_SMALL)
        .clickable { onItemClick() },
    border = if (account.isSelected) {
        BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
    } else {
        null
    },
) {
    Row {
        AccountAvatar(
            accountUrl = account.avatar,
            ciName = ciName,
            ciIcon = ciIcon,
        )
        AccountInfoItemView(
            name = account.name,
            email = account.email,
            modifier = Modifier
                .padding(start = SPACING_SMALL)
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun AccountInfoItemView(
    name: String,
    email: String,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Text(
        text = name,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.padding(top = SPACING_SMALL))
    Text(
        text = email,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

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
