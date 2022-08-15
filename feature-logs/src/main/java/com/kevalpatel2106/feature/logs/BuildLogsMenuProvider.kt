package com.kevalpatel2106.feature.logs

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Success
import com.kevalpatel2106.feature.logs.usecase.TextChangeDirection.Companion.TEXT_SIZE_DECREASE
import com.kevalpatel2106.feature.logs.usecase.TextChangeDirection.Companion.TEXT_SIZE_INCREASE

internal class BuildLogsMenuProvider private constructor(
    private val viewModel: BuildLogsViewModel,
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
        menuInflater.inflate(R.menu.menu_build_logs, menu)
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
        fun bindWithLifecycle(fragment: Fragment, viewModel: BuildLogsViewModel) = with(fragment) {
            requireActivity().addMenuProvider(
                BuildLogsMenuProvider(viewModel),
                viewLifecycleOwner,
                Lifecycle.State.RESUMED,
            )
        }
    }
}
