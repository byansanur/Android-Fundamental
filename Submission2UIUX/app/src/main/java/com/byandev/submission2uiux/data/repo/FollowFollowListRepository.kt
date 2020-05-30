package com.byandev.submission2uiux.data.repo

import com.byandev.submission2uiux.network.RetrofitInstance

class FollowFollowListRepository {

    suspend fun userFollowers(userName: String, pageNumber: Int) =
        RetrofitInstance.api.followers(userName, pageNumber)

    suspend fun userFollowing(userName: String, pageNumber: Int) =
        RetrofitInstance.api.following(userName, pageNumber)
}