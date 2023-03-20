package com.kevalpatel2106.feature.setting.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.setting.webView.model.WebViewViewState
import com.kevalpatel2106.feature.setting.webView.screen.WebviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private val viewModel: WebViewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView {
        viewModel.viewState.collectStateInFragment(this, ::handleViewStates)
        return setContent { WebviewScreen() }
    }

    private fun handleViewStates(viewState: WebViewViewState) {
        activity?.setTitle(viewState.title)
    }
}
