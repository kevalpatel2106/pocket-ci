package com.kevalpatel2106.registration.register.analytics

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.analytics.Event

internal data class RegisterEvent(val ciType: CIType) : Event(Name.REGISTER_CI) {
    override val properties: Map<String, String?> = mapOf(
        ITEM_NAME to ciType.name,
    )

    companion object {
        private const val ITEM_NAME = "item_name"
    }
}
