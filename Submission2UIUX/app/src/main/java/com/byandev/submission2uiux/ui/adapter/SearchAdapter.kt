package com.byandev.submission2uiux.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.model.Item
import kotlinx.android.synthetic.main.item_list_users.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item> () {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_list_users,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val search = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(search.avatar_url)
                .centerCrop()
                .into(imgUsers)
            tvUserName.text = search?.login
            setOnClickListener {
                onItemClickListener?.let { it(search) }
            }
        }
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

}