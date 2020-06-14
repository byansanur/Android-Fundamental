package com.byandev.submission3uiuxapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModel
import com.byandev.submission2uiux.ui.viewModel.detailUser.DetailUserViewModelFactory
import com.byandev.submission3uiuxapi.R
import com.byandev.submission3uiuxapi.db.dao.UserFavDao
import com.byandev.submission3uiuxapi.db.repository.AppRepository

class DetailActivity : AppCompatActivity() {


    lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailRepository = AppRepository(UserFavDao(this))
        val detailUserViewModelProviderFactory =
            DetailUserViewModelFactory(
                application,
                detailRepository
            )
        viewModel = ViewModelProvider(this, detailUserViewModelProviderFactory)
            .get(DetailUserViewModel::class.java)



    }

}