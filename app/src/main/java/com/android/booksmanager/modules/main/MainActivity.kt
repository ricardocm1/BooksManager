package com.android.booksmanager.modules.main

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.android.booksmanager.R
import com.android.booksmanager.enums.AlertView
import com.android.booksmanager.helpers.NetworkHelper
import com.android.booksmanager.helpers.TinyDB
import com.android.booksmanager.models.Book
import com.android.booksmanager.models.Item
import com.android.booksmanager.modules.main.recyclerview.BookAdapter
import com.android.booksmanager.modules.main.recyclerview.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_no_connection.view.*


class MainActivity : AppCompatActivity(), MainView {
    private var progressDialog: ProgressDialog? = null
    private var alertView: View? = null
    var page: Int = 0
    var itemsAmount = 0
    var filter: String = ""
    var searchView: SearchView? = null

    val presenter: MainPresenter by lazy {
        MainPresenter(this, MainInteractor(TinyDB(this)), NetworkHelper(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.page = 0
        this.setUpRecyclerView()

        this.viewAllRead()
    }

    override fun onResume() {
        super.onResume()
        (this.recyclerView?.adapter as BookAdapter).items = presenter.items
        this.recyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        this.setUpSearchView(menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.app_bar_view_all_read -> {
                viewAllRead()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setActionBarTitle(title: String) {
        this.supportActionBar?.title = title
    }

    private fun viewAllRead() {
        this.setActionBarTitle(getString(R.string.app_name))
        this.resetSearchView()

        this.presenter.loadSavedBooks()
    }

    private fun setUpSearchView(menu: Menu) {
        val searchViewItem = menu.findItem(R.id.app_bar_search)
        this.searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        this.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                this@MainActivity.filter = searchView?.query.toString()
                this@MainActivity.page = 0

                this@MainActivity.request()

                return true
            }
        })

        this.searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                resetSearchView()

                return true
            }
        })
    }

    private fun resetSearchView() {
        this.filter = ""
        this.searchView?.onActionViewCollapsed()
    }

    private fun setUpRecyclerView() {
        this.recyclerView?.layoutManager = LinearLayoutManager(this)
        this.recyclerView?.adapter = BookAdapter(presenter.items, this.presenter)
        this.recyclerView?.isNestedScrollingEnabled = false
    }

    fun request() {
        this.setActionBarTitle(getString(R.string.books_search))
        this.presenter.getBooks(this.page, this.filter)
    }

    override fun loadBooks(book: Book?, saved: Boolean) {
        this.itemsAmount = book!!.totalItems!!
        this.presenter.items.addAll(book.items)

        this.recyclerView?.adapter?.notifyDataSetChanged()
        this.recyclerView.clearOnScrollListeners()

        if (!saved) {
            this.recyclerView?.addOnScrollListener(object : PaginationScrollListener(recyclerView!!.layoutManager as LinearLayoutManager,
                    this,
                    this.itemsAmount) {})
        }
    }

    override fun onFirstItemClicked(position: Int) {
        this.presenter.items.get(position).let {
            this.presenter.addBook(it)

            showSnackBarAddBook(it)
        }
    }

    override fun onSecondItemClicked(position: Int) {
        this.presenter.items.getOrNull(position)?.let {
            undoSaveBook(it)

            removeItem(position)
        }
    }

    fun removeItem(position: Int): Item {
        val adapter = this.recyclerView?.adapter as BookAdapter
        val product = adapter.items[position]
        try {
            this.itemsAmount--
            adapter.items.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, adapter.itemCount)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        return product
    }

    fun showSnackBarAddBook(item: Item) {
        val snackbar = createSnackBar(getString(R.string.save_book_message))

        // Set the action for snack bar
        snackbar.setAction(getString(R.string.undo)) {
            undoSaveBook(item)

            snackbar.dismiss()
        }

        // Finally, display the snack bar
        snackbar.show()
    }

    fun showSnackBarRemoveBook() {
        val snackbar = createSnackBar(getString(R.string.remove_book_message))
        snackbar.show()
    }

    private fun undoSaveBook(item: Item) {
        item.let {
            this.presenter.removeBook(item)

            showSnackBarRemoveBook()
        }
    }

    fun createSnackBar(message: String): Snackbar {
        // Initialize a new snack bar instance
        val snackbar = Snackbar.make(
                snackBarContainer,
                message,
                Snackbar.LENGTH_LONG
        ).apply {
            view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                setMargins(15, 15, 15, 120)
            }
        }

        // Get snack bar root view
        val snackRootView = snackbar.view

        // Get snack bar TextView
        snackRootView.findViewById<TextView>(android.support.design.R.id.snackbar_text)

        // Get snack bar action view
        val snackActionView = snackRootView.findViewById<Button>(android.support.design.R.id.snackbar_action)

        // Changing the background color
        snackRootView.background = ContextCompat.getDrawable(this, R.drawable.layout_snackbar)

        // Change the color of the action
        snackActionView.setTextColor(ResourcesCompat.getColor(resources, R.color.carnation, null))

        return snackbar
    }

    override fun showMessageError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show()
    }

    override fun showAlertView(alertView: AlertView) {
        this.presenter.items.clear()
        this.itemsAmount = 0
        this.recyclerView?.adapter?.notifyDataSetChanged()
        this.alertView = layoutInflater.inflate(alertView.layoutId, this.containerLayout, false)
        this.alertView!!.tryAgainButton?.setOnClickListener {
            this.hideAlertView()

            if (!TextUtils.isEmpty(this.filter))
                this.request()
            else
                this.viewAllRead()
        }

        this.containerLayout?.addView(this.alertView)
    }

    override fun hideAlertView() {
        this.alertView?.let {
            if (it.parent == this.containerLayout) {
                this.containerLayout?.removeView(this.alertView)
                this.alertView = null
            }
        }
    }

    override fun hideProgress() {
        this.presenter.isLoading = false

        if (this.progressDialog != null && this.progressDialog!!.isShowing) {
            this.progressDialog?.dismiss()
            this.progressDialog = null
        }
    }

    override fun showProgress() {
        if (this.progressDialog != null) return

        this.progressDialog = ProgressDialog(this, R.style.ProgressDialog)
        this.progressDialog?.setMessage(this.getString(R.string.loading))
        this.progressDialog?.setCancelable(false)
        this.progressDialog?.show()
    }
}
