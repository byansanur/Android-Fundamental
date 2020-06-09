package com.byandev.fundametalandroid27.optimizationSqliteQuery.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MahasiswaModels(
    var id: Int = 0,
    var name: String? = null,
    var nim: String? = null
) : Parcelable