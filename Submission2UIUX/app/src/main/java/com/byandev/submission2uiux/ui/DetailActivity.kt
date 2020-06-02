package com.byandev.submission2uiux.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import com.byandev.submission2uiux.data.repo.DetailUserRepository
import com.byandev.submission2uiux.data.repo.FollowFollowListRepository
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModel
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUsersViewModelFactory
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowFollowViewModel
import com.byandev.submission2uiux.ui.viewModel.followFollow.FollowFollowViewModelProviderFactory
import com.byandev.submission2uiux.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    val args: DetailActivityArgs by navArgs()

    private lateinit var viewModel: DetailUserViewModel
    lateinit var viewModelFollow: FollowFollowViewModel

    private lateinit var saveDataTheme: SaveDataTheme


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

        val detailRepository = DetailUserRepository()
        val detailUserViewModelProviderFactory =
            DetailUsersViewModelFactory(
                application,
                detailRepository
            )
        viewModel = ViewModelProvider(this, detailUserViewModelProviderFactory)
            .get(DetailUserViewModel::class.java)

        val followRepository = FollowFollowListRepository()
        val followViewModelFactory =
            FollowFollowViewModelProviderFactory(
                application,
                followRepository
            )
        viewModelFollow = ViewModelProvider(this, followViewModelFactory)
            .get(FollowFollowViewModel::class.java)

        htab_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        htab_toolbar.setNavigationOnClickListener { onBackPressed() }

        viewModel.detailUserFetch(userName.login.toString())

        htab_toolbar.title = userName.login
        htab_header.apply {
            Glide.with(this)
                .load(userName.avatar_url)
                .into(htab_header)
        }

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        htab_viewpager.adapter = viewPagerAdapter

        htab_tabs.setupWithViewPager(htab_viewpager)

    }


}