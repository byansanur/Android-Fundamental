package com.byandev.submission2uiux.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.byandev.submission2uiux.data.model.Item

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(item: Item) : Long

    @Query("SELECT * FROM user")
    fun readUser(): LiveData<List<Item>>

    @Delete
    suspend fun deleteUser(item: Item)
}