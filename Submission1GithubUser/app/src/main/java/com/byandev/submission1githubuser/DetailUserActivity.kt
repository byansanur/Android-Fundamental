package com.byandev.submission1githubuser

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.toolbar.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
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


    private lateinit var tvUserNameDetail: TextView
    private lateinit var tvCompanyUser: TextView
    private lateinit var tvLocationUser: TextView
    private lateinit var tvCountFollower: TextView
    private lateinit var tvCountFollowing: TextView
    private lateinit var tvCountRepository: TextView
    private lateinit var imgUser: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        setupUI()
        setupIntent()
    }

    private fun setupUI() {
        tvUserNameDetail = findViewById(R.id.tvUserName)
        imgUser = findViewById(R.id.imgUser)
        tvCompanyUser = findViewById(R.id.tvCompany)
        tvLocationUser = findViewById(R.id.tvLocation)
        tvCountFollower = findViewById(R.id.tvCountFollower)
        tvCountFollowing = findViewById(R.id.tvCountFollowing)
        tvCountRepository = findViewById(R.id.tvCountRepository)
    }

    private fun setupIntent() {
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val company = intent.getStringExtra(EXTRA_COMPANY)
        val follower = intent.getIntExtra(EXTRA_FOLLOWER, 0)
        val following = intent.getIntExtra(EXTRA_FOLLOWING, 0)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val name = intent.getStringExtra(EXTRA_USER)
        val repo = intent.getIntExtra(EXTRA_REPOSITORY, 0)
        val uname = intent.getStringExtra(EXTRA_USERNAME)

        toolbar.title = name

        tvUserNameDetail.text = uname
        tvCompanyUser.text = company
        tvLocationUser.text = location
        tvCountFollower.text = follower.toString()
        tvCountRepository.text = repo.toString()
        tvCountFollowing.text = following.toString()

        Glide.with(this)
            .load(avatar.toInt())
            .centerInside()
            .circleCrop()
            .into(imgUser)
    }
}
