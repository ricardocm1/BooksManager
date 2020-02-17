package com.android.booksmanager.modules.main

import android.text.TextUtils
import com.android.booksmanager.enums.AlertView
import com.android.booksmanager.helpers.NetworkHelper
import com.android.booksmanager.models.Book
import com.android.booksmanager.models.Item

class MainPresenter(val mainView: MainView, val mainInteractor: MainInteractor, val networkHelper: NetworkHelper) {
    var items: MutableList<Item> = arrayListOf()
    var isLoading = false

    fun getBooks(page: Int, filter: String) {
        this.mainView.hideAlertView()

        if (!networkHelper.isOnline()) {
            this.mainView.showAlertView(AlertView.NO_CONNECTION)
            return
        }

        if (TextUtils.isEmpty(filter)) {
            this.mainView.showAlertView(AlertView.NO_BOOKS)
            return
        }

        this.isLoading = true

        if (page == 0) {
            this.mainView.showProgress()
            this.items.clear()
        }

        mainInteractor.getBooks(page, filter) { success, book ->
            this.mainView.apply {
                this.hideProgress()

                if (success) {
                    if (book != null && book.items.isNotEmpty())
                        this.loadBooks(book, false)
                    else if (page == 0)
                        this.showAlertView(AlertView.NO_BOOKS)
                } else
                    this.showMessageError()
            }
        }
    }

    fun loadSavedBooks() {
        this.mainView.hideAlertView()
        this.items.clear()

        if (!networkHelper.isOnline()) {
            this.mainView.showAlertView(AlertView.NO_CONNECTION)
            return
        }

        val savedBooks = this.mainInteractor.getAllSavedBooks()

        if (savedBooks.isEmpty())
            this.mainView.showAlertView(AlertView.NO_BOOKS)
        else {
            savedBooks.forEach {
                getBook(it)
            }
        }
    }

    fun getBook(volumeId: String) {
        this.isLoading = true

        this.mainView.showProgress()

        mainInteractor.getBook(volumeId) { success, item ->
            this.mainView.apply {
                this.hideProgress()

                if (success) {
                    if (item != null)
                        this.loadBooks(Book(item), true)
                    else
                        this.showAlertView(AlertView.NO_BOOKS)
                } else
                    this.showMessageError()
            }
        }
    }

    fun addBook(item: Item) {
        this.mainInteractor.addBook(item)
    }

    fun removeBook(item: Item) {
        this.mainInteractor.removeBook(item)
    }

    fun onFirstItemClicked(position: Int) {
        this.mainView.onFirstItemClicked(position)
    }

    fun onSecondItemClicked(position: Int) {
        this.mainView.onSecondItemClicked(position)
    }
}