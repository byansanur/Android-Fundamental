package com.byandev.submission3uiuxapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.models.FollowersSourceItem
import com.byandev.submission3uiuxapi.models.Item
import kotlinx.android.synthetic.main.item_list_search.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<FollowersSourceItem> () {
        override fun areItemsTheSame(
            oldItem: FollowersSourceItem,
            newItem: FollowersSourceItem
        ): Boolean {
            return oldItem.id  == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FollowersSourceItem,
            newItem: FollowersSourceItem
        ): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_list_search,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val followersSource = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(followersSource.avatar_url)
                .centerCrop()
                .into(imgUser)
            tvUsername.text = followersSource?.login
            tvTypeUser.text = followersSource?.type
            setOnClickListener {
                Toast.makeText(context, "followers ${followersSource.login}", Toast.LENGTH_SHORT).show()
                onItemClickListener?.let { it(followersSource) }
            }
        }
    }

    private var onItemClickListener: ((FollowersSourceItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (FollowersSourceItem) -> Unit) {
        onItemClickListener = listener
    }

}