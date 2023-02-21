package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.feature.account.list.AccountsListItem
import com.kevalpatel2106.feature.account.list.AccountsListItem.AccountItem

interface InsertAccountListHeaders {
    operator fun invoke(before: AccountItem?, after: AccountItem?): AccountsListItem?
}
