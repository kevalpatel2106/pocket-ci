package com.kevalpatel2106.cache.di

import javax.inject.Qualifier

/**
 * Hilt qualifier for injecting if the build is debug or not?
 *
 * Note: This annotation is not provided in the repo module and instead provided in app module as we
 * want the build config info from app module and not library modules.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
internal annotation class EnableEncryption
