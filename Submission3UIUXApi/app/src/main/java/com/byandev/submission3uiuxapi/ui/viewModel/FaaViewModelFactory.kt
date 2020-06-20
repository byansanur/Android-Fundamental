package com.byandev.submission3uiuxapi.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission3uiuxapi.db.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class FaaViewModelFactory (
    val app: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavViewModel(app, appRepository ) as T
    }

}