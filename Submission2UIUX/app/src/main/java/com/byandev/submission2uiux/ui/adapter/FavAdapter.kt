package com.byandev.submission2uiux.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.model.FavoriteItem
import kotlinx.android.synthetic.main.item_list_favorite.view.*
import java.util.*

class FavAdapter : RecyclerView.Adapter<FavAdapter.Holder>() {

    var listFavoriteUser = ArrayList<FavoriteItem>()
        set(listFavoriteUser) {
            if (listFavoriteUser.size > 0) {
                this.listFavoriteUser.clear()
            }
            this.listFavoriteUser.addAll(listFavoriteUser)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = 
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_users, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listFavoriteUser[position])
    }

    override fun getItemCount(): Int = this.listFavoriteUser.size

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteUserItems: FavoriteItem) {
            with(itemView){
                tvUserName.text = favoriteUserItems.login
                Glide.with(context)
                    .load(favoriteUserItems.avatar_url)
                    .apply(
                        RequestOptions()
                        .override(56,56)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                    .into(imgUsers)
                setOnClickListener {
                    onItemClickListener?.let { it(favoriteUserItems) }
                }
            }
        }
    }

    private var onItemClickListener: ((FavoriteItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (FavoriteItem) -> Unit) {
        onItemClickListener = listener
    }

}