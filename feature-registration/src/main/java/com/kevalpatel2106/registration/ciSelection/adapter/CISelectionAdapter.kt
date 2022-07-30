package com.kevalpatel2106.registration.ciSelection.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kevalpatel2106.entity.CIInfo

internal class CISelectionAdapter(
    private val callback: CISelectionAdapterCallback,
) : ListAdapter<CIInfo, CISelectionViewHolder>(CiSelectionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CISelectionViewHolder.create(parent, callback)

    override fun onBindViewHolder(holder: CISelectionViewHolder, position: Int) =
        holder.bind(getItem(position))
}
