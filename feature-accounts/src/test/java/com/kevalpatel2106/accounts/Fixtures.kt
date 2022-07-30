package com.kevalpatel2106.accounts

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem
import com.kevalpatel2106.coreTest.getAccountFixture

internal fun getAccountItemFixture(fixture: KFixture) = AccountsListItem.AccountItem(
    account = getAccountFixture(fixture),
    ciIcon = fixture(),
    ciName = fixture(),
    imageStrokeWidth = fixture(),
)
