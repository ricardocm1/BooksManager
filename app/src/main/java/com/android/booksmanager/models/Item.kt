package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.parcelableCreator
import com.android.booksmanager.helpers.readTypedObjectCompat
import com.android.booksmanager.helpers.writeTypedObjectCompat
import com.google.gson.annotations.SerializedName

class Item() : Parcelable {
    @SerializedName("kind")
    var kind: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("etag")
    var etag: String? = null

    @SerializedName("selfLink")
    var selfLink: String? = null

    @SerializedName("volumeInfo")
    var volumeInfo: VolumeInfo? = null

    @SerializedName("saleInfo")
    var saleInfo: SaleInfo? = null

    @SerializedName("accessInfo")
    var accessInfo: AccessInfo? = null

    @SerializedName("searchInfo")
    var searchInfo: SearchInfo? = null

    private constructor(parcel: Parcel) : this() {
        kind = parcel.readString()
        id = parcel.readString()
        etag = parcel.readString()
        selfLink = parcel.readString()
        volumeInfo = parcel.readTypedObjectCompat(VolumeInfo.CREATOR)
        saleInfo = parcel.readTypedObjectCompat(SaleInfo.CREATOR)
        accessInfo = parcel.readTypedObjectCompat(AccessInfo.CREATOR)
        searchInfo = parcel.readTypedObjectCompat(SearchInfo.CREATOR)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(kind)
        writeString(id)
        writeString(etag)
        writeString(selfLink)
        writeTypedObjectCompat(volumeInfo, flags)
        writeTypedObjectCompat(saleInfo, flags)
        writeTypedObjectCompat(accessInfo, flags)
        writeTypedObjectCompat(searchInfo, flags)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Item)
    }
}