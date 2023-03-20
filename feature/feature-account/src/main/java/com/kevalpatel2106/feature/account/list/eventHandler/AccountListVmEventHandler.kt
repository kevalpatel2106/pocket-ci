package com.kevalpatel2106.feature.account.list.eventHandler

import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent

internal interface AccountListVmEventHandler {
    operator fun invoke(event: AccountListVMEvent)
}