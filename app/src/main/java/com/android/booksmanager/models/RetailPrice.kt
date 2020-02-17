package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName

class RetailPrice() : Parcelable {
    @SerializedName("amount")
    var amount: Double? = null

    @SerializedName("currencyCode")
    var currencyCode: String? = null

    private constructor(parcel: Parcel) : this() {
        amount = parcel.readDouble()
        currencyCode = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(amount ?: 0.0)
        writeString(currencyCode)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::RetailPrice)
    }
}