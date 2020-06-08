package com.byandev.submission2uiux.ui.viewModel.search

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission2uiux.SearchApplication
import com.byandev.submission2uiux.data.model.SearchModel
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.byandev.submission2uiux.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


@Suppress("DEPRECATION")
class SearchViewModel (
    app: Application,
    private val searchUsersRepository: SearchListRepository
) : AndroidViewModel(app) {

    internal val searchUsers: MutableLiveData<Resource<SearchModel>> = MutableLiveData()
    internal var searchUsersPage = QUERY_PAGE_SIZE
    private var searchUsersResponse: SearchModel? = null

    fun searchFetch(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }

    private fun handlerUsersResponse(response: Response<SearchModel>) : Resource<SearchModel> {
        if(response.isSuccessful) {
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

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        searchUsers.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = searchUsersRepository.searchUser(searchQuery, searchUsersPage)
                searchUsers.postValue(handlerUsersResponse(response))
            } else {
                searchUsers.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchUsers.postValue(Resource.Error("Network Failure"))
                else -> searchUsers.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<SearchApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}

