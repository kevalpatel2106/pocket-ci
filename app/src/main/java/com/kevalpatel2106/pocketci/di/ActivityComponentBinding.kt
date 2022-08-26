package com.kevalpatel2106.pocketci.di

import com.kevalpatel2106.pocketci.host.usecase.HandleHostVMEvents
import com.kevalpatel2106.pocketci.host.usecase.HandleHostVMEventsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class ActivityComponentBinding {

    @Binds
    @ActivityScoped
    abstract fun bindHandleHostVMEvents(impl: HandleHostVMEventsImpl): HandleHostVMEvents
}
