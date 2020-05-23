package com.byandev.submission2uiux.ui.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission2uiux.SearchApplication
import com.byandev.submission2uiux.data.model.SearchModel
import com.byandev.submission2uiux.data.repo.SearchRepository
import com.byandev.submission2uiux.utils.Responses
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class SearchFragmentViewModel (
    app: SearchApplication,
    val searchRepository: SearchRepository
) : AndroidViewModel(app) {

    val searchItem : MutableLiveData<Responses<SearchModel>> = MutableLiveData()
    var searchModelResponse: SearchModel? = null
    var searchNewsPage = 1

    fun searchUser(query: String) = viewModelScope.launch {
        safeSearchUserCall(query)
    }

    private fun handlerSearchUsersResponds(response: Response<SearchModel>): Responses<SearchModel>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchModelResponse == null) {
                    searchModelResponse = resultResponse
                } else {
                    val oldUsers = searchModelResponse?.items
                    val newUsers = resultResponse.items
                    oldUsers?.addAll(newUsers)
                }
                return Responses.Success(searchModelResponse ?: resultResponse)
            }
        }
        return Responses.Error(response.message())
    }

    private suspend fun safeSearchUserCall(query: String) {
        searchItem.postValue(Responses.Loading())
        try {
            if (hasInternetConnection() == true) {
                val response = searchRepository.searchUser(query)
                searchItem.postValue(handlerSearchUsersResponds(response))
            } else {
                searchItem.postValue(Responses.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> searchItem.postValue(Responses.Error("Network Failure"))
                else -> searchItem.postValue(Responses.Error("Fatal Error"))
            }

        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<SearchApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}