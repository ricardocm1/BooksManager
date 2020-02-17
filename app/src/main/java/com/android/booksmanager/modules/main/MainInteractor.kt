package com.android.booksmanager.modules.main

import android.util.Log
import com.android.booksmanager.Constants
import com.android.booksmanager.helpers.TinyDB
import com.android.booksmanager.models.Book
import com.android.booksmanager.models.Item
import com.android.booksmanager.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainInteractor(val dataBase: TinyDB) {
    private val retrofitService by lazy {
        ApiClient.bookManager()
    }

    fun getBooks(page: Int, filter: String, callback: (success: Boolean, Book?) -> Unit) {
        val filter2 = "intitle:".plus(filter)
        val startIndex = page * 10

        val request = retrofitService.getBooks(filter2, startIndex)

        request.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>?, response: Response<Book>?) {
                response?.body()?.let {
                    callback(true, it)
                } ?: run {
                    callback(false, null)
                }
            }

            override fun onFailure(call: Call<Book>?, t: Throwable?) {
                Log.e("books request", t?.localizedMessage, t)
                callback(false, null)
            }
        })
    }

    fun getBook(volumeId: String, callback: (success: Boolean, Item?) -> Unit) {
        val request = retrofitService.getBook(volumeId)

        request.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>?, response: Response<Item>?) {
                response?.body()?.let {
                    callback(true, it)
                } ?: run {
                    callback(false, null)
                }
            }

            override fun onFailure(call: Call<Item>?, t: Throwable?) {
                Log.e("book request", t?.localizedMessage, t)
                callback(false, null)
            }
        })
    }

    fun addBook(item: Item) {
        val savedBooks = dataBase.getListString(Constants.SAVED_AS_READ_LIST)

        if (!savedBooks.contains(item.id!!)) {
            savedBooks.add(item.id!!)
            dataBase.putListString(Constants.SAVED_AS_READ_LIST, savedBooks)
        }
    }

    fun getAllSavedBooks(): ArrayList<String> {
        return dataBase.getListString(Constants.SAVED_AS_READ_LIST)
    }

    fun removeBook(item: Item) {
        val savedBooks = dataBase.getListString(Constants.SAVED_AS_READ_LIST)
        savedBooks.remove(item.id!!)

        dataBase.putListString(Constants.SAVED_AS_READ_LIST, savedBooks)
    }
}
