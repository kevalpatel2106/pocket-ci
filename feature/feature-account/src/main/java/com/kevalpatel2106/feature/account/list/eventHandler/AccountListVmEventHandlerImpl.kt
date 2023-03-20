package com.kevalpatel2106.feature.account.list.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.ui.errorHandling.showErrorSnack
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.account.list.AccountListFragment
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorLoadingAccounts
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorSelectingAccount
import javax.inject.Inject

internal class AccountListVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : AccountListVmEventHandler {

    override fun invoke(event: AccountListVMEvent): Unit = with(fragment as AccountListFragment) {
        when (event) {
            is OpenProjects -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.ProjectList(event.accountId),
                cleanUpStack = true,
            )
            is ShowErrorRemovingAccount -> {
                showErrorSnack(event.error, R.string.error_removing_account)
            }
            is ShowErrorSelectingAccount -> {
                showErrorSnack(event.error, R.string.error_selecting_account)
            }
            OpenCiSelection -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.CiSelection,
            )
            is ShowDeleteConfirmation -> fragment.showDeleteConfirmationDialog(
                accountId = event.accountId,
                name = event.name,
            )
            AccountRemovedSuccess -> showSnack(R.string.success_removing_account)
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingAccounts -> showErrorSnack(event.error)
            InvalidateOptionsMenu -> requireActivity().invalidateMenu()
        }
    }
}