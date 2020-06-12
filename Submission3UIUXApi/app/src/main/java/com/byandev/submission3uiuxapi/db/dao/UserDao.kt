package com.byandev.submission3uiuxapi.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.byandev.submission3uiuxapi.models.Item

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Item): Long

    @Query("SELECT * FROM item_user")
    fun getAllUser(): LiveData<List<Item>>

    @Delete
    suspend fun deleteItemUser(item: Item)
}