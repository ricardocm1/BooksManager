package com.android.booksmanager.modules.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.booksmanager.R
import com.android.booksmanager.models.Item
import com.android.booksmanager.modules.images.ImagesActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val item: Item? = intent?.extras?.getParcelable("item")
        setUpBookDetails(item)

        imageViewBook.setOnClickListener {
            val intent = Intent(this, ImagesActivity::class.java)
            intent.putExtra("item", item)

            startActivity(intent)
        }
    }

    private fun setUpBookDetails(item: Item?) {
        item?.volumeInfo?.let {
            it.imageLinks?.smallThumbnail?.let {
                Glide.with(this@DetailActivity).load(it).into(imageViewBook)
            }

            titleTextView.text = it.title ?: "-"
            descriptionTextView.text = it.description
            pagesTextView.text = String.format("%d %s", it.pageCount, getString(R.string.pages))
            publisherTextView.text = it.publisher
            publishedDateTextView.text = it.publishedDate
            ratingsCountTextView.text = String.format("%d %s", it.ratingsCount, getString(R.string.ratings))
            ratingBar.rating = it.averageRating?.toFloat() ?: 0f

            val categories = StringBuilder()
            it.categories.forEach {
                categories.append(it)
                categories.append("\n")
            }
            categoryTextView.text = categories.toString().removeSuffix("\n")
        }
    }
}