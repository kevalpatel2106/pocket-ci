package com.kevalpatel2106.entity.analytics

import androidx.annotation.Size

abstract class Event(
    @Size(min = EVENT_NAME_MIN_SIZE, max = EVENT_NAME_MAX_SIZE) val name: Name,
) {
    abstract val properties: Map<String, String?>

    override fun toString() = "Event: $name\n\n Properties: $properties"

    enum class Name(@Size(min = EVENT_NAME_MIN_SIZE, max = EVENT_NAME_MAX_SIZE) val value: String) {
        CLICK("click")
    }

    companion object {
        private const val EVENT_NAME_MIN_SIZE = 1L
        private const val EVENT_NAME_MAX_SIZE = 40L
    }
}
