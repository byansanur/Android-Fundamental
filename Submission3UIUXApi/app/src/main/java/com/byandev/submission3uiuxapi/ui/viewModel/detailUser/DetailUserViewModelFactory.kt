package com.byandev.submission2uiux.ui.viewModel.detailUser

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission3uiuxapi.db.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class DetailUserViewModelFactory(
    val app: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailUserViewModel(
            app,
            appRepository
        ) as T
    }
}