package com.kevalpatel2106.registration.ciSelection.model

import com.kevalpatel2106.entity.CIInfo

internal sealed class CISelectionVMEvent {
    data class OpenRegisterAccount(val ciInfo: CIInfo) : CISelectionVMEvent()
    object Close : CISelectionVMEvent()
}
