package com.kevalpatel2106.projects.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.projects.list.adapter.ProjectListItemType.HEADER_ITEM
import com.kevalpatel2106.projects.list.adapter.ProjectListItemType.PROJECT_ITEM

internal class ProjectAdapter(
    private val callback: ProjectAdapterCallback,
) : PagingDataAdapter<ProjectListItem, RecyclerView.ViewHolder>(ProjectDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        PROJECT_ITEM.ordinal -> ProjectViewHolder.create(parent, callback)
        HEADER_ITEM.ordinal -> ProjectHeaderViewHolder.create(parent)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal ?: RecyclerView.INVALID_TYPE
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
