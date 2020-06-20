package com.byandev.submission2uiux.ui.viewModel.detailUser

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.data.repo.SearchListRepository

@Suppress("UNCHECKED_CAST")
class DetailUserViewModelFactory(
    val app: Application,
    private val detailUserRepository: SearchListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailUserViewModel(
            app,
            detailUserRepository
        ) as T
    }
}