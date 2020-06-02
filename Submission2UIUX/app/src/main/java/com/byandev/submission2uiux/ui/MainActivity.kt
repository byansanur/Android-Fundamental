package com.byandev.submission2uiux.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SaveDataTheme
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.ui.viewModel.search.SearchFragmentViewModel
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: SearchFragmentViewModel
    private lateinit var saveDataTheme: SaveDataTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        saveDataTheme = SaveDataTheme(this)
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }

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
    }

    override fun onStart() {
        super.onStart()
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    override fun onResume() {
        super.onResume()
        if (saveDataTheme.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

}
