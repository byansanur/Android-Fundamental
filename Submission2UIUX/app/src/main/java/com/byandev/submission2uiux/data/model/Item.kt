package com.byandev.submission2uiux.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "tb_favorite",
    indices = [Index(value = ["login"], unique = true)]
)
data class Item(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "login") var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null,
    var isFavorite: Boolean = false
) : Serializable