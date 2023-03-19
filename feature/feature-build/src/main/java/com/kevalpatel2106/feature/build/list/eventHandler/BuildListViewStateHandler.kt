package com.kevalpatel2106.feature.build.list.eventHandler

import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent
import com.kevalpatel2106.feature.build.list.model.BuildListViewState

internal interface BuildListViewStateHandler {

    operator fun invoke(viewState: BuildListViewState)
}
