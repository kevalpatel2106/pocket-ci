package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.CIType
import dagger.MapKey

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class CIConnectorBindingKey(val value: CIType)
