package com.kevalpatel2106.registration.ciSelection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.registration.R
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.OpenRegisterAccount
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.LoadingState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.SuccessState
import com.kevalpatel2106.registration.ciSelection.adapter.CISelectionAdapter
import com.kevalpatel2106.registration.databinding.FragmentSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CISelectionFragment : Fragment(R.layout.fragment_selection) {
    private val viewModel by viewModels<CISelectionViewModel>()
    private val binding by viewBinding(FragmentSelectionBinding::bind) {
        ciListRecyclerView.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.ciListRecyclerView) {
        adapter = CISelectionAdapter(viewModel)
    }

    private fun handleSingleEvent(event: CISelectionVMEvent) = when (event) {
        is OpenRegisterAccount -> findNavController().navigate(
            CISelectionFragmentDirections.actionCiSelectionToRegister(event.ciInfo),
        )
    }

    private fun handleViewState(state: CISelectionViewState) = when (state) {
        is SuccessState -> {
            (binding.ciListRecyclerView.adapter as CISelectionAdapter).submitList(state.listOfCi)
        }
        is ErrorState -> binding.ciListErrorView.setErrorThrowable(state.throwable)
        LoadingState -> Unit // No-op
    }
}
