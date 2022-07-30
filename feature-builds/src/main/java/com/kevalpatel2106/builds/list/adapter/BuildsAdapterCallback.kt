package com.kevalpatel2106.builds.list.adapter

import com.kevalpatel2106.entity.Build

internal interface BuildsAdapterCallback {
    fun onBuildSelected(build: Build)
}
