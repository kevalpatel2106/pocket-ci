package com.kevalpatel2106.registration.ciSelection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.Close
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.OpenRegisterAccount
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CISelectionFragment : Fragment() {
    private val viewModel by viewModels<CISelectionViewModel>()

    @Inject
    lateinit var displayErrorMapper: DisplayErrorMapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { CISelectionScreen(displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun handleSingleEvent(event: CISelectionVMEvent) {
        when (event) {
            is OpenRegisterAccount -> findNavController().navigate(
                CISelectionFragmentDirections.actionCiSelectionToRegister(event.ciInfo),
            )

            Close -> findNavController().navigateUp()
        }
    }
}
