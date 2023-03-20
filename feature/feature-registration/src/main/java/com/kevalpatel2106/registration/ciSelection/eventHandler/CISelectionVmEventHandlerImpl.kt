package com.kevalpatel2106.registration.ciSelection.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.registration.ciSelection.CISelectionFragmentDirections
import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent
import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent.Close
import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent.OpenRegisterAccount
import javax.inject.Inject

internal class CISelectionVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : CISelectionVmEventHandler {

    override operator fun invoke(event: CISelectionVMEvent) {
        when (event) {
            is OpenRegisterAccount -> fragment.findNavController().navigate(
                CISelectionFragmentDirections.actionCiSelectionToRegister(event.ciInfo),
            )

            Close -> fragment.findNavController().navigateUp()
        }
    }
}