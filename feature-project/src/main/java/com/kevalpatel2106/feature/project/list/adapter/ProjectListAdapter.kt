package com.kevalpatel2106.feature.project.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItemType.HEADER_ITEM
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItemType.PROJECT_ITEM

internal class ProjectListAdapter(
    private val callback: ProjectListAdapterCallback,
) : PagingDataAdapter<ProjectListItem, RecyclerView.ViewHolder>(ProjectListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        PROJECT_ITEM.ordinal -> ProjectViewHolder.create(parent, callback)
        HEADER_ITEM.ordinal -> ProjectHeaderViewHolder.create(parent)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal
            ?: error("Invalid position: $position. Current list size is $itemCount.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is ProjectViewHolder -> holder.bind(it as ProjectListItem.ProjectItem)
                is ProjectHeaderViewHolder -> holder.bind(it as ProjectListItem.HeaderItem)
                else -> error("Invalid view holder: $holder")
            }
        }
    }
}
