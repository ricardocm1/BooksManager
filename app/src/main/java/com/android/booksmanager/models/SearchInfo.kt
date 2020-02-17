package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName

class SearchInfo() : Parcelable {
    @SerializedName("textSnippet")
    var textSnippet: String? = null

    private constructor(parcel: Parcel) : this() {
        textSnippet = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(textSnippet)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::SearchInfo)
    }
}