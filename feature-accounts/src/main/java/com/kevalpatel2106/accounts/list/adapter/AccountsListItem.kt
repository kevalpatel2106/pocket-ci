package com.kevalpatel2106.accounts.list.adapter

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevalpatel2106.accounts.list.adapter.AccountsListItemType.ACCOUNT_ITEM
import com.kevalpatel2106.accounts.list.adapter.AccountsListItemType.HEADER_ITEM
import com.kevalpatel2106.entity.Account

sealed class AccountsListItem(val listItemType: AccountsListItemType, val compareId: String) {
    data class AccountItem(
        val account: Account,
        @DrawableRes val ciIcon: Int,
        @StringRes val ciName: Int,
        @DimenRes val imageStrokeWidth: Int,
    ) : AccountsListItem(ACCOUNT_ITEM, account.localId.toString())

    data class HeaderItem(@StringRes val ciName: Int) :
        AccountsListItem(HEADER_ITEM, ciName.toString())
}
