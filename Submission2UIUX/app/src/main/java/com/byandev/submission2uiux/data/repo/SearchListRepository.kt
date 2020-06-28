package com.byandev.submission2uiux.data.repo

import com.byandev.submission2uiux.data.dao.UserDatabase
import com.byandev.submission2uiux.data.model.Item
import com.byandev.submission2uiux.network.RetrofitInstance

class SearchListRepository(val db: UserDatabase) {

    suspend fun searchUser(query: String, pageNumber: Int) =
        RetrofitInstance.api.searchUsers(query, pageNumber)

    suspend fun fetchDetailUsers(userName: String) =
        RetrofitInstance.api.detailUser(userName)

    suspend fun userFollowers(userName: String, pageNumber: Int) =
        RetrofitInstance.api.followers(userName, pageNumber)

    suspend fun userFollowing(userName: String, pageNumber: Int) =
        RetrofitInstance.api.following(userName, pageNumber)

    suspend fun createUserFav(favoriteItem: Item) = db.getUserDao().createUser(favoriteItem)

    fun readUserFav() = db.getUserDao().readUser()

    suspend fun deleteUserFav(favoriteItem: Item) = db.getUserDao().deleteUser(favoriteItem)

    fun getWidgetFav(id: Int) = db.getUserDao().getWidget()

    fun getAllWidgetFav() = db.getUserDao().getAllForWidget()
}