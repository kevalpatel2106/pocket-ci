package com.kevalpatel2106.registration.register

import com.kevalpatel2106.entity.id.AccountId

internal sealed class RegisterVMEvent {
    data class HandleAuthSuccess(val accountId: AccountId, val accountName: String) :
        RegisterVMEvent()

    object AccountAlreadyAdded : RegisterVMEvent()
    object ShowErrorAddingAccount : RegisterVMEvent()
}
