package com.kevalpatel2106.feature.account

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem

internal fun getAccountItemFixture(fixture: KFixture) = AccountsListItem.AccountItem(
    account = getAccountFixture(fixture),
    ciIcon = fixture(),
    ciName = fixture(),
    imageStrokeWidth = fixture(),
)
