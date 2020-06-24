package com.byandev.submission2uiux.ui

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.SharedPref
import com.byandev.submission2uiux.data.dao.UserDatabase
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModel
import com.byandev.submission2uiux.ui.viewModel.search.SearchViewModelFactory

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: SearchViewModel
    private lateinit var sharedPref: SharedPref
    private var doubleClickBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = SharedPref(this)
        if (sharedPref.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchListRepository = SearchListRepository(UserDatabase(this))
        val searchViewModelProviderFactory =
            SearchViewModelFactory(
                application,
                searchListRepository
            )
        viewModel = ViewModelProvider(this, searchViewModelProviderFactory).get(SearchViewModel::class.java)

        supportFragmentManager.findFragmentById(R.id.searchNavHostFragment)?.findNavController()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.loadModeState() == true) {
            setTheme(R.style.DarkThem)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    override fun onBackPressed() {
        if (doubleClickBackToExit) {
            super.onBackPressed()
            return
        }
        this.doubleClickBackToExit = true
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_LONG).show()
        Handler().postDelayed({ doubleClickBackToExit = false }, 2000)
    }


}
