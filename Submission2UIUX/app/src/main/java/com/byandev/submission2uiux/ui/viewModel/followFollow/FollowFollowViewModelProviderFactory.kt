package com.byandev.submission2uiux.ui.viewModel.followFollow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.data.repo.FollowFollowListRepository

@Suppress("UNCHECKED_CAST")
class FollowFollowViewModelProviderFactory(
        val app: Application,
        val followFollowListRepository: FollowFollowListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowFollowViewModel(
            app,
            followFollowListRepository
        ) as T
    }


}