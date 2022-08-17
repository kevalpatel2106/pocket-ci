package com.kevalpatel2106.feature.account.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.R
import com.kevalpatel2106.feature.account.databinding.FragmentAccountListBinding
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RefreshAccountList
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RetryLoading
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.adapter.AccountListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AccountListFragment : Fragment(R.layout.fragment_account_list) {
    private val viewModel by viewModels<AccountListViewModel>()
    private val binding by viewBinding(FragmentAccountListBinding::bind) {
        accountListRecyclerView.adapter = null
    }
    private val accountListAdapter by lazy(LazyThreadSafetyMode.NONE) { AccountListAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        viewModel.pageViewState.collectInFragment(this, accountListAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleEventFlow)
    }

    private fun prepareRecyclerView() = with(binding.accountListRecyclerView) {
        adapter = accountListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
    }

    private fun observeAdapterLoadState() = with(binding) {
        accountListAdapter.loadStateFlow.collectInFragment(this@AccountListFragment) { loadState ->
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            accountListViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    accountListErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.isEmptyList(accountListAdapter) -> POS_EMPTY_VIEW
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                else -> POS_LIST
            }
        }
    }

    private fun handleEventFlow(event: AccountListVMEvent) {
        when (event) {
            is OpenProjects -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.ProjectList(event.accountId),
                cleanUpStack = true,
            )
            RetryLoading -> accountListAdapter.retry()
            RefreshAccountList -> accountListAdapter.refresh()
            ShowErrorRemovingAccount -> showSnack(getString(R.string.error_removing_account))
            ShowErrorSelectingAccount -> showSnack(getString(R.string.error_selecting_account))
            OpenCiSelection -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.CiSelection,
            )
            is ShowDeleteConfirmation -> showDeleteConfirmationDialog(event.accountId, event.name)
            AccountRemovedSuccess -> {
                showSnack(getString(R.string.success_removing_account))
                accountListAdapter.refresh()
            }
            Close -> findNavController().navigateUp()
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