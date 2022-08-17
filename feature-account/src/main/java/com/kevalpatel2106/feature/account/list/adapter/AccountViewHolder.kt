package com.kevalpatel2106.feature.account.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.account.databinding.ListItemAccountBinding
import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem.AccountItem

internal class AccountViewHolder(
    private val binding: ListItemAccountBinding,
    private val adapterCallback: AccountListAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AccountItem) = with(binding) {
        this.item = item
        this.callback = adapterCallback
        accountIconImageView.setStrokeWidthResource(item.imageStrokeWidth)
    }

    companion object {
        fun create(parent: ViewGroup, callback: AccountListAdapterCallback): AccountViewHolder {
            val binding = ListItemAccountBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return AccountViewHolder(binding, callback)
        }
    }
}
