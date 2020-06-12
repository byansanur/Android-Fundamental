package com.byandev.submission3uiuxapi.db.repository

import com.byandev.submission3uiuxapi.db.dao.UserFavDb
import com.byandev.submission3uiuxapi.models.Item

class FavoriteRepository(val db: UserFavDb) {

    suspend fun insertFav(item: Item) = db.getItemDao().upsert(item)

    fun getSavedFav() = db.getItemDao().getAllUser()

    suspend fun deleteFav(item: Item) = db.getItemDao().deleteItemUser(item)
}