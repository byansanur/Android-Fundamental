package com.byandev.submission2uiux.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "user"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    var UIDS: Int? = null,
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null
) : Serializable