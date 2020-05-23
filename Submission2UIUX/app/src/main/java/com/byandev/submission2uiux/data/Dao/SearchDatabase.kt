package com.byandev.submission2uiux.data.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byandev.submission2uiux.data.model.Item

@Database(
    entities = [Item::class],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun getSearchDao(): SearchDao

    companion object {
        private var instance : SearchDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabases(context).also {
                instance = it
            }
        }

        private fun createDatabases(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SearchDatabase::class.java,
                "search_db.db"
            ).build()
    }
}