package com.kevalpatel2106.builds.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.builds.databinding.ListItemBuildBinding
import com.kevalpatel2106.builds.list.adapter.BuildListItem.BuildItem

internal class BuildViewHolder(
    private val binding: ListItemBuildBinding,
    private val adapterCallback: BuildsAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: BuildItem) = with(binding) {
        build = listItem.build
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: BuildsAdapterCallback): BuildViewHolder {
            val binding = ListItemBuildBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return BuildViewHolder(binding, callback)
        }
    }
}
