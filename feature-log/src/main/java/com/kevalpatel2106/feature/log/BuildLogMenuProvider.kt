package com.kevalpatel2106.feature.log

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.kevalpatel2106.feature.log.BuildLogViewState.Success
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_DECREASE
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_INCREASE

internal class BuildLogMenuProvider private constructor(
    private val viewModel: BuildLogViewModel,
) : MenuProvider {

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val showMenu = viewModel.viewState.value is Success
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
                viewModel.onTextSizeChanged(TEXT_SIZE_INCREASE)
                true
            }
            R.id.menuBuildLogTextSizeDown -> {
                viewModel.onTextSizeChanged(TEXT_SIZE_DECREASE)
                true
            }
            R.id.menuBuildLogSave -> {
                viewModel.onSaveLogs()
                true
            }
            else -> false
        }
    }

    companion object {
        fun bindWithLifecycle(fragment: Fragment, viewModel: BuildLogViewModel) = with(fragment) {
            requireActivity().addMenuProvider(
                BuildLogMenuProvider(viewModel),
                viewLifecycleOwner,
                Lifecycle.State.RESUMED,
            )
        }
    }
}
