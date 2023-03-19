package com.kevalpatel2106.feature.build.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.build.detail.eventHandler.BuildDetailVmEventHandler
import com.kevalpatel2106.feature.build.detail.screen.BuildDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuildDetailFragment : Fragment() {
    private val viewModel by viewModels<BuildDetailViewModel>()

    @Inject
    internal lateinit var vmEventHandler: BuildDetailVmEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView {
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
        return setContent { BuildDetailScreen() }
    }
}
