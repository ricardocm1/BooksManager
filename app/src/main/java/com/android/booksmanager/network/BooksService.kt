package com.android.booksmanager.network

import com.android.booksmanager.models.Book
import com.android.booksmanager.models.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksService {

    @GET("volumes/")
    fun getBooks(
            @Query("q") filter: String,
            @Query("startIndex") startIndex: Int): Call<Book>

    @GET("volumes/{volumeId}")
    fun getBook(@Path(value = "volumeId", encoded = true) volumeId: String): Call<Item>
}