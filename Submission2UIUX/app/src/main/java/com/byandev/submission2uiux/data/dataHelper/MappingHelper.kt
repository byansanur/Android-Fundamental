package com.byandev.submission2uiux.data.dataHelper

import android.database.Cursor
import com.byandev.submission2uiux.data.dao.DatabaseContract
import com.byandev.submission2uiux.data.model.Item

object MappingHelper {
    fun mapCursorToObject(cursor: Cursor?): Item {
        var item = Item(0, 0, null, null, null)
        cursor?.apply {
            moveToFirst()
            val UIDs = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.UIDs))
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID))
            val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
            val type = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE))
            item = Item(UIDs, id, login, avatar, type)
        }
        return item
    }
}