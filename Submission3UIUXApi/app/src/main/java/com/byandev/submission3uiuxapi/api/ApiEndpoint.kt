package com.byandev.submission3uiuxapi.api

import com.byandev.submission3uiuxapi.models.DetailUser
import com.byandev.submission3uiuxapi.models.FollowersSource
import com.byandev.submission3uiuxapi.models.FollowingSource
import com.byandev.submission3uiuxapi.models.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") search: String,
        @Query("page") page: Int =1
    ): Response<SearchModel>

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): Response<DetailUser>

    @GET("users/{username}/followers")
    suspend fun followers(
        @Path("username") username: String,
        @Query("page") page: Int = 1
    ): Response<FollowersSource>

    @GET("users/{username}/following")
    suspend fun following(
        @Path("username") username: String,
        @Query("page") page: Int = 1
    ): Response<FollowingSource>
}