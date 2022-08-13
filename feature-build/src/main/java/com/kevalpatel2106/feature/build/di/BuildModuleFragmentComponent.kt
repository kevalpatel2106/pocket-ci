package com.kevalpatel2106.feature.build.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import io.noties.markwon.Markwon

@Module
@InstallIn(FragmentComponent::class)
internal class BuildModuleFragmentComponent {

    @FragmentScoped
    @Provides
    fun provideMarkwon(fragment: Fragment) = Markwon.create(fragment.requireContext())
}
