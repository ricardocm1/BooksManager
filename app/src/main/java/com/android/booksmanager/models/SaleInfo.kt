package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.*
import com.google.gson.annotations.SerializedName

class SaleInfo() : Parcelable {
    @SerializedName("country")
    var country: String? = null

    @SerializedName("saleability")
    var saleability: String? = null

    @SerializedName("isEbook")
    var isEbook: Boolean? = null

    @SerializedName("listPrice")
    var listPrice: ListPrice? = null

    @SerializedName("retailPrice")
    var retailPrice: RetailPrice? = null

    @SerializedName("buyLink")
    var buyLink: String? = null

    @SerializedName("offers")
    var offers = ArrayList<Offer>()

    private constructor(parcel: Parcel) : this() {
        country = parcel.readString()
        saleability = parcel.readString()
        isEbook = parcel.readBoolean()
        listPrice = parcel.readTypedObjectCompat(ListPrice.CREATOR)
        retailPrice = parcel.readTypedObjectCompat(RetailPrice.CREATOR)
        buyLink = parcel.readString()
        offers = arrayListOf<Offer>().apply {
            parcel.readTypedList(this, Offer.CREATOR)
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(country)
        writeString(saleability)
        writeBoolean(isEbook ?: false)
        writeTypedObjectCompat(listPrice, flags)
        writeTypedObjectCompat(retailPrice, flags)
        writeString(buyLink)
        writeTypedList(offers)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::SaleInfo)
    }
}