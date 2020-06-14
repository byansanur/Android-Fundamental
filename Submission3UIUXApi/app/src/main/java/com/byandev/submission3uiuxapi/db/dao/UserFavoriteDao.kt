package com.byandev.submission3uiuxapi.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.byandev.submission3uiuxapi.models.Item

@Dao
interface UserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(item: Item): Long

    @Query("SELECT * FROM item")
    fun getAllFavoriteUsers(): LiveData<List<Item>>

    @Delete
    suspend fun deleteFavoriteUsers(article: Item)
}