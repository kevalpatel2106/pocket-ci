package com.kevalpatel2106.feature.job.list.eventHandler

import com.kevalpatel2106.feature.job.list.model.JobListVMEvent

internal interface JobListVmEventHandler {
    operator fun invoke(vmEvent: JobListVMEvent)
}