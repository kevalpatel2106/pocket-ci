package com.kevalpatel2106.feature.account.list.model

internal data class AccountListViewState(val isEditModeOn: Boolean) {
    companion object {
        fun initialState() = AccountListViewState(isEditModeOn = false)
    }
}
