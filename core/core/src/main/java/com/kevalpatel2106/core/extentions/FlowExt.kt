package com.kevalpatel2106.core.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// With screens with compose collectAsState in compose should be used instead of this.
inline fun <T> Flow<T>.collectStateInFragment(
    fragment: Fragment,
    crossinline action: suspend (value: T) -> Unit,
) {
    val lifecycle = if (fragment.view == null) {
        fragment.lifecycle
    } else {
        fragment.viewLifecycleOwner.lifecycle
    }
    fragment.lifecycleScope.launch {
        flowWithLifecycle(lifecycle)
            .collectLatest { action(it) }   // Only interested in latest events
    }
}

inline fun <T> Flow<T>.collectVMEventInFragment(
    fragment: Fragment,
    crossinline action: suspend (value: T) -> Unit,
) {
    val lifecycle = if (fragment.view == null) {
        fragment.lifecycle
    } else {
        fragment.viewLifecycleOwner.lifecycle
    }
    flowWithLifecycle(lifecycle)
        .buffer()   // We want to get all the events
        .onEach { action(it) }
        .launchIn(fragment.lifecycleScope)
}

inline fun <T> Flow<T>.collectStateInActivity(
    activity: AppCompatActivity,
    crossinline action: suspend (value: T) -> Unit,
) {
    activity.lifecycleScope.launch {
        flowWithLifecycle(activity.lifecycle).collectLatest { action(it) }
    }
}

inline fun <T> Flow<T>.collectVMEventsInActivity(
    activity: AppCompatActivity,
    crossinline action: suspend (value: T) -> Unit,
) {
    flowWithLifecycle(activity.lifecycle)
        .buffer()   // We want to get all the events
        .onEach { action(it) }
        .launchIn(activity.lifecycleScope)
}
