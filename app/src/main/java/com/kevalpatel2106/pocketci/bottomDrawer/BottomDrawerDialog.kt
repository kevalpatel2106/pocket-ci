package com.kevalpatel2106.pocketci.bottomDrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.pocketci.bottomDrawer.usecase.HandleBottomDrawerVMEvents
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BottomDrawerDialog : BottomSheetDialogFragment() {
    private val viewModel by viewModels<BottomDrawerViewModel>()

    @Inject
    internal lateinit var handleBottomDrawerVMEvents: HandleBottomDrawerVMEvents

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            BottomDrawerDialogView(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectInFragment(this, handleBottomDrawerVMEvents::invoke)
    }

    companion object {
        fun show(fragmentManager: FragmentManager) = BottomDrawerDialog()
            .show(fragmentManager, BottomDrawerDialog::class.simpleName)
    }
}
