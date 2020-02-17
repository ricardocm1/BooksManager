package com.android.booksmanager.modules.images

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.booksmanager.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ortiz.touchview.TouchImageView

class SlidingImageAdapter(private val context: Context, private val images: List<String>) : PagerAdapter() {

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.view_image_book, parent, false)
        val imageView = view.findViewById(R.id.bookImage) as TouchImageView
        val url: String? = images[position]

        Glide.with(parent.context)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(R.drawable.book_error_image)
                        .error(R.drawable.book_error_image)
                        .fitCenter()
                )
                .into(imageView)

        parent.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}