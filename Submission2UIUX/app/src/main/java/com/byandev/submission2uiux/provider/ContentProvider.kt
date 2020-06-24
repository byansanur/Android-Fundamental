package com.byandev.submission2uiux.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.byandev.submission2uiux.data.dao.DatabaseContract.AUTHORITY
import com.byandev.submission2uiux.data.dao.DatabaseContract.SCHEME
import com.byandev.submission2uiux.data.dao.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.byandev.submission2uiux.data.dao.UserDao
import com.byandev.submission2uiux.data.dao.UserDatabase


class ContentProvider : ContentProvider() {

    companion object {

        private const val CODE_USER = 1

        private lateinit var userDao: UserDao

        val CONTENT_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE_NAME, CODE_USER)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        if (MATCHER.match(uri) == CODE_USER) {
            return "vnd.android.cursor.dir/$AUTHORITY.$TABLE_NAME"
        }
        throw IllegalArgumentException("Unknown Uri: $uri")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        val userDatabase = UserDatabase
        userDatabase.invoke(context as Context).isOpen
        userDao = userDatabase.invoke(context as Context).getUserDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        val code = MATCHER.match(uri)
        if (code == CODE_USER) {
            cursor = userDao.selectUser()
            cursor.setNotificationUri(context?.contentResolver, uri)
            return cursor
        }
        throw java.lang.IllegalArgumentException("Unknown uri $uri")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }


}
