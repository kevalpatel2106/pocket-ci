package com.kevalpatel2106.feature.artifact.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItemType.ARTIFACT_ITEM

internal class ArtifactAdapter(
    private val callback: ArtifactAdapterCallback,
) : PagingDataAdapter<ArtifactListItem, RecyclerView.ViewHolder>(ArtifactListItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ARTIFACT_ITEM.ordinal -> ArtifactViewHolder.create(parent, callback)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal
            ?: error("Invalid position: $position. Current list size is $itemCount.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is ArtifactViewHolder -> holder.bind(it as ArtifactItem)
                else -> error("Invalid view holder: $holder")
            }
        }
    }
}
