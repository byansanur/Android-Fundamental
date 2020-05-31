package com.byandev.submission2uiux.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.repo.DetailUserRepository
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModel
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUsersViewModelFactory
import com.byandev.submission2uiux.utils.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bs_detail_user_layout.view.*
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    val args: DetailActivityArgs by navArgs()

    private lateinit var viewModel: DetailUserViewModel

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
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

        listener()
        viewModel.detailUserFetch(userName.login.toString())
        htab_toolbar.title = userName.login
        htab_header.apply {
            Glide.with(this)
                .load(userName.avatar_url)
                .into(htab_header)
        }

        bottomSheetDialog = BottomSheetDialog(this)
        val viewBS = layoutInflater.inflate(R.layout.bs_detail_user_layout, null)
        bottomSheetDialog.setContentView(viewBS)
        viewBS.tvUserName.text = userName.login
        val imgbs = viewBS.imgBsUser
        val imgUrl = userName.avatar_url
        Glide.with(this)
            .load(imgUrl)
            .into(imgbs)


        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        htab_viewpager.adapter = viewPagerAdapter

        htab_tabs.setupWithViewPager(htab_viewpager)

    }

//    private fun binding(user: DetailUser?) {
//
//    }



    private fun listener() {
        htab_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        htab_toolbar.setNavigationOnClickListener { onBackPressed() }
        setSupportActionBar(htab_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.info_badge) {
//            Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

}