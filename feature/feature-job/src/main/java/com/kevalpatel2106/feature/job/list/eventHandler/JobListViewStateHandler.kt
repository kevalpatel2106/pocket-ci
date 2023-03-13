package com.kevalpatel2106.feature.job.list.eventHandler

import com.kevalpatel2106.feature.job.list.model.JobListViewState

internal interface JobListViewStateHandler {
    operator fun invoke(viewState: JobListViewState)
}
