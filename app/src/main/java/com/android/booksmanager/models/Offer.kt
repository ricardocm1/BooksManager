package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.*
import com.google.gson.annotations.SerializedName

class Offer() : Parcelable {
    @SerializedName("finskyOfferType")
    var finskyOfferType: Int? = null

    @SerializedName("listPrice")
    var listPrice: ListPrice? = null

    @SerializedName("retailPrice")
    var retailPrice: RetailPrice? = null

    @SerializedName("giftable")
    var giftable: Boolean? = null

    private constructor(parcel: Parcel) : this() {
        finskyOfferType = parcel.readInt()
        listPrice = parcel.readTypedObjectCompat(ListPrice.CREATOR)
        retailPrice = parcel.readTypedObjectCompat(RetailPrice.CREATOR)
        giftable = parcel.readBoolean()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(finskyOfferType ?: 0)
        writeTypedObjectCompat(listPrice, flags)
        writeTypedObjectCompat(retailPrice, flags)
        writeBoolean(giftable ?: false)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Offer)
    }
}