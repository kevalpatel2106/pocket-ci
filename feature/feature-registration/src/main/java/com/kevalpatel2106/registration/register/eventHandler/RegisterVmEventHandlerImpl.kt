package com.kevalpatel2106.registration.register.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.ui.errorHandling.showErrorSnack
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.registration.register.model.RegisterVMEvent
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.model.RegisterVMEvent.ShowErrorAddingAccount
import javax.inject.Inject

internal class RegisterVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : RegisterVmEventHandler {

    override operator fun invoke(event: RegisterVMEvent): Unit = with(fragment){
        when (event) {
            is HandleAuthSuccess -> {
                showSnack(getString(R.string.register_success_message, event.accountName))
                findNavController().navigateToInAppDeeplink(
                    DeepLinkDestinations.ProjectList(event.accountId),
                    cleanUpStack = true,
                )
            }
            AccountAlreadyAdded -> showSnack(R.string.register_error_account_already_added)
            is ShowErrorAddingAccount -> {
                showErrorSnack(event.displayError, R.string.error_adding_account)
            }
        }
    }
}