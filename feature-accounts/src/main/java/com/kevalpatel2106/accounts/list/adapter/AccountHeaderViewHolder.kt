package com.kevalpatel2106.accounts.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.accounts.databinding.ListItemAccountHeaderBinding
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem.HeaderItem

internal class AccountHeaderViewHolder(
    private val binding: ListItemAccountHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HeaderItem) {
        binding.item = item
    }

    companion object {
        fun create(parent: ViewGroup): AccountHeaderViewHolder {
            val binding = ListItemAccountHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return AccountHeaderViewHolder(binding)
        }
    }
}
