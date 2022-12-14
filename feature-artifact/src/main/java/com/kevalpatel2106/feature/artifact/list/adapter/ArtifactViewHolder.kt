package com.kevalpatel2106.feature.artifact.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.artifact.databinding.ListItemArtifactBinding
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem

internal class ArtifactViewHolder(
    private val binding: ListItemArtifactBinding,
    private val adapterCallback: ArtifactListAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ArtifactItem) = with(binding) {
        artifactItem = listItem
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: ArtifactListAdapterCallback): ArtifactViewHolder {
            val binding = ListItemArtifactBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ArtifactViewHolder(binding, callback)
        }
    }
}
