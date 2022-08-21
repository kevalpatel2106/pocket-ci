package com.kevalpatel2106.core.viewbinding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T,
    disposeCallback: T.() -> Unit = {},
) = FragmentViewBindingDelegate(this, viewBindingFactory, disposeCallback)

inline fun <T : ViewBinding> Fragment.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }

inline fun <T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }
