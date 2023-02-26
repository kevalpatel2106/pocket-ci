package com.kevalpatel2106.core.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectInFragment(
    fragment: Fragment,
    crossinline action: suspend (value: T) -> Unit,
) {
    fragment.lifecycleScope.launch {
        if (fragment.view == null) {
            flowWithLifecycle(fragment.lifecycle)
        } else {
            flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle)
        }.collectLatest { action(it) }
    }
}

inline fun <T> Flow<T>.collectInActivity(
    activity: AppCompatActivity,
    crossinline action: suspend (value: T) -> Unit,
) {
    activity.lifecycleScope.launch {
        flowWithLifecycle(activity.lifecycle).collectLatest { action(it) }
    }
}

inline fun <T : Any> MutableStateFlow<T>.modify(
    viewModelScope: CoroutineScope,
    crossinline modify: T.() -> T,
) {
    viewModelScope.launch { emit(value.modify()) }
}

suspend inline fun <T : Any> MutableStateFlow<T>.modify(modify: T.() -> T) = emit(value.modify())
