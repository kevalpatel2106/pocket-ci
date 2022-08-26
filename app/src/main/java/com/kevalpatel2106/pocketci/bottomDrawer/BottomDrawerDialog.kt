package com.kevalpatel2106.pocketci.bottomDrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenSettingsAndClose
import com.kevalpatel2106.pocketci.databinding.DialogBottomDrawerBinding

internal class BottomDrawerDialog : BottomSheetDialogFragment() {
    private val binding by viewBinding(DialogBottomDrawerBinding::inflate)
    private val viewModel by viewModels<BottomDrawerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVmEvents)
    }

    private fun handleVmEvents(event: BottomDrawerVMEvent) = when (event) {
        OpenAccountsAndClose -> {
            findNavController().navigateToInAppDeeplink(DeepLinkDestinations.AccountList)
            dismiss()
        }
        OpenSettingsAndClose -> {
            findNavController().navigateToInAppDeeplink(DeepLinkDestinations.SettingList)
            dismiss()
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager) = BottomDrawerDialog()
            .show(fragmentManager, BottomDrawerDialog::class.simpleName)
    }
}
