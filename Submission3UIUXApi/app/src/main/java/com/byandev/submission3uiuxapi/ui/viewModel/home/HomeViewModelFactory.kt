package com.byandev.submission3uiuxapi.ui.viewModel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission3uiuxapi.db.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory (
    val app: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(
            app,
            appRepository
        ) as T
    }
}