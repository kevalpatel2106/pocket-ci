package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem
import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem.AccountItem

interface InsertAccountListHeaders {
    operator fun invoke(before: AccountItem?, after: AccountItem?): AccountsListItem?
}
