package com.kevalpatel2106.cache.remoteConfig

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class FirebaseRemoteConfigCacheTest {
    private val mockTask = mock<Task<Boolean>>()
    private val firebaseRemoteConfig = mock<FirebaseRemoteConfig> {
        on { fetchAndActivate() } doReturn mockTask
    }

    @BeforeEach
    fun before() {
        whenever(mockTask.addOnCompleteListener(any())).thenReturn(mockTask)
        whenever(mockTask.addOnFailureListener(any())).thenReturn(mockTask)
    }

    @Test
    fun `when firebase remote config cache initialised then remote config fetched and activated`() {
        FirebaseRemoteConfigCache(firebaseRemoteConfig)

        verify(firebaseRemoteConfig).fetchAndActivate()
    }

    @Test
    fun `when firebase remote config cache initialised then completion listener attached`() {
        FirebaseRemoteConfigCache(firebaseRemoteConfig)

        verify(mockTask).addOnCompleteListener(any())
    }

    @Test
    fun `when firebase remote config cache initialised then failure listener attached`() {
        FirebaseRemoteConfigCache(firebaseRemoteConfig)

        verify(mockTask).addOnFailureListener(any())
    }
}
