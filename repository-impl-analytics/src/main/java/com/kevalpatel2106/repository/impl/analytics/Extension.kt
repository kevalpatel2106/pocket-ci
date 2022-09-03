package com.kevalpatel2106.repository.impl.analytics

import androidx.core.os.bundleOf

internal fun Map<String, String?>.toBundle() = bundleOf().also {
    forEach { (key, value) -> it.putString(key, value) }
}
