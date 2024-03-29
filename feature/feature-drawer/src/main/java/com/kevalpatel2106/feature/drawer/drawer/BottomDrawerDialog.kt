package com.kevalpatel2106.feature.drawer.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.drawer.drawer.eventHandler.BottomDrawerVMEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomDrawerDialog : BottomSheetDialogFragment() {
    private val viewModel by viewModels<BottomDrawerViewModel>()

    @Inject
    internal lateinit var vmEventHandler: BottomDrawerVMEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = setContent { BottomDrawerDialogScreen(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
    }

    companion object {
        fun show(fragmentManager: FragmentManager) = BottomDrawerDialog()
            .show(fragmentManager, BottomDrawerDialog::class.simpleName)
    }
}
