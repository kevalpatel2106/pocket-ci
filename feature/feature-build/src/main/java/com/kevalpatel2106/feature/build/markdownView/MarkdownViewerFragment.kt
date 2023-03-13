package com.kevalpatel2106.feature.build.markdownView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.kevalpatel2106.core.ui.extension.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarkdownViewerFragment : DialogFragment() {
    private val navArgs by navArgs<MarkdownViewerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { MarkdownViewerScreen(navArgs.message) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().title = navArgs.title
    }
}
