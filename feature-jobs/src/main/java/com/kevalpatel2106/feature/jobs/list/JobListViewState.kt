package com.kevalpatel2106.feature.jobs.list

data class JobListViewState(val toolbarTitle: String?) {

    companion object {
        fun initialState(navArgs: JobListFragmentArgs) = JobListViewState(navArgs.title)
    }
}
