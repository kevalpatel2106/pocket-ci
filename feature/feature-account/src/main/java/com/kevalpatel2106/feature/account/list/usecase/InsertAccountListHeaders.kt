package com.kevalpatel2106.feature.account.list.usecase

import com.kevalpatel2106.feature.account.list.model.AccountsListItem
import com.kevalpatel2106.feature.account.list.model.AccountsListItem.AccountItem

interface InsertAccountListHeaders {
    operator fun invoke(before: AccountItem?, after: AccountItem?): AccountsListItem?
}
