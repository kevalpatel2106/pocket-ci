package com.kevalpatel2106.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("UnnecessaryAbstractClass")
abstract class BaseViewModel<ViewModelEvents> : ViewModel() {

    @Suppress("PropertyName", "VariableNaming")
    protected val _vmEventsFlow = MutableSharedFlow<ViewModelEvents>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()
}
