package com.android.booksmanager.enums

import com.android.booksmanager.R

enum class AlertView {
    NO_BOOKS {
        override val layoutId = R.layout.view_no_books
    },
    NO_CONNECTION {
        override val layoutId = R.layout.view_no_connection
    };

    abstract val layoutId: Int
}