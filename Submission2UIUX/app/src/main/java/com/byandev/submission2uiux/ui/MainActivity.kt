package com.byandev.submission2uiux.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.ui.viewModel.SearchFragmentViewModel
import com.byandev.submission2uiux.ui.viewModel.SearchViewModelFactory

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: SearchFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelProviderFactory = SearchViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchFragmentViewModel::class.java)
    }



}
