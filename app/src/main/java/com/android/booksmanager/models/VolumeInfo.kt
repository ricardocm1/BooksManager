package com.android.booksmanager.models

import android.os.Parcel
import android.os.Parcelable
import com.android.booksmanager.helpers.*
import com.google.gson.annotations.SerializedName

class VolumeInfo() : Parcelable {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("authors")
    var authors = ArrayList<String>()

    @SerializedName("publisher")
    var publisher: String? = null

    @SerializedName("publishedDate")
    var publishedDate: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("industryIdentifiers")
    var industryIdentifiers = ArrayList<IndustryIdentifier>()

    @SerializedName("readingModes")
    var readingModes: ReadingModes? = null

    @SerializedName("pageCount")
    var pageCount: Int? = null

    @SerializedName("printType")
    var printType: String? = null

    @SerializedName("categories")
    var categories = ArrayList<String>()

    @SerializedName("averageRating")
    var averageRating: Double? = null

    @SerializedName("ratingsCount")
    var ratingsCount: Int? = null

    @SerializedName("maturityRating")
    var maturityRating: String? = null

    @SerializedName("allowAnonLogging")
    var allowAnonLogging: Boolean? = null

    @SerializedName("contentVersion")
    var contentVersion: String? = null

    @SerializedName("imageLinks")
    var imageLinks: ImageLinks? = null

    @SerializedName("language")
    var language: String? = null

    @SerializedName("previewLink")
    var previewLink: String? = null

    @SerializedName("infoLink")
    var infoLink: String? = null

    @SerializedName("canonicalVolumeLink")
    var canonicalVolumeLink: String? = null

    private constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        authors = arrayListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        }
        publisher = parcel.readString()
        publishedDate = parcel.readString()
        description = parcel.readString()
        industryIdentifiers = arrayListOf<IndustryIdentifier>().apply {
            parcel.readTypedList(this, IndustryIdentifier.CREATOR)
        }
        readingModes = parcel.readTypedObjectCompat(ReadingModes.CREATOR)
        pageCount = parcel.readValue(Int::class.java.getClassLoader()) as Int?
        printType = parcel.readString()
        categories = arrayListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        }
        averageRating = parcel.readDouble()
        ratingsCount = parcel.readInt()
        maturityRating = parcel.readString()
        allowAnonLogging = parcel.readBoolean()
        contentVersion = parcel.readString()
        imageLinks = parcel.readTypedObjectCompat(ImageLinks.CREATOR)
        language = parcel.readString()
        previewLink = parcel.readString()
        infoLink = parcel.readString()
        canonicalVolumeLink = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeList(authors)
        writeString(publisher)
        writeString(publishedDate)
        writeString(description)
        writeTypedList(industryIdentifiers)
        writeTypedObjectCompat(readingModes, flags)
        writeValue(pageCount)
        writeString(printType)
        writeList(categories)
        writeDouble(averageRating ?: 0.0)
        writeInt(ratingsCount ?: 0)
        writeString(maturityRating)
        writeBoolean(allowAnonLogging ?: false)
        writeString(contentVersion)
        writeTypedObjectCompat(imageLinks, flags)
        writeString(language)
        writeString(previewLink)
        writeString(infoLink)
        writeString(canonicalVolumeLink)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::VolumeInfo)
    }
}