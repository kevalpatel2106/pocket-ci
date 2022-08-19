package com.kevalpatel2106.repositoryImpl.di

import javax.inject.Qualifier

/**
 * Hilt qualifier for injecting master key generated by androidX security
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
internal annotation class MasterKey
