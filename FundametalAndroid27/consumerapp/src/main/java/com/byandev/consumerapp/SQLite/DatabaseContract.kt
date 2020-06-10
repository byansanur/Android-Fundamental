package com.byandev.consumerapp.SQLite

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.byandev.fundametalandroid27" // sesuaikan dengan file manifest <provider/>
    const val SCHEME = "content"

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"

            // materi content resolver
            // untuk membuat URI content://com.byandev.fundametalandroid27/note
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}