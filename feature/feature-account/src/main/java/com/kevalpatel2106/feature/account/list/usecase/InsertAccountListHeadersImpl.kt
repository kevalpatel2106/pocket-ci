package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.feature.account.list.model.AccountsListItem
import javax.inject.Inject

class InsertAccountListHeadersImpl @Inject constructor() : InsertAccountListHeaders {

    override operator fun invoke(
        before: AccountsListItem.AccountItem?,
        after: AccountsListItem.AccountItem?,
    ): AccountsListItem? = when {
        after == null -> null
        before == null -> AccountsListItem.HeaderItem(after.ciName)
        before.account.type != after.account.type -> AccountsListItem.HeaderItem(after.ciName)
        else -> null
    }
}
