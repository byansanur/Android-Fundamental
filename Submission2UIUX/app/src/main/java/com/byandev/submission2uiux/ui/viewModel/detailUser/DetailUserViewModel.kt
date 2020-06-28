package com.byandev.submission2uiux.ui.viewModel.detailUser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission2uiux.data.model.DetailUser
import com.byandev.submission2uiux.data.model.Item
import com.byandev.submission2uiux.data.repo.SearchListRepository
import com.byandev.submission2uiux.utils.InternetConnection
import com.byandev.submission2uiux.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DetailUserViewModel(
    app: Application,
    private val searchUsersRepository: SearchListRepository
) : AndroidViewModel(app) {

    val detailUsers: MutableLiveData<Resource<DetailUser>> = MutableLiveData()
    private var detailUsersResponse: DetailUser? = null

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
            val internetConnection = InternetConnection(getApplication())
            if (internetConnection.hasInternetConnection()) {
                val response = searchUsersRepository.fetchDetailUsers(userName)
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

    fun saveUser(favoriteItem: Item) = viewModelScope.launch {
        searchUsersRepository.createUserFav(favoriteItem)
    }

    fun deleteUser(favoriteItem: Item) = viewModelScope.launch {
        searchUsersRepository.deleteUserFav(favoriteItem)
    }


}