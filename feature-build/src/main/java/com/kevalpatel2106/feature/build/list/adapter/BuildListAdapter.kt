package com.kevalpatel2106.feature.build.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem.BuildItem
import com.kevalpatel2106.feature.build.list.adapter.BuildListItemType.BUILD_ITEM

internal class BuildListAdapter(
    private val callback: BuildListAdapterCallback,
) : PagingDataAdapter<BuildListItem, RecyclerView.ViewHolder>(BuildListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        BUILD_ITEM.ordinal -> BuildViewHolder.create(parent, callback)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal
            ?: error("Invalid position: $position. Current list size is $itemCount.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is BuildViewHolder -> holder.bind(it as BuildItem)
                else -> error("Invalid view holder: $holder")
            }
        }
    }
}
