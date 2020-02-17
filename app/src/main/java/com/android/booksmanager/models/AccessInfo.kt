package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.*
import com.google.gson.annotations.SerializedName

class AccessInfo() : Parcelable {
    @SerializedName("country")
    var country: String? = null

    @SerializedName("viewability")
    var viewability: String? = null

    @SerializedName("embeddable")
    var embeddable: Boolean? = null

    @SerializedName("publicDomain")
    var publicDomain: Boolean? = null

    @SerializedName("textToSpeechPermission")
    var textToSpeechPermission: String? = null

    @SerializedName("epub")
    var epub: Epub? = null

    @SerializedName("pdf")
    var pdf: Pdf? = null

    @SerializedName("webReaderLink")
    var webReaderLink: String? = null

    @SerializedName("accessViewStatus")
    var accessViewStatus: String? = null

    @SerializedName("quoteSharingAllowed")
    var quoteSharingAllowed: Boolean? = null

    private constructor(parcel: Parcel) : this() {
        country = parcel.readString()
        viewability = parcel.readString()
        embeddable = parcel.readBoolean()
        publicDomain = parcel.readBoolean()
        textToSpeechPermission = parcel.readString()
        epub = parcel.readTypedObjectCompat(Epub.CREATOR)
        pdf = parcel.readTypedObjectCompat(Pdf.CREATOR)
        webReaderLink = parcel.readString()
        accessViewStatus = parcel.readString()
        quoteSharingAllowed = parcel.readBoolean()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(country)
        writeString(viewability)
        writeBoolean(embeddable ?: false)
        writeBoolean(publicDomain ?: false)
        writeString(textToSpeechPermission)
        writeTypedObjectCompat(epub, flags)
        writeTypedObjectCompat(pdf, flags)
        writeString(webReaderLink)
        writeString(accessViewStatus)
        writeBoolean(quoteSharingAllowed ?: false)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::AccessInfo)
    }
}