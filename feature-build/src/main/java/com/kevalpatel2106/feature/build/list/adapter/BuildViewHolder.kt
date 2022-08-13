package com.kevalpatel2106.feature.build.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.build.databinding.ListItemBuildBinding
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem.BuildItem

internal class BuildViewHolder(
    private val binding: ListItemBuildBinding,
    private val adapterCallback: BuildsAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: BuildItem) = with(binding) {
        buildItem = listItem
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
