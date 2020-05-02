package com.byandev.fundametalandroid8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row_hero.view.*

class ListAdapterHero(private val listHero: ArrayList<DataHero>) : RecyclerView.Adapter<ListAdapterHero.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hero: DataHero) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(hero.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(img_item_photo)
                tv_item_name.text = hero.name
                tv_item_description.text = hero.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterHero.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = listHero.size

    override fun onBindViewHolder(holder: ListAdapterHero.Holder, position: Int) {
        holder.bind(listHero[position])
    }

}