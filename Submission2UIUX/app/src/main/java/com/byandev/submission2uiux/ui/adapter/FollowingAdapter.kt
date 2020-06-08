package com.byandev.submission2uiux.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.model.FollowingSourceItem
import kotlinx.android.synthetic.main.item_list_users.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<FollowingSourceItem> () {
        override fun areItemsTheSame(
            oldItem: FollowingSourceItem,
            newItem: FollowingSourceItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FollowingSourceItem,
            newItem: FollowingSourceItem
        ): Boolean {
            return oldItem == newItem
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
        val followersSource = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(followersSource.avatar_url)
                .centerCrop()
                .into(imgUsers)
            tvUserName.text = followersSource?.login
            setOnClickListener {
                Toast.makeText(context, "followers ${followersSource.login}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}