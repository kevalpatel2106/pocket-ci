package com.kevalpatel2106.feature.logs

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.Close
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.ScrollToBottom
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.ScrollToTop
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Empty
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Error
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Loading
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Success
import com.kevalpatel2106.feature.logs.databinding.FragmentBuildLogsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BuildLogsFragment : Fragment(R.layout.fragment_build_logs) {
    private val viewModel by viewModels<BuildLogsViewModel>()
    private val binding by viewBinding(FragmentBuildLogsBinding::bind)
    private val navArgs by navArgs<BuildLogsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        setUpTitle()
        BuildLogsMenuProvider.bind(this, viewModel)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVMEvents)
    }

    private fun setUpTitle() {
        val titleRes = if (navArgs.jobId.isNullOrBlank()) {
            R.string.title_build_logs_screen
        } else {
            R.string.title_job_logs_screen
        }
        requireActivity().title = getString(titleRes)
    }

    private fun handleViewState(viewState: BuildLogsViewState) {
        setMenuVisibility(viewState is Success)
        when (viewState) {
            is Success -> with(binding.buildLogsTextView) {
                text = viewState.logs
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * viewState.textScale)
            }
            is Error -> {
                Timber.e(viewState.error)
                binding.buildListErrorView.setErrorThrowable(viewState.error)
            }
            Loading -> Unit
            Empty -> Unit
        }
    }

    private fun handleVMEvents(event: BuildLogsVMEvent) {
        when (event) {
            ScrollToBottom -> binding.buildLogsVerticalScroll.fullScroll(View.FOCUS_DOWN)
            ScrollToTop -> binding.buildLogsVerticalScroll.scrollTo(0, 0)
            Close -> findNavController().navigateUp()
        }
    }
}
