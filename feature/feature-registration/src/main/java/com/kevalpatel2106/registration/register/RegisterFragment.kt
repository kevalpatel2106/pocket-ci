package com.kevalpatel2106.registration.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.registration.register.eventHandler.RegisterVmEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel by viewModels<RegisterViewModel>()

    @Inject
    internal lateinit var vmEventHandler: RegisterVmEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { RegisterScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
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
}
