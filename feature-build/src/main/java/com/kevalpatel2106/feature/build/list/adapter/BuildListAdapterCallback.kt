package com.kevalpatel2106.feature.build.list.adapter

import com.kevalpatel2106.entity.Build

internal interface BuildListAdapterCallback {
    fun onBuildSelected(build: Build)
}
