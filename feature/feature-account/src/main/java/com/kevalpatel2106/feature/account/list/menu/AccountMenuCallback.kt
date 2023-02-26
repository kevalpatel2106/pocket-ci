package com.kevalpatel2106.feature.account.list.menu

internal interface AccountMenuCallback {
    fun editModeStatus(on: Boolean)
    fun isInEditMode() : Boolean
}
