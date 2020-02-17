package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.android.booksmanager.helpers.readBoolean
import com.android.booksmanager.helpers.writeBoolean
import com.google.gson.annotations.SerializedName

class ReadingModes() : Parcelable {
    @SerializedName("text")
    var text: Boolean? = null

    @SerializedName("image")
    var image: Boolean? = null

    private constructor(parcel: Parcel) : this() {
        text = parcel.readBoolean()
        image = parcel.readBoolean()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeBoolean(text ?: false)
        writeBoolean(image ?: false)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ReadingModes)
    }
}