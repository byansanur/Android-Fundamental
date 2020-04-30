package com.byandev.submission1githubuser

import android.annotation.SuppressLint
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

    override fun getView(i: Int, viewItem: View?, group: ViewGroup?): View {
        var view = viewItem
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_user, group, false)
        }
        val holder = Holder(view as View)
        val user = getItem(i) as DataSource
        holder.bind(user)
        view.setOnClickListener { v ->
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

        return view
    }

    private inner class Holder internal constructor(view: View) {
        private val tvUserName: TextView = view.findViewById(R.id.tvUserName)
        private val tvFollower: TextView = view.findViewById(R.id.tvFollower)
        private val tvFollowing: TextView = view.findViewById(R.id.tvFollowing)
        private val tvRepository: TextView = view.findViewById(R.id.tvRepository)
        private val imgUsers: CircleImageView = view.findViewById(R.id.imgUsers)

        @SuppressLint("SetTextI18n")
        internal fun bind(user: DataSource) {
            tvUserName.text = user.username
            tvFollower.text = "Follower\n" + user.follower
            tvFollowing.text = "Following\n" + user.following
            imgUsers.setImageResource(user.avatar.toInt())
            tvRepository.text = "Repository\n" + user.repository
        }

    }

    override fun getItem(i: Int): Any = users[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getCount(): Int = users.size

}