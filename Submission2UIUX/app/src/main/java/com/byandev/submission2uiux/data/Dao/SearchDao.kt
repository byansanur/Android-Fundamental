package com.byandev.submission2uiux.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byandev.submission2uiux.data.model.Item

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(search: Item) : Long

    @Query("SELECT * FROM item_search")
    fun getSearchAll(): LiveData<List<Item>>
}