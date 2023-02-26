package com.kevalpatel2106.feature.build.markdownView

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.feature.build.R
import com.kevalpatel2106.feature.build.databinding.FragmentMarkdownViewerBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import javax.inject.Inject

@AndroidEntryPoint
class MarkdownViewerFragment : DialogFragment(R.layout.fragment_markdown_viewer) {
    private val binding by viewBinding(FragmentMarkdownViewerBinding::bind)
    private val navArgs by navArgs<MarkdownViewerFragmentArgs>()

    @Inject
    internal lateinit var markwon: Markwon

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markwon.setMarkdown(binding.markdownTextView, navArgs.message)
        binding.markdownTextView.movementMethod = ScrollingMovementMethod()
        requireActivity().title = navArgs.title
    }
}
