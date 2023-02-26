package com.kevalpatel2106.feature.account.list

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Account

sealed class AccountsListItem {
    data class AccountItem(
        val account: Account,
        @DrawableRes val ciIcon: Int,
        @StringRes val ciName: Int,
        @DimenRes val imageStrokeWidth: Int,
    ) : AccountsListItem()

    data class HeaderItem(@StringRes val ciName: Int) : AccountsListItem()
}
