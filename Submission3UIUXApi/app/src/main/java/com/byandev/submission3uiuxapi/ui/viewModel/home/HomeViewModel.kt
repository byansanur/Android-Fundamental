package com.byandev.submission3uiuxapi.ui.viewModel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission3uiuxapi.db.repository.AppRepository
import com.byandev.submission3uiuxapi.models.Item
import com.byandev.submission3uiuxapi.models.SearchModel
import com.byandev.submission3uiuxapi.utils.Constants.Companion.PAGE_SIZE
import com.byandev.submission3uiuxapi.utils.InternetConnection
import com.byandev.submission3uiuxapi.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    app: Application,
    val appRepository: AppRepository
) : AndroidViewModel(app) {

    internal val searchUsers: MutableLiveData<Resource<SearchModel>> = MutableLiveData()
    internal var searchUsersPage = PAGE_SIZE
    private var searchUsersResponse: SearchModel? = null

    fun fetchSearch(q: String) = viewModelScope.launch {
        safeSearch(q)
    }



    private fun handlerSearchResponse(response: Response<SearchModel>): Resource<SearchModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchUsersPage++
                if(searchUsersResponse == null) {
                    searchUsersResponse = resultResponse
                } else {
                    for (element in resultResponse.items) {
                        val oldUsers = searchUsersResponse?.items
                        oldUsers?.contains(element)
                    }
                    searchUsers.postValue(Resource.Loading())
                }
                return Resource.Success(searchUsersResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearch(q: String) {
        searchUsers.postValue(Resource.Loading())
        try {
            val internetConnection = InternetConnection(getApplication())
            if (internetConnection.hasInternetConnection()) {
                val response = appRepository.searchUser(q, searchUsersPage)
                searchUsers.postValue(handlerSearchResponse(response))
            } else {
                searchUsers.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> searchUsers.postValue(Resource.Error("Network Failure"))
                else -> searchUsers.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
}