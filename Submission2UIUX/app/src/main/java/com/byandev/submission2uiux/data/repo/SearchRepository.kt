package com.byandev.submission2uiux.data.repo

import com.byandev.submission2uiux.data.Dao.SearchDatabase
import com.byandev.submission2uiux.data.model.Item
import com.byandev.submission2uiux.network.RetrofitClient

class SearchRepository (val db : SearchDatabase) {

    suspend fun searchUser(query: String) =
        RetrofitClient.api.searchUsers(query)

    suspend fun upsert(search: Item) = db.getSearchDao().upsert(search)


}