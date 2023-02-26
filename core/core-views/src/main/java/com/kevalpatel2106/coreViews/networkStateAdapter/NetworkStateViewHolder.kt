package com.kevalpatel2106.coreViews.networkStateAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.coreViews.databinding.ListItemNetworkStateBinding
import timber.log.Timber

class NetworkStateViewHolder(
    private val binding: ListItemNetworkStateBinding,
    callback: NetworkStateCallback,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.callback = callback
    }

    fun bind(loadState: LoadState) = with(binding) {
        networkStateViewFlipper.displayedChild = when (loadState) {
            is LoadState.Loading -> POS_LOADING
            is LoadState.Error -> {
                Timber.e(loadState.error)
                networkStateErrorView.setErrorThrowable(loadState.error)
                POS_ERROR
            }
            else -> POS_ERROR
        }
    }

    companion object {
        private const val POS_LOADING = 0
        private const val POS_ERROR = 1

        fun create(parent: ViewGroup, callback: NetworkStateCallback) = NetworkStateViewHolder(
            ListItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback,
        )
    }
}
