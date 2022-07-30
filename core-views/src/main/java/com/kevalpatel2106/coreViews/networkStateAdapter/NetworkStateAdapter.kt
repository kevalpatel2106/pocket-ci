package com.kevalpatel2106.coreViews.networkStateAdapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class NetworkStateAdapter(
    private val callback: NetworkStateCallback,
) : LoadStateAdapter<NetworkStateViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateViewHolder.create(parent, callback)
}
