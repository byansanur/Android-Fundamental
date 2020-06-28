package com.byandev.submission2uiux.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.byandev.submission2uiux.data.dao.UserDao
import com.byandev.submission2uiux.data.dao.UserDatabase


class ContentProvider : ContentProvider() {

    private lateinit var userDatabase: UserDatabase
    private lateinit var userDao: UserDao

    companion object {

        const val AUTHORITY = "com.byandev.submission2uiux"

        private const val CODE_USER = 1
        private const val CODE_USER_ITEM = 2

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, "tb_favorite", CODE_USER)
            MATCHER.addURI(AUTHORITY, "tb_favorite/*", CODE_USER_ITEM)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return when {
            MATCHER.match(uri) == CODE_USER -> {
                "vnd.android.cursor.dir/$AUTHORITY.tb_favorite"
            }
            MATCHER.match(uri) == CODE_USER_ITEM -> {
                "vnd.android.cursor.item/$AUTHORITY.tb_favorite"
            }
            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        val context: Context = context?.applicationContext!!
        userDatabase = UserDatabase.invoke(context)
        userDao = userDatabase.getUserDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        var cursor: Cursor? = null

        val code: Int = MATCHER.match(uri)
        if (code == CODE_USER || code == CODE_USER_ITEM) {
            userDao = userDatabase.getUserDao()

            if (code == CODE_USER) {
                cursor = userDao.selectUser()
            }

            cursor?.setNotificationUri(context?.contentResolver, uri)
        } else {
            throw java.lang.IllegalArgumentException("Unknown uri $uri")
        }
        return cursor

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }


}
