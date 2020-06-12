package com.byandev.submission3uiuxapi.db.repository

import com.byandev.submission3uiuxapi.api.RetrofitClient

class AppRepository {
    suspend fun fetchDetailUsers(userName: String) =
        RetrofitClient.api.detailUser(userName)


    suspend fun getFollowers(userName: String, pageNumber: Int) =
        RetrofitClient.api.followers(userName, pageNumber)


    suspend fun getFollowing(userName: String, pageNumber: Int) =
        RetrofitClient.api.following(userName, pageNumber)

    suspend fun searchUser(query: String, pageNumber: Int) =
        RetrofitClient.api.searchUsers(query, pageNumber)
}