package com.kevalpatel2106.registration.ciSelection.adapter

import com.kevalpatel2106.entity.CIInfo

internal interface CISelectionAdapterCallback {
    fun onCISelected(ci: CIInfo)
}
