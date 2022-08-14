package com.kevalpatel2106.coreViews.di

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.scopes.ViewScoped

@Module
@InstallIn(ViewComponent::class)
internal class CoreViewComponent {

    @Provides
    @ViewScoped
    fun provideClipboard(application: Application): ClipboardManager =
        application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}
