package com.byandev.submission3uiuxapi.ui.viewModel.followFollow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.byandev.submission3uiuxapi.db.repository.AppRepository
import com.byandev.submission3uiuxapi.models.FollowersSource
import com.byandev.submission3uiuxapi.utils.Constants.Companion.PAGE_SIZE
import com.byandev.submission3uiuxapi.utils.InternetConnection
import com.byandev.submission3uiuxapi.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

@Suppress("DEPRECATION")
class FollowersViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    var pagination = PAGE_SIZE

    val userFollowers: MutableLiveData<Resource<FollowersSource>> = MutableLiveData()
    private var userFollowersResponse: FollowersSource? = null


    fun followersFetch(userName: String) = viewModelScope.launch {
        safeFollowersCall(userName)
    }

    private fun handleFollowersResponse(response: Response<FollowersSource>) : Resource<FollowersSource>? {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                pagination++
                if (userFollowersResponse == null) {
                    userFollowersResponse = resultResponse
                }else {

                    for (i in 0 until resultResponse.size) {
                        val foll = userFollowersResponse
                        val follow = resultResponse[i]
                        foll?.contains(follow)
                    }
                    userFollowers.postValue(Resource.Loading())
                }
                return Resource.Success(userFollowersResponse ?: resultResponse)
            }
        }

        return Resource.Error(response.message())
    }



    private suspend fun safeFollowersCall(userName: String) {
        userFollowers.postValue(Resource.Loading())
        try {
            val internetConnection = InternetConnection(getApplication())
            if (internetConnection.hasInternetConnection()) {
                val response =
                    appRepository.getFollowers(userName, pagination)
                userFollowers.postValue(handleFollowersResponse(response))
            } else {
                userFollowers.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> userFollowers.postValue(Resource.Error("Network Failure"))
                else -> userFollowers.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


}