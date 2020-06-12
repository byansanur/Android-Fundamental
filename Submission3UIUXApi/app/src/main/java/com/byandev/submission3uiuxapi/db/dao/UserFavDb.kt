package com.byandev.submission3uiuxapi.db.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byandev.submission3uiuxapi.models.Item

@Database(
    entities = [Item::class],
    version = 1
)

abstract class UserFavDb : RoomDatabase() {

    abstract fun getItemDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserFavDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserFavDb::class.java,
                "item_user_db.db"
            ).build()
    }

}