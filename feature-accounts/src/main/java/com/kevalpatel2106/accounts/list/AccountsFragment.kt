package com.kevalpatel2106.accounts.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevalpatel2106.accounts.R
import com.kevalpatel2106.accounts.databinding.FragmentAccountsBinding
import com.kevalpatel2106.accounts.list.AccountsVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.accounts.list.AccountsVMEvent.OpenCiSelection
import com.kevalpatel2106.accounts.list.AccountsVMEvent.OpenProjects
import com.kevalpatel2106.accounts.list.AccountsVMEvent.RefreshAccounts
import com.kevalpatel2106.accounts.list.AccountsVMEvent.RetryLoading
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowErrorView
import com.kevalpatel2106.accounts.list.adapter.AccountsAdapter
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.entity.id.AccountId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsFragment : Fragment(R.layout.fragment_accounts) {
    private val viewModel by viewModels<AccountsViewModel>()
    private val binding by viewBinding(FragmentAccountsBinding::bind) {
        accountsRecyclerView.adapter = null
    }
    private val accountsAdapter by lazy(LazyThreadSafetyMode.NONE) { AccountsAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        viewModel.pageViewState.collectInFragment(this, accountsAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleEventFlow)
    }

    private fun prepareRecyclerView() = with(binding.accountsRecyclerView) {
        adapter = accountsAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

    private fun observeAdapterLoadState() {
        accountsAdapter.loadStateFlow.collectInFragment(this) { loadState ->
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            binding.accountsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> POS_ERROR
                sourceStates.isEmptyList(accountsAdapter) -> POS_EMPTY_VIEW
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                else -> POS_LIST
            }
        }
    }

    private fun handleEventFlow(event: AccountsVMEvent) {
        when (event) {
            is OpenProjects -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.ProjectsList(event.accountId),
            )
            RetryLoading -> accountsAdapter.retry()
            RefreshAccounts -> accountsAdapter.refresh()
            ShowErrorRemovingAccount -> showSnack(getString(R.string.error_removing_account))
            ShowErrorSelectingAccount -> showSnack(getString(R.string.error_selecting_account))
            OpenCiSelection -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.CiSelection
            )
            is ShowDeleteConfirmation -> showDeleteConfirmationDialog(event.accountId, event.name)
            AccountRemovedSuccess -> {
                showSnack(getString(R.string.success_removing_account))
                accountsAdapter.refresh()
            }
            ShowErrorView -> binding.accountsViewFlipper.displayedChild = POS_ERROR
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

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}
