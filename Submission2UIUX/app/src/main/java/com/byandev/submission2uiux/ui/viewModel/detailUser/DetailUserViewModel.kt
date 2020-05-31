package com.byandev.submission2uiux.ui.viewModel.detailUser

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission2uiux.SearchApplication
import com.byandev.submission2uiux.data.model.DetailUser
import com.byandev.submission2uiux.data.repo.DetailUserRepository
import com.byandev.submission2uiux.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DetailUserViewModel(
    app: Application,
    val detailUserRepository: DetailUserRepository
) : AndroidViewModel(app) {

    val detailUsers: MutableLiveData<Resource<DetailUser>> = MutableLiveData()
    var detailUsersResponse: DetailUser? = null

    fun detailUserFetch(userName: String) = viewModelScope.launch {
        safeDetailCall(userName)
    }

    private fun handleDetailUserResponse(response: Response<DetailUser>) : Resource<DetailUser> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(detailUsersResponse == null) {
                    detailUsersResponse = resultResponse
                } else {
                    val oldUsers = detailUsersResponse
                    oldUsers?.login
                }
                return Resource.Success(detailUsersResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeDetailCall(userName: String) {
        detailUsers.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = detailUserRepository.fetchDetailUsers(userName)
                detailUsers.postValue(handleDetailUserResponse(response))
            } else {
                detailUsers.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> detailUsers.postValue(Resource.Error("Network Failure"))
                else -> detailUsers.postValue(Resource.Error("Conversion Error"))
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