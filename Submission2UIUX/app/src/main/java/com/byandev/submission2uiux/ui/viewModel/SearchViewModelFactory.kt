package com.byandev.submission2uiux.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byandev.submission2uiux.SearchApplication
import com.byandev.submission2uiux.data.repo.SearchRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    val app: SearchApplication,
    val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  SearchFragmentViewModel(app, repository) as T
    }

}