package com.byandev.submission1githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.toolbar.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "user"
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
        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
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
        val user = intent.getParcelableExtra(EXTRA_USER) as DataSource

        toolbar.title = user.name

        val avatars = user.avatar!!.toInt()

        tvUserNameDetail.text = user.username
        tvCompanyUser.text = user.company
        tvLocationUser.text = user.location
        tvCountFollower.text = user.follower.toString()
        tvCountRepository.text = user.repository.toString()
        tvCountFollowing.text = user.following.toString()

        Glide.with(this)
            .load(avatars)
            .centerCrop()
            .circleCrop()
            .into(imgUser)
    }
}
