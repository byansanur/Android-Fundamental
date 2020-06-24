package com.byandev.consumersubmission.model

import java.io.Serializable


data class Item(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null,
    var isFavorite: Boolean = false
) : Serializable

//fun fromContentValues(values: ContentValues): Item {
//    val favorite = Item()
//    if (values.containsKey(DatabaseContract.UserColumns.ID))
//        favorite.id = values.getAsInteger(DatabaseContract.UserColumns.ID)
//
//    if (values.containsKey(DatabaseContract.UserColumns.LOGIN))
//        favorite.login = values.getAsString(DatabaseContract.UserColumns.LOGIN)
//
//    if (values.containsKey(DatabaseContract.UserColumns.AVATAR_URL))
//        favorite.avatar_url = values.getAsString(DatabaseContract.UserColumns.AVATAR_URL)
//
//    if (values.containsKey(DatabaseContract.UserColumns.TYPE))
//        favorite.type = values.getAsString(DatabaseContract.UserColumns.TYPE)
//
//    return favorite
//}
//
//fun Item(cursor: Cursor?) {
//    this.id = getColumnInt(cursor, DatabaseContract.UserColumns.ID)
//    this.login = getColumnString(cursor, DatabaseContract.UserColumns.LOGIN)
//    this.avatar_url = getColumnString(cursor, DatabaseContract.UserColumns.AVATAR_URL)
//    this.type = getColumnString(cursor, DatabaseContract.UserColumns.TYPE)
//}