package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName

class ImageLinks() : Parcelable {
    @SerializedName("smallThumbnail")
    var smallThumbnail: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    private constructor(parcel: Parcel) : this() {
        smallThumbnail = parcel.readString()
        thumbnail = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(smallThumbnail)
        writeString(thumbnail)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ImageLinks)
    }
}