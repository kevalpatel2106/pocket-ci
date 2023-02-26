package com.kevalpatel2106.feature.log

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.kevalpatel2106.feature.log.menu.BuildLogMenuProvider
import com.kevalpatel2106.feature.log.menu.BuildMenuProviderCallback
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class BuildLogMenuProviderTest {
    private val menu = mock<Menu>()
    private val menuInflater = mock<MenuInflater>()
    private val callback = mock<BuildMenuProviderCallback>()
    private val subject = BuildLogMenuProvider(callback)

    @Test
    fun `when text size up menu item clicked then verify text size increased`() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(R.id.menuBuildLogTextSizeUp)

        subject.onMenuItemSelected(menuItem)

        verify(callback).onTextSizeChanged(TextChangeDirection.TEXT_SIZE_INCREASE)
    }

    @Test
    fun `when text size down menu item clicked then verify text size decreased`() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(R.id.menuBuildLogTextSizeDown)

        subject.onMenuItemSelected(menuItem)

        verify(callback).onTextSizeChanged(TextChangeDirection.TEXT_SIZE_DECREASE)
    }

    @Test
    fun `when save menu item clicked then verify save logs called`() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(R.id.menuBuildLogSave)

        subject.onMenuItemSelected(menuItem)

        verify(callback).onSaveLogs()
    }

    @Test
    fun `when preparing menu then menu resource inflated is menu_build_log`() {
        subject.onCreateMenu(menu, menuInflater)

        verify(menuInflater).inflate(R.menu.menu_build_log, menu)
    }

    @Test
    fun `when should show item is false then verify all menu items are hidden when preparing menu`() {
        whenever(callback.shouldShowMenu()).thenReturn(false)
        val sizeUpMenuItem = mock<MenuItem>()
        val sizeDownMenuItem = mock<MenuItem>()
        val saveLog = mock<MenuItem>()

        whenever(menu.findItem(R.id.menuBuildLogTextSizeUp)).thenReturn(sizeUpMenuItem)
        whenever(menu.findItem(R.id.menuBuildLogTextSizeDown)).thenReturn(sizeDownMenuItem)
        whenever(menu.findItem(R.id.menuBuildLogSave)).thenReturn(saveLog)

        subject.onPrepareMenu(menu)

        verify(sizeUpMenuItem).isVisible = false
        verify(sizeDownMenuItem).isVisible = false
        verify(saveLog).isVisible = false
    }

    @Test
    fun `when should show item is true then verify all menu items are visible when preparing menu`() {
        whenever(callback.shouldShowMenu()).thenReturn(true)
        val sizeUpMenuItem = mock<MenuItem>()
        val sizeDownMenuItem = mock<MenuItem>()
        val saveLog = mock<MenuItem>()

        whenever(menu.findItem(R.id.menuBuildLogTextSizeUp)).thenReturn(sizeUpMenuItem)
        whenever(menu.findItem(R.id.menuBuildLogTextSizeDown)).thenReturn(sizeDownMenuItem)
        whenever(menu.findItem(R.id.menuBuildLogSave)).thenReturn(saveLog)

        subject.onPrepareMenu(menu)

        verify(sizeUpMenuItem).isVisible = true
        verify(sizeDownMenuItem).isVisible = true
        verify(saveLog).isVisible = true
    }
}
