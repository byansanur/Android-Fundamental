package com.byandev.submission2uiux.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.byandev.submission2uiux.data.dao.DatabaseContract.AUTHORITY
import com.byandev.submission2uiux.data.dao.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.byandev.submission2uiux.data.dao.UserDao
import com.byandev.submission2uiux.data.dao.UserDatabase


class ContentProvider : ContentProvider() {

    companion object {

        private const val CODE_USER = 1

        private lateinit var userDao: UserDao


        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE_NAME, CODE_USER)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert one or more rows.")
    }

    override fun onCreate(): Boolean {
        val userDatabase = UserDatabase
        userDatabase.invoke(context as Context).isOpen
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when(MATCHER.match(uri)) {
            CODE_USER -> cursor = UserDatabase.invoke(context as Context).getUserDao().selectUser()
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

//    override fun applyBatch(
//        operations: ArrayList<ContentProviderOperation?>
//    ) : Array<ContentProviderResult?> {
//        val context = context ?: return arrayOfNulls(0)
//        val database: UserDatabase = UserDatabase.invoke(context)
//        database.beginTransaction()
//        return try {
//            val result = super.applyBatch(operations)
//            database.setTransactionSuccessful()
//            result
//        } finally {
//            database.endTransaction()
//        }
//    }

}
