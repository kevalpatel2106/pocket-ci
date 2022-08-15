package com.kevalpatel2106.settings.webView

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.settings.R
import com.kevalpatel2106.settings.databinding.FragmentWebviewBinding
import com.kevalpatel2106.settings.webView.WebViewVMEvent.Close
import com.kevalpatel2106.settings.webView.WebViewVMEvent.Reload
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
        if (binding.webview.url != getString(viewState.linkUrl)) {
            binding.webview.loadUrl(getString(viewState.linkUrl))
        }
    }

    private fun handleVMEvents(event: WebViewVMEvent) {
        when (event) {
            is Reload -> binding.webview.loadUrl(getString(event.urlToReload))
            Close -> findNavController().navigateUp()
        }
    }

    private fun setUpWebView() = with(binding.webview) {
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        settings.loadsImagesAutomatically = true
        settings.javaScriptEnabled = false

        webViewClient = CustomWebViewClient(model = viewModel)
    }
}
