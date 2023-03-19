package com.kevalpatel2106.feature.build.list.model

data class BuildListViewState(val toolbarTitle: String) {

    companion object {
        fun initialState() = BuildListViewState("")
    }
}
