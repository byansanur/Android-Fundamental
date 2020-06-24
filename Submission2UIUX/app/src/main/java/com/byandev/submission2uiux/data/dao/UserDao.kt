package com.byandev.submission2uiux.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.byandev.submission2uiux.data.model.Item

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(favoriteItem: Item) : Long

    @Query("SELECT * FROM tb_favorite")
    fun readUser(): LiveData<List<Item>>

    @Delete
    suspend fun deleteUser(favoriteItem: Item)

    @Query("SELECT * FROM tb_favorite")
    fun selectUser() : Cursor

}