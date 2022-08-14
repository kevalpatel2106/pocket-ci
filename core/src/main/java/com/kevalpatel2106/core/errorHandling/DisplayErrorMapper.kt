package com.kevalpatel2106.core.errorHandling

import com.kevalpatel2106.entity.DisplayError

interface DisplayErrorMapper {
    operator fun invoke(throwable: Throwable): DisplayError
}
