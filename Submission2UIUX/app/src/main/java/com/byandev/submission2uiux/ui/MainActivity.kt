package com.byandev.submission2uiux.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.ui.viewModel.search.SearchFragmentViewModel
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: SearchFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchListRepository = SearchListRepository()
        val searchViewModelProviderFactory =
            SearchViewModelProviderFactory(
                application,
                searchListRepository
            )
        viewModel = ViewModelProvider(this, searchViewModelProviderFactory).get(SearchFragmentViewModel::class.java)

        searchNavHostFragment.findNavController()
//        val mFragmentManager = supportFragmentManager
//        val mHomeFragment = FragmentSearch()
//        val fragment = mFragmentManager.findFragmentByTag(FragmentSearch::class.java.simpleName)
//
//        if (fragment !is FragmentSearch) {
//            mFragmentManager
//                .beginTransaction()
//                .add(R.id.flFragment, mHomeFragment, FragmentSearch::class.java.simpleName)
//        }
    }

}
