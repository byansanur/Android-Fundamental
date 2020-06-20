package com.byandev.submission3uiuxapi.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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

        setSupportActionBar(toolbar)
        supportFragmentManager.findFragmentById(R.id.searchNavHostFragment)?.findNavController()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_fav -> intentFragmentFav()
            R.id.menu_setting -> intentFragmentSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun intentFragmentSettings() {
        Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
    }

    private fun intentFragmentFav() {
        Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
    }
}