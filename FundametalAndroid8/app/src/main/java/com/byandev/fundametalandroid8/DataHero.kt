package com.byandev.fundametalandroid8

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataHero(
    var name: String,
    var description: String,
    var photo: String
) : Parcelable