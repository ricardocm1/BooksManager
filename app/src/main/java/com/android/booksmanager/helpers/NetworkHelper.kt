package com.android.booksmanager.helpers

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper(val context: Context) {
    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}