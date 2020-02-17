package com.android.booksmanager.modules.main.recyclerview

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_book.view.*
import kotlinx.android.synthetic.main.view_book.view.*
import kotlinx.android.synthetic.main.view_book_options.view.*

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bookImageView: ImageView = view.bookImageView
    val bookCardView: CardView = view.bookCardView
    val titleTextView: TextView = view.titleTextView
    val authorTextView: TextView = view.authorTextView
    val publisherTextView: TextView = view.publisherTextView
    val pageCountTextView: TextView = view.pageCountTextView
    val languageTextView: TextView = view.languageTextView

    val firstItemLayout: Array<LinearLayout> = arrayOf(view.smMenuViewRight.firstItemLayout, view.smMenuViewLeft.firstItemLayout)
    val secondItemLayout: Array<LinearLayout> = arrayOf(view.smMenuViewRight.secondItemLayout, view.smMenuViewLeft.secondItemLayout)

    val leftSwipeCardView: CardView = view.smMenuViewLeft.swipeCardView
    val rightSwipeCardView: CardView = view.smMenuViewRight.swipeCardView
}