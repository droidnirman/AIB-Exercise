package com.aib.exercise.utils

import java.text.SimpleDateFormat
import java.util.*


@Suppress("unused")
fun convertDateToString(dateInMilliseconds: Int): String {

    val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())
    val date = Date(dateInMilliseconds * 1000L)
    return dateFormat.format(date)
}

fun convertDateTextToNewFormat(oldFormatDateText: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("E, dd MMM", Locale.getDefault())
    return formatter.format(parser.parse(oldFormatDateText)!!)
}