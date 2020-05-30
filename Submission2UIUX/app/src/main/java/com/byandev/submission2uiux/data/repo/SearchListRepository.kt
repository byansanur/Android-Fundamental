package com.byandev.submission2uiux.data.repo

import com.byandev.submission2uiux.network.RetrofitInstance

class SearchListRepository {

    suspend fun searchUser(query: String, pageNumber: Int) =
        RetrofitInstance.api.searchUsers(query, pageNumber)


}