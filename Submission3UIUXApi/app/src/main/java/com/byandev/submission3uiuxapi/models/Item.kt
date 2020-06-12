package com.byandev.submission3uiuxapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "item_user"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    var _id: Int? = null,
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null
) : Serializable