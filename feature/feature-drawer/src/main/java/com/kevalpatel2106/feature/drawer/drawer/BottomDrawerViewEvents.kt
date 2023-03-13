package com.kevalpatel2106.feature.drawer.drawer

import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem

internal interface BottomDrawerViewEvents {
    fun onDrawerItemClicked(item: BottomDrawerItem)
}
