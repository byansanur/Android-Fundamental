package com.byandev.submission1githubuser

import android.os.Parcel
import android.os.Parcelable

data class DataSource(
    val avatar: String,
    val company: String,
    val follower: Int,
    val following: Int,
    val location: String,
    val name: String,
    val repository: Int,
    val username: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar)
        parcel.writeString(company)
        parcel.writeInt(follower)
        parcel.writeInt(following)
        parcel.writeString(location)
        parcel.writeString(name)
        parcel.writeInt(repository)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataSource> {
        override fun createFromParcel(parcel: Parcel): DataSource {
            return DataSource(parcel)
        }

        override fun newArray(size: Int): Array<DataSource?> {
            return arrayOfNulls(size)
        }
    }
}