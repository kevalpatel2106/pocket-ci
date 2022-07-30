package com.kevalpatel2106.repositoryImpl.di

import javax.inject.Qualifier

/**
 * Hilt qualifier for injecting application version name.
 *
 * Note: This annotation is not provided in the repo module and instead provided in app module as we
 * want the build config info from app module and not library modules.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class AppVersion
