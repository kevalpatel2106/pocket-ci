package com.kevalpatel2106.feature.artifact.list

data class ArtifactListViewState(
    val toolbarTitle: String?,
    val isRefreshing: Boolean,
    val showDownloadingLoader: Boolean
) {

    companion object {
        fun initialState(title: String?): ArtifactListViewState {
            return ArtifactListViewState(title, isRefreshing = false, showDownloadingLoader = false)
        }
    }
}
