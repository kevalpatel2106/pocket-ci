package com.kevalpatel2106.registration.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.WindowManager
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.registration.R
import com.kevalpatel2106.registration.databinding.FragmentRegisterBinding
import com.kevalpatel2106.registration.register.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.RegisterVMEvent.ShowErrorAddingAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val viewModel by viewModels<RegisterViewModel>()
    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
            fragment = this@RegisterFragment
        }
        binding.registerTokenEditTextView.setOnEditorActionListener { _, _, _ ->
            submit()
            return@setOnEditorActionListener false
        }
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
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

    fun submit() {
        viewModel.submit(
            inputUrl = binding.registerUrlEditTextView.text.toString(),
            inputToken = binding.registerTokenEditTextView.text.toString(),
        )
    }

    private fun handleViewState(event: RegisterViewState) {
        // Set auth token help
        binding.registerAuthTokenHelp.apply {
            text = getString(
                R.string.register_hint_how_to_get_auth_token,
                event.authTokenHintLink,
            ).parseAsHtml()
            movementMethod = LinkMovementMethod.getInstance()
        }
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
