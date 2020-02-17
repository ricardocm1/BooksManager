package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.android.booksmanager.helpers.readBoolean
import com.android.booksmanager.helpers.writeBoolean
import com.google.gson.annotations.SerializedName

class Pdf() : Parcelable {
    @SerializedName("isAvailable")
    var isAvailable: Boolean? = null

    private constructor(parcel: Parcel) : this() {
        isAvailable = parcel.readBoolean()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeBoolean(isAvailable ?: false)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Pdf)
    }
}