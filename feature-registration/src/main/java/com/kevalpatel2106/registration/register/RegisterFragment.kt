package com.kevalpatel2106.registration.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.setContent
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.registration.register.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.RegisterVMEvent.ShowErrorAddingAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { RegisterScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    private fun handleSingleEvent(event: RegisterVMEvent) {
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
