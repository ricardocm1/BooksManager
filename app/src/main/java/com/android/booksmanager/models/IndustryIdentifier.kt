package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName

class IndustryIdentifier() : Parcelable {
    @SerializedName("type")
    var type: String? = null

    @SerializedName("identifier")
    var identifier: String? = null

    private constructor(parcel: Parcel) : this() {
        type = parcel.readString()
        identifier = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(type)
        writeString(identifier)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::IndustryIdentifier)
    }
}