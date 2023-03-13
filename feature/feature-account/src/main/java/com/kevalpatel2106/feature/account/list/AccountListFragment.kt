package com.kevalpatel2106.feature.account.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.core.baseUi.showErrorSnack
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorLoadingAccounts
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.menu.AccountMenuProvider
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountListFragment : Fragment() {
    private val viewModel by viewModels<AccountListViewModel>()

    @Inject
    lateinit var displayErrorMapper: DisplayErrorMapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { AccountListScreen(displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AccountMenuProvider.bindWithLifecycle(this, viewModel)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, ::handleEventFlow)
    }

    private fun handleEventFlow(event: AccountListVMEvent) {
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
            is ShowDeleteConfirmation -> showDeleteConfirmationDialog(event.accountId, event.name)
            AccountRemovedSuccess -> showSnack(R.string.success_removing_account)
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingAccounts -> showErrorSnack(event.error)
            InvalidateOptionsMenu -> requireActivity().invalidateMenu()
        }
    }

    private fun showDeleteConfirmationDialog(accountId: AccountId, name: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.account_remove_confirmation_message, name))
            .setPositiveButton(R.string.remove) { _, _ ->
                viewModel.onAccountDeleteConfirmed(accountId)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
