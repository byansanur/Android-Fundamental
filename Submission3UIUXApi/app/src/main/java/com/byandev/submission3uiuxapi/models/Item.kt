package com.byandev.submission3uiuxapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "item"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    var UID: Int? = null,
    var id: Int?,
    var login: String?,
    var avatar_url: String?,
    var type: String?
) : Serializable