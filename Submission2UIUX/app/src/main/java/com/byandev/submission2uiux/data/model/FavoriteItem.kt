package com.byandev.submission2uiux.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteItem(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var type: String? = null
) : Parcelable