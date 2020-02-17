package com.android.booksmanager.modules.images

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.android.booksmanager.R
import com.android.booksmanager.models.Item
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.activity_images.*

class ImagesActivity : AppCompatActivity() {
    private var pageChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val item: Item? = intent?.extras?.getParcelable("item")
        loadImages(item)
    }

    fun loadImages(item: Item?) {
        item?.volumeInfo?.let {
            this.toolbarTitle.text = it.title

            val images = mutableListOf<String>()
            it.imageLinks?.let {
                it.thumbnail?.let { images.add(it) }
            }

            this.viewPager.adapter = SlidingImageAdapter(this@ImagesActivity, images)

            this.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    tabIndicator.getTabAt(position)?.select()
                    pageChanged = true
                }

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_IDLE && pageChanged) {
                        for (i in 0 until viewPager.getChildCount()) {
                            val touchImageView = viewPager.getChildAt(i) as TouchImageView
                            if (touchImageView.isZoomed) {
                                touchImageView.resetZoom()
                            }
                        }

                        pageChanged = false
                    }
                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }
            })

            this.tabIndicator.removeAllTabs()
            for (i in 1..images.size)
                this.tabIndicator.addTab(tabIndicator.newTab())
        }
    }
}