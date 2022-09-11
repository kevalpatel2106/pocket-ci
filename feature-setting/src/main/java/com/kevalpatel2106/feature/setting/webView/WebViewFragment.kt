package com.kevalpatel2106.feature.setting.webView

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.feature.setting.BuildConfig
import com.kevalpatel2106.feature.setting.R
import com.kevalpatel2106.feature.setting.databinding.FragmentWebviewBinding
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Close
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Reload
import com.kevalpatel2106.feature.setting.webView.usecase.CustomWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment(R.layout.fragment_webview) {
    private val viewModel: WebViewViewModel by viewModels()
    private val binding by viewBinding(FragmentWebviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        setUpWebView()
        viewModel.viewState.collectInFragment(this, ::handleViewStates)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVMEvents)
    }

    private fun handleViewStates(viewState: WebViewViewState) {
        activity?.setTitle(viewState.title)
        if (binding.webview.url != viewState.linkUrl.value) {
            binding.webview.loadUrl(viewState.linkUrl.value)
        }
    }

    private fun handleVMEvents(event: WebViewVMEvent) {
        when (event) {
            is Reload -> binding.webview.loadUrl(event.urlToReload.value)
            Close -> findNavController().navigateUp()
        }
    }

    private fun setUpWebView() = with(binding.webview) {
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        settings.loadsImagesAutomatically = true
        settings.javaScriptEnabled = false

        webViewClient = CustomWebViewClient(model = viewModel)
        if (BuildConfig.DEBUG) {
            clearCache(true)
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
    }
}
