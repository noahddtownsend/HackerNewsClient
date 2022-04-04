package com.noahtownsend.hackernewsclient

import android.content.Context
import android.net.ConnectivityManager

class SystemValuesUtil {
    companion object {
        fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}