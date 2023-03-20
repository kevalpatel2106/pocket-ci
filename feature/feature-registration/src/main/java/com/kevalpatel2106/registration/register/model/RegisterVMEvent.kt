package com.kevalpatel2106.registration.register.model

import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.id.AccountId

internal sealed class RegisterVMEvent {
    data class HandleAuthSuccess(val accountId: AccountId, val accountName: String) :
        RegisterVMEvent()

    object AccountAlreadyAdded : RegisterVMEvent()
    data class ShowErrorAddingAccount(val displayError: DisplayError) : RegisterVMEvent()
}
