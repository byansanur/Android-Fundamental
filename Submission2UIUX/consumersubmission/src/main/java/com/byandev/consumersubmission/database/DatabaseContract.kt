package com.byandev.consumersubmission.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract  {

    const val AUTHORITY = "com.byandev.submission2uiux"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "tb_favorite"
            const val ID = "id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"
            const val IS_FAVORITE = "isFavorite"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

            fun getColumnString(cursor: Cursor, columnName: String?): String? {
                return cursor.getString(cursor.getColumnIndex(columnName))
            }

            fun getColumnInt(cursor: Cursor, columnName: String?): Int {
                return cursor.getInt(cursor.getColumnIndex(columnName))
            }

            fun getColumnBool(
                cursor: Cursor,
                columnName: String?
            ): Boolean {
                return cursor.getInt(cursor.getColumnIndex(columnName)) > 0
            }
        }


    }
}