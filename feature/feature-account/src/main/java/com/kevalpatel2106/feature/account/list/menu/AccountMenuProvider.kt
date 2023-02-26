package com.kevalpatel2106.feature.account.list.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.kevalpatel2106.feature.account.R

internal class AccountMenuProvider @VisibleForTesting constructor(
    private val callback: AccountMenuCallback,
) : MenuProvider {

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        with(menu) {
            findItem(R.id.editAccount).isVisible = callback.isInEditMode()
            findItem(R.id.doneEditingAccount).isVisible = !callback.isInEditMode()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_account_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.editAccount -> {
                callback.editModeStatus(true)
                true
            }
            R.id.doneEditingAccount -> {
                callback.editModeStatus(false)
                true
            }

            else -> false
        }
    }

    companion object {
        fun bindWithLifecycle(fragment: Fragment, callback: AccountMenuCallback) = with(fragment) {
            requireActivity().addMenuProvider(
                AccountMenuProvider(callback),
                viewLifecycleOwner,
                Lifecycle.State.RESUMED,
            )
        }
    }
}
