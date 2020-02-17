package com.android.booksmanager.models

import com.google.gson.annotations.SerializedName

class Book() {
    constructor(item: Item) : this() {
        this.kind = item.kind
        this.totalItems = 1
        this.items = listOf(item)
    }

    @SerializedName("kind")
    var kind: String? = null

    @SerializedName("totalItems")
    var totalItems: Int? = null

    @SerializedName("items")
    var items: List<Item> = ArrayList()
}