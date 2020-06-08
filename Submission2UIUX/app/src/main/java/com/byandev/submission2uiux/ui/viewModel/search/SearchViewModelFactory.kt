package com.byandev.submission2uiux.ui.viewModel.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.data.repo.SearchListRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    val app: Application,
    private val searchRepository: SearchListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(
            app,
            searchRepository
        ) as T
    }
}