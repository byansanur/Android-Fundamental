package com.byandev.submission2uiux.ui.viewModel.followFollow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.data.repo.SearchListRepository

@Suppress("UNCHECKED_CAST")
class FollowingViewModelFactory(
    val app: Application,
    private val followFollowListRepository: SearchListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowingViewModel(
            app,
            followFollowListRepository
        ) as T
    }


}