package com.kevalpatel2106.feature.account.list

internal data class AccountListViewState(val isEditModeOn: Boolean) {
    companion object {
        fun initialState() = AccountListViewState(isEditModeOn = false)
    }
}
