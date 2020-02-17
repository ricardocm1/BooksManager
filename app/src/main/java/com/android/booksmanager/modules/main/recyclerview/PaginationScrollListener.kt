package com.android.booksmanager.modules.main.recyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.booksmanager.modules.main.MainActivity

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager, val view: MainActivity, val totalItens: Int) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount

        if (totalItens <= (view.page + 1) * 10) {
            return
        }

        val visibleItemCount = layoutManager.childCount
        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && !view.presenter.isLoading) {
            view.page = getPageToLoad()
            view.request()
        }
    }

    private fun getPageToLoad(): Int {
        return Math.ceil(layoutManager.itemCount.toDouble() / 10).toInt()
    }
}
