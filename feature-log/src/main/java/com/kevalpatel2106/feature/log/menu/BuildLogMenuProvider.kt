package com.kevalpatel2106.feature.log.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.kevalpatel2106.feature.log.R
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_DECREASE
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_INCREASE

internal class BuildLogMenuProvider @VisibleForTesting constructor(
    private val callback: BuildMenuProviderCallback,
) : MenuProvider {

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val showMenu = callback.shouldShowMenu()
        with(menu) {
            findItem(R.id.menuBuildLogTextSizeUp).isVisible = showMenu
            findItem(R.id.menuBuildLogTextSizeDown).isVisible = showMenu
            findItem(R.id.menuBuildLogSave).isVisible = showMenu
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_build_log, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuBuildLogTextSizeUp -> {
                callback.onTextSizeChanged(TEXT_SIZE_INCREASE)
                true
            }
            R.id.menuBuildLogTextSizeDown -> {
                callback.onTextSizeChanged(TEXT_SIZE_DECREASE)
                true
            }
            R.id.menuBuildLogSave -> {
                callback.onSaveLogs()
                true
            }
            else -> false
        }
    }

    companion object {
        fun bindWithLifecycle(fragment: Fragment, callback: BuildMenuProviderCallback) =
            with(fragment) {
                requireActivity().addMenuProvider(
                    BuildLogMenuProvider(callback),
                    viewLifecycleOwner,
                    Lifecycle.State.RESUMED,
                )
            }
    }
}
