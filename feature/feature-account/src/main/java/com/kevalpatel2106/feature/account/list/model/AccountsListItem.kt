package com.kevalpatel2106.feature.account.list.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Account

sealed class AccountsListItem {
    data class AccountItem(
        val account: Account,
        @DrawableRes val ciIcon: Int,
        @StringRes val ciName: Int,
    ) : AccountsListItem()

    data class HeaderItem(@StringRes val ciName: Int) : AccountsListItem()
}
