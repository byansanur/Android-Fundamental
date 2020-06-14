package com.byandev.submission3uiuxapi.db.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byandev.submission3uiuxapi.models.Item

@Database(
    entities = [Item::class],
    version = 5
)
abstract class UserFavDao : RoomDatabase() {

    abstract fun getArticleDao() : UserFavoriteDao

    companion object {
        @Volatile
        private var instance : UserFavDao? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserFavDao::class.java,
                "item_fav.db"
            ).build()
    }
}