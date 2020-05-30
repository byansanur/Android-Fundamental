package com.byandev.submission2uiux.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

//    lateinit var viewModel: SearchFragmentViewModel
    val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

//        viewModel = (callingActivity as MainActivity).viewModel

        val idUser = args.search

        htab_header.apply {
            Glide.with(this)
                .load(idUser.avatar_url)
                .into(htab_header)
        }

        htab_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        htab_toolbar.setNavigationOnClickListener { onBackPressed() }
        htab_toolbar.title = idUser.login

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        htab_viewpager.adapter = viewPagerAdapter

        htab_tabs.setupWithViewPager(htab_viewpager)

        supportActionBar?.elevation = 0f
    }
}