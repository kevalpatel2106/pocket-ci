package com.kevalpatel2106.accounts.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem.AccountItem
import com.kevalpatel2106.accounts.list.adapter.AccountsListItem.HeaderItem
import com.kevalpatel2106.accounts.list.adapter.AccountsListItemType.ACCOUNT_ITEM
import com.kevalpatel2106.accounts.list.adapter.AccountsListItemType.HEADER_ITEM

internal class AccountsAdapter(
    private val callback: AccountsAdapterCallback,
) : PagingDataAdapter<AccountsListItem, RecyclerView.ViewHolder>(AccountsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ACCOUNT_ITEM.ordinal -> AccountViewHolder.create(parent, callback)
        HEADER_ITEM.ordinal -> AccountHeaderViewHolder.create(parent)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal
            ?: error("Invalid position: $position. Current list size is $itemCount.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is AccountViewHolder -> holder.bind(it as AccountItem)
                is AccountHeaderViewHolder -> holder.bind(it as HeaderItem)
            }
        }
    }
}
