package com.byandev.submission2uiux.data.dao

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract  {

    const val AUTHORITY = "com.byandev.submission2uiux"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "user"
            const val UIDs = "UIDs"
            const val ID = "id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }

    }
}