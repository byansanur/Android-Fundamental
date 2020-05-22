package com.byandev.submission2uiux.network

import com.byandev.submission2uiux.data.model.DetailUser
import com.byandev.submission2uiux.data.model.FollowersSource
import com.byandev.submission2uiux.data.model.FollowingSource
import com.byandev.submission2uiux.data.model.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("search/users")
    fun searchUsers(
        @Query("q") search: String?
    ): Response<SearchModel>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Response<DetailUser>

    @GET("users/{username}/followers")
    fun followers(
        @Path("username") username: String
    ): Response<FollowersSource>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String
    ): Response<FollowingSource>

}