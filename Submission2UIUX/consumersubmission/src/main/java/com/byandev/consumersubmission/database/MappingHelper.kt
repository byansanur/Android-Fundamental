package com.byandev.consumersubmission.database

import android.database.Cursor
import com.byandev.consumersubmission.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.byandev.consumersubmission.database.DatabaseContract.UserColumns.Companion.ID
import com.byandev.consumersubmission.database.DatabaseContract.UserColumns.Companion.IS_FAVORITE
import com.byandev.consumersubmission.database.DatabaseContract.UserColumns.Companion.LOGIN
import com.byandev.consumersubmission.database.DatabaseContract.UserColumns.Companion.TYPE
import com.byandev.consumersubmission.model.Item


class MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor): ArrayList<Item>? {
        val movieList: ArrayList<Item> = ArrayList()
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            val login: String = cursor.getString(cursor.getColumnIndexOrThrow(LOGIN))
            val avatar_url: String = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL))
            val type: String = cursor.getString(cursor.getColumnIndexOrThrow(TYPE))
            val isFavorite: Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(IS_FAVORITE)) > 0
            movieList.add(Item(id, login, avatar_url, type, isFavorite))
        }
        return movieList
    }

}