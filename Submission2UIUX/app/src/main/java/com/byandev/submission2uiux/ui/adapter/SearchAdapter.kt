package com.byandev.submission2uiux.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.model.Item
import kotlinx.android.synthetic.main.item_list_users.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.Holder>() {

    private val mData = ArrayList<Item>()

    fun setData(items: ArrayList<Item>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(searchItem: Item) {
            with(itemView) {
                tvUserName.text = searchItem.login
//                val imgUserUrl = searchItem.avatar_url
//                Glide.with(this)
//                    .load(imgUserUrl)
//                    .centerCrop()
//                    .circleCrop()
//                    .into(imgUsers)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_users, parent, false)
        return Holder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mData[position])
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }


}