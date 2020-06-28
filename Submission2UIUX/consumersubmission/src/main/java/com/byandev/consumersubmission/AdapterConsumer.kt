package com.byandev.consumersubmission

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_users_consumer.view.*


class AdapterConsumer (private val context: Context) : RecyclerView.Adapter<AdapterConsumer.Holder>() {

    private var cursor: Cursor? = null

    fun setData(cursor: Cursor?) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(
                R.layout.item_list_users_consumer,
                parent,
                false
            )
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return if (cursor == null) 0 else cursor!!.count
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.apply {
            if (cursor?.moveToPosition(position)!!) {

                tvUserName.text = cursor?.getColumnIndexOrThrow(
                    "login"
                )?.let {
                    cursor?.getString(it)
                }

                val builder = Picasso.Builder(context)
                builder.build()
                    .load(
                        cursor?.getColumnIndexOrThrow(
                            "avatar_url"
                        )?.let {
                            cursor?.getString(it)
                        })
                    .fit()
                    .into(imgUsers)
            }
        }
    }

}


