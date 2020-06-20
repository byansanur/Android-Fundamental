package com.byandev.submission3uiuxapi.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byandev.submission3uiuxapi.db.repository.AppRepository
import com.byandev.submission3uiuxapi.models.Item
import kotlinx.coroutines.launch

class FavViewModel (
    app: Application,
    val appRepository: AppRepository
) : AndroidViewModel(app) {


    fun saveFavorite(item: Item) = viewModelScope.launch {
        appRepository.insertFav(item)
    }

    fun getSaveFavorite() = appRepository.getSavedFav()


    fun deleteFavorite(item: Item) = viewModelScope.launch {
        appRepository.deleteFav(item)
    }

}