package com.kevalpatel2106.coreViews.useCase

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface LiveTimeDifferenceTicker {

    operator fun invoke(dateOfEventStart: Date, tickMorePrecise: Boolean): Flow<Date>
}
