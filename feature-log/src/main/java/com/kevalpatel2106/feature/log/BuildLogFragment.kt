package com.kevalpatel2106.feature.log

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.feature.log.BuildLogVMEvent.Close
import com.kevalpatel2106.feature.log.BuildLogVMEvent.CreateLogFile
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ErrorSavingLog
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ScrollToBottom
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ScrollToTop
import com.kevalpatel2106.feature.log.BuildLogViewState.Empty
import com.kevalpatel2106.feature.log.BuildLogViewState.Error
import com.kevalpatel2106.feature.log.BuildLogViewState.Loading
import com.kevalpatel2106.feature.log.BuildLogViewState.Success
import com.kevalpatel2106.feature.log.databinding.FragmentBuildLogBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BuildLogFragment : Fragment(R.layout.fragment_build_log) {
    private val viewModel by viewModels<BuildLogViewModel>()
    private val binding by viewBinding(FragmentBuildLogBinding::bind)
    private val navArgs by navArgs<BuildLogFragmentArgs>()
    private lateinit var logFileSaveHelper: LogFileSaveHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        setUpTitle()
        BuildLogMenuProvider.bindWithLifecycle(this, viewModel)
        logFileSaveHelper = LogFileSaveHelper.bindWithLifecycle(this)
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

    private fun handleViewState(viewState: BuildLogViewState) {
        requireActivity().invalidateMenu()
        when (viewState) {
            is Success -> with(binding.buildLogTextView) {
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

    private fun handleVMEvents(event: BuildLogVMEvent) {
        when (event) {
            ScrollToBottom -> binding.buildLogVerticalScroll.fullScroll(View.FOCUS_DOWN)
            ScrollToTop -> binding.buildLogVerticalScroll.scrollTo(0, 0)
            is CreateLogFile -> logFileSaveHelper.createFile(event.fileName, event.logs)
            is ErrorSavingLog -> showErrorSnack(event.error, R.string.build_log_error_writing_logs)
            Close -> findNavController().navigateUp()
        }
    }
}
