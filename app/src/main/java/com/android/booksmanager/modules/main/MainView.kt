package com.android.booksmanager.modules.main

import com.android.booksmanager.enums.AlertView
import com.android.booksmanager.models.Book

interface MainView {
    fun loadBooks(book: Book?, saved: Boolean)

    // UI
    fun showProgress()
    fun hideProgress()
    fun showMessageError()
    fun showAlertView(alertView: AlertView)
    fun hideAlertView()

    fun onFirstItemClicked(position: Int)
    fun onSecondItemClicked(position: Int)
}