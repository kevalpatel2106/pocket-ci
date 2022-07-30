package com.kevalpatel2106.registration.di

import com.kevalpatel2106.registration.register.usecase.SanitiseRegisterInput
import com.kevalpatel2106.registration.register.usecase.SanitiseRegisterInputImpl
import com.kevalpatel2106.registration.register.usecase.ValidateRegisterInput
import com.kevalpatel2106.registration.register.usecase.ValidateRegisterInputImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RegisterModuleBinding {

    @Binds
    @ViewModelScoped
    abstract fun bindSanitiseRegisterInput(
        impl: SanitiseRegisterInputImpl,
    ): SanitiseRegisterInput

    @Binds
    @ViewModelScoped
    abstract fun bindValidateRegisterInput(
        impl: ValidateRegisterInputImpl,
    ): ValidateRegisterInput
}
