package com.example.weatherapp.presentation.weather

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatTimeFromUnix(unixTime: Long, timeZoneOffsetSeconds: Int = 0): String {
        val date = Date((unixTime + timeZoneOffsetSeconds) * 1000L)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

}