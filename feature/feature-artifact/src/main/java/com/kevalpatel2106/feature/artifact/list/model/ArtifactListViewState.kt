package com.kevalpatel2106.feature.artifact.list.model

data class ArtifactListViewState(
    val toolbarTitle: String?,
    val showDownloadingLoader: Boolean
) {

    companion object {
        fun initialState(title: String?): ArtifactListViewState {
            return ArtifactListViewState(title, showDownloadingLoader = false)
        }
    }
}
