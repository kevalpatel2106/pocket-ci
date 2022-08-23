package com.kevalpatel2106.core.errorHandling

import com.kevalpatel2106.entity.DisplayError
import java.util.Date

interface DisplayErrorMapper {
    operator fun invoke(
        throwable: Throwable,
        shortMessage: Boolean = false,
        time: Date = Date(),
    ): DisplayError
}
