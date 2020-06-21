package com.byandev.submission2uiux.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byandev.submission2uiux.data.model.Item

@Database(
    entities = [Item::class],
    version = 2
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

    companion object {
        @Volatile
        private var instance : UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                "user_fav.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

}