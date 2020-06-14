package com.byandev.submission3uiuxapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.db.dao.UserFavDao
import com.byandev.submission3uiuxapi.db.repository.AppRepository
import com.byandev.submission3uiuxapi.ui.viewModel.home.HomeViewModel
import com.byandev.submission3uiuxapi.ui.viewModel.home.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val appRepository = AppRepository(UserFavDao(this))
        val homeViewModelFactory = HomeViewModelFactory(application, appRepository)
        viewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)


        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }
}