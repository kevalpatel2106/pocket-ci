package com.kevalpatel2106.registration.ciSelection

import com.kevalpatel2106.entity.CIInfo

internal sealed class CISelectionVMEvent {
    data class OpenRegisterAccount(val ciInfo: CIInfo) : CISelectionVMEvent()
    object Close : CISelectionVMEvent()
}
