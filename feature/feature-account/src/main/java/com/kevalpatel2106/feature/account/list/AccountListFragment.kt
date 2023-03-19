package com.kevalpatel2106.feature.account.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.list.eventHandler.AccountListVmEventHandler
import com.kevalpatel2106.feature.account.list.menu.AccountMenuProvider
import com.kevalpatel2106.feature.account.list.screen.AccountListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountListFragment : Fragment() {
    private val viewModel by viewModels<AccountListViewModel>()

    @Inject
    internal lateinit var displayErrorMapper: DisplayErrorMapper

    @Inject
    internal lateinit var vmEventHandler: AccountListVmEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { AccountListScreen(displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AccountMenuProvider.bindWithLifecycle(this, viewModel)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
    }

    internal fun showDeleteConfirmationDialog(accountId: AccountId, name: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.account_remove_confirmation_message, name))
            .setPositiveButton(R.string.remove) { _, _ ->
                viewModel.onAccountDeleteConfirmed(accountId)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
