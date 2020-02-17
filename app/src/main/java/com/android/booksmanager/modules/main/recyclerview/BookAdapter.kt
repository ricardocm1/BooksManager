package com.android.booksmanager.modules.main.recyclerview

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.booksmanager.R
import com.android.booksmanager.models.Item
import com.android.booksmanager.modules.detail.DetailActivity
import com.android.booksmanager.modules.main.MainPresenter
import com.bumptech.glide.Glide
import com.tubb.smrv.SwipeHorizontalMenuLayout

class BookAdapter(var items: MutableList<Item>, val presenter: MainPresenter) : RecyclerView.Adapter<BookViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_book, parent, false))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = items.get(index = position)

        this.setUpBook(item, holder)
    }

    private fun setUpBook(item: Item, holder: BookViewHolder) {
        holder.titleTextView.text = item.volumeInfo?.title ?: "-"
        holder.authorTextView.text = item.volumeInfo?.authors?.firstOrNull() ?: "-"
        holder.publisherTextView.text = item.volumeInfo?.publisher ?: "-"
        val pageCount = item.volumeInfo?.pageCount ?: 0
        holder.pageCountTextView.text = if (pageCount <= 0) "-" else pageCount.toString()
        holder.languageTextView.text = item.volumeInfo?.language ?: "-"

        this.setUpClicks(holder, item)

        item.volumeInfo?.imageLinks?.thumbnail?.let {
            Glide.with(holder.itemView.context).load(it).into(holder.bookImageView)
        }
    }

    private fun setUpClicks(holder: BookViewHolder, item: Item) {
        val layoutParamsLeft: ViewGroup.MarginLayoutParams = holder.leftSwipeCardView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsLeft.setMargins(-5, 0, 0, 0)

        val layoutParamsRight: ViewGroup.MarginLayoutParams = holder.rightSwipeCardView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsRight.setMargins(0, 0, -5, 0)

        holder.firstItemLayout.forEach {
            it.setOnClickListener {
                presenter.onFirstItemClicked(items.indexOf(item))
                (holder.itemView as? SwipeHorizontalMenuLayout)?.smoothCloseMenu() ?: run {
                    holder.itemView.findViewById<SwipeHorizontalMenuLayout>(R.id.smBook)?.smoothCloseMenu()
                }
            }
        }

        holder.secondItemLayout.forEach {
            it.setOnClickListener {
                presenter.onSecondItemClicked(items.indexOf(item))
                (holder.itemView as? SwipeHorizontalMenuLayout)?.smoothCloseMenu() ?: run {
                    holder.itemView.findViewById<SwipeHorizontalMenuLayout>(R.id.smBook)?.smoothCloseMenu()
                }
            }
        }

        holder.bookCardView.setOnClickListener {
            item.let {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("item", it)

                holder.itemView.context.startActivity(intent)
            }
        }
    }
}