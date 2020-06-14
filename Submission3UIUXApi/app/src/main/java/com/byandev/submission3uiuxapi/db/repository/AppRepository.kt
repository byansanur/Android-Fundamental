package com.byandev.submission3uiuxapi.db.repository

import com.byandev.submission3uiuxapi.api.RetrofitClient
import com.byandev.submission3uiuxapi.db.dao.UserFavDao
import com.byandev.submission3uiuxapi.models.Item

class AppRepository(private val db: UserFavDao) {
    suspend fun fetchDetailUsers(userName: String) =
        RetrofitClient.api.detailUser(userName)


    suspend fun getFollowers(userName: String, pageNumber: Int) =
        RetrofitClient.api.followers(userName, pageNumber)


    suspend fun getFollowing(userName: String, pageNumber: Int) =
        RetrofitClient.api.following(userName, pageNumber)

    suspend fun searchUser(query: String, pageNumber: Int) =
        RetrofitClient.api.searchUsers(query, pageNumber)

    suspend fun insertFav(item: Item) = db.getArticleDao().inserts(item)

    fun getSavedFav() = db.getArticleDao().getAllFavoriteUsers()


    suspend fun deleteFav(item: Item) = db.getArticleDao().deleteFavoriteUsers(item)
}