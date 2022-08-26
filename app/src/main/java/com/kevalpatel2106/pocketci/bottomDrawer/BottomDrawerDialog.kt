package com.kevalpatel2106.pocketci.bottomDrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.pocketci.bottomDrawer.usecase.HandleBottomDrawerVMEvents
import com.kevalpatel2106.pocketci.databinding.DialogBottomDrawerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BottomDrawerDialog : BottomSheetDialogFragment() {
    private val binding by viewBinding(DialogBottomDrawerBinding::inflate)
    private val viewModel by viewModels<BottomDrawerViewModel>()

    @Inject
    internal lateinit var handleBottomDrawerVMEvents: HandleBottomDrawerVMEvents

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
        viewModel.vmEventsFlow.collectInFragment(this, handleBottomDrawerVMEvents::invoke)
    }

    companion object {
        fun show(fragmentManager: FragmentManager) = BottomDrawerDialog()
            .show(fragmentManager, BottomDrawerDialog::class.simpleName)
    }
}
