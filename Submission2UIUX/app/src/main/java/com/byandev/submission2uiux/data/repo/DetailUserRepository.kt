package com.byandev.submission2uiux.data.repo

import com.byandev.submission2uiux.network.RetrofitInstance

class DetailUserRepository {

    suspend fun fetchDetailUsers(userName: String) =
        RetrofitInstance.api.detailUser(userName)

}