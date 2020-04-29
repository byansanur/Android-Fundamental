package com.byandev.submission1githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AVATAR = "avatar"
        const val EXTRA_COMPANY = "company"
        const val EXTRA_FOLLOWER = "follower"
        const val EXTRA_FOLLOWING = "following"
        const val EXTRA_LOCATION = "location"
        const val EXTRA_USER = "name"
        const val EXTRA_REPOSITORY = "repository"
        const val EXTRA_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val tvNameDetail: TextView = findViewById(R.id.tvNameDetail)

        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val company = intent.getStringExtra(EXTRA_COMPANY)
        val follower = intent.getStringExtra(EXTRA_FOLLOWER)
        val following = intent.getStringExtra(EXTRA_FOLLOWING)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val name = intent.getStringExtra(EXTRA_USER)
        val repo = intent.getStringExtra(EXTRA_REPOSITORY)
        val uname = intent.getStringExtra(EXTRA_USERNAME)
        val text = name
        tvNameDetail.text = text
    }
}
