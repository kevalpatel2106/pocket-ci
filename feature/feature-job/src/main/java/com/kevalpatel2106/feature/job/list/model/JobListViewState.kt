package com.kevalpatel2106.feature.job.list.model

import com.kevalpatel2106.feature.job.list.JobListFragmentArgs

data class JobListViewState(val toolbarTitle: String?) {

    companion object {
        fun initialState(navArgs: JobListFragmentArgs) = JobListViewState(navArgs.title)
    }
}
