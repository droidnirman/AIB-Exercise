package com.aib.exercise.utils

import android.content.Context
import android.net.ConnectivityManager

@Suppress("unused")
fun Context.isNetworkStatusAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        it.activeNetworkInfo?.let { networkInfo ->
            if (networkInfo.isConnected) return true
        }
    }
    return false
}