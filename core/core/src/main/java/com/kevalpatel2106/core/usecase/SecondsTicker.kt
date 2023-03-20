package com.kevalpatel2106.core.usecase

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface SecondsTicker {

    operator fun invoke(): Flow<Long>
}
