package com.byandev.fundametalandroid24

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_items.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.Holder>() {

    private val mData = ArrayList<WeatherItems>()

    fun setData(items: ArrayList<WeatherItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.Holder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.weather_items, parent, false)
        return Holder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: WeatherAdapter.Holder, position: Int) {
        holder.bind(mData[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherItems: WeatherItems) {
            with(itemView) {
                textCity.text = weatherItems.name
                textTemp.text = weatherItems.temperature
                textDesc.text = weatherItems.description
            }
        }
    }
}