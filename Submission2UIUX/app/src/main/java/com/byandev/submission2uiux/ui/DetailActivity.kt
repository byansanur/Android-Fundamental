package com.byandev.submission2uiux.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import com.byandev.submission2uiux.data.dao.UserDatabase
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModel
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModelFactory
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowersViewModel
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowersViewModelFactory
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowingViewModel
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowingViewModelFactory
import com.byandev.submission2uiux.utils.Resource
import com.byandev.submission2uiux.utils.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bs_detail_user_layout.view.*
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    val args: DetailActivityArgs by navArgs()

    private lateinit var viewModel: DetailUserViewModel
    lateinit var viewModelFollowers: FollowersViewModel
    lateinit var viewModelFollowing: FollowingViewModel

    private lateinit var saveDataTheme: SaveDataTheme
    private lateinit var bottomSheetDialog: BottomSheetDialog


    override fun onCreate(savedInstanceState: Bundle?) {

        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        val userName = args.search

        val detailRepository = SearchListRepository(UserDatabase(this))
        val detailUserViewModelProviderFactory =
            DetailUserViewModelFactory(
                application,
                detailRepository
            )
        viewModel = ViewModelProvider(this, detailUserViewModelProviderFactory)
            .get(DetailUserViewModel::class.java)

        bottomSheetDialog = BottomSheetDialog(this)


        // for follow
        val followRepository = SearchListRepository(UserDatabase(this))
        val followersViewModelFactory =
            FollowersViewModelFactory(
                application,
                followRepository
            )
        val followingViewModelFactory =
            FollowingViewModelFactory(
                application,
                followRepository
            )
        viewModelFollowers = ViewModelProvider(this, followersViewModelFactory)
            .get(FollowersViewModel::class.java)
        viewModelFollowing = ViewModelProvider(this,followingViewModelFactory)
            .get(FollowingViewModel::class.java)

        setSupportActionBar(htab_toolbar)
        htab_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        htab_toolbar.setNavigationOnClickListener { onBackPressed() }

        dataInfoUser()


        viewModel.detailUserFetch(userName.login!!)
        supportActionBar?.title = userName.login
        htab_header.apply {
            Glide.with(this)
                .load(userName.avatar_url)
                .centerInside()
                .into(htab_header)
        }

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        htab_viewpager.adapter = viewPagerAdapter
        htab_tabs.setupWithViewPager(htab_viewpager)

        imgFav.setOnClickListener {
            viewModel.saveUser(userName)
            Snackbar.make(it, "User is save in local db", Snackbar.LENGTH_LONG).show()
        }

    }


    @SuppressLint("InflateParams")
    private fun dataInfoUser() {
        val viewBS = layoutInflater.inflate(R.layout.bs_detail_user_layout, null)
        bottomSheetDialog.setContentView(viewBS)
        viewModel.detailUsers.observe(this, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        viewBS.tvUserName.text = it.login
                        Glide.with(this)
                            .load(it.avatar_url)
                            .circleCrop()
                            .into(viewBS.imgBsUser)
                        viewBS.tvLocation.text = it.location
                        viewBS.tvCountFollowers.text = it.followers.toString()
                        viewBS.tvCountFollowing.text = it.following.toString()
                        viewBS.tvCountRepository.text = it.public_repos.toString()
                        val url = it.html_url
                        viewBS.btnCheckUsers.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        }
                        viewBS.closeBtn.setOnClickListener { bottomSheetDialog.dismiss() }
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(this, "An error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.info_badge) {
            bottomSheetDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }


}