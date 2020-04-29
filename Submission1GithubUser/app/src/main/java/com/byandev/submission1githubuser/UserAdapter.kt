package com.byandev.submission1githubuser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context: Context) : BaseAdapter() {

    internal var users = arrayListOf<DataSource>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        }
        val viewHolder = Holder(itemView as View)
        val user = getItem(position) as DataSource
        viewHolder.bind(user)
        itemView.setOnClickListener { v ->
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("avatar", user.avatar)
            intent.putExtra("company", user.company)
            intent.putExtra("follower", user.follower)
            intent.putExtra("following", user.following)
            intent.putExtra("location", user.location)
            intent.putExtra("name", user.name)
            intent.putExtra("repository", user.repository)
            intent.putExtra("username", user.username)
            context.startActivity(intent)
        }

        return itemView
    }

    private inner class Holder internal constructor(view: View) {
        private val tvUserName: TextView = view.findViewById(R.id.tvUserName)
        private val tvNamaLengkap: TextView = view.findViewById(R.id.tvNamaLengkap)
        private val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        private val imgUsers: CircleImageView = view.findViewById(R.id.imgUsers)

        internal fun bind(user: DataSource) {
            tvUserName.text = user.username
            tvNamaLengkap.text = user.name
            tvLocation.text = user.location
            imgUsers.setImageResource(user.avatar.toInt())
        }

    }

    override fun getItem(position: Int): Any = users[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = users.size

}