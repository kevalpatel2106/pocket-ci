package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.entity.BuildStatus

internal interface BuildStatusMapper {
    operator fun invoke(conclusion: String?, status: String): BuildStatus
}
