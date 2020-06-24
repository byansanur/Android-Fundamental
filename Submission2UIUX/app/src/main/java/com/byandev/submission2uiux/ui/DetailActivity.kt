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
import com.byandev.submission2uiux.data.SharedPref
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
import kotlinx.android.synthetic.main.bs_detail_user_layout.view.*
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    val args: DetailActivityArgs by navArgs()

    private lateinit var viewModel: DetailUserViewModel
    lateinit var viewModelFollowers: FollowersViewModel
    lateinit var viewModelFollowing: FollowingViewModel

    private lateinit var sharedPref: SharedPref
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private var isFavorite:Boolean = false
    private var menuItem: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPref = SharedPref(this)
        if (sharedPref.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)




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

        val item = args.search

        viewModel.detailUserFetch(item.login!!)
        supportActionBar?.title = item.login
        htab_header.apply {
            Glide.with(this)
                .load(item.avatar_url)
                .into(htab_header)
        }

        if (!item.isFavorite) {
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_baseline_favorite_24)
        } else menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_baseline_favorite_border_24)


        viewPagerState()

    }



    private fun viewPagerState() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        htab_viewpager.adapter = viewPagerAdapter
        htab_tabs.setupWithViewPager(htab_viewpager)
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

    private fun addFavUser() {
        val itemFav = args.search
        viewModel.saveUser(itemFav)
        toastMessage("add db")
    }

    private fun removeFavUser() {
        val itemFav = args.search
        viewModel.deleteUser(itemFav)
        toastMessage("delete db")
    }

    private fun toastMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun setUpFav() {
        if (isFavorite){
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        menuItem = menu
        setUpFav()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.info_badge -> bottomSheetDialog.show()
            R.id.favorite_badge -> {
                if (isFavorite) removeFavUser() else addFavUser()
                isFavorite = !isFavorite
                setUpFav()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}