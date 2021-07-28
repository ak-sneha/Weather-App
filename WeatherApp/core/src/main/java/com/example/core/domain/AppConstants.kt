package com.example.core.domain

import android.Manifest.permission.ACCESS_FINE_LOCATION

class AppConstants {

    companion object {

        const val TWO_HOURS: Int = 2 * 60 * 60 * 1000
        const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

        const val KEY_LATITUDE = "lat"
        const val KEY_LONGITUDE = "lon"
        const val KEY_UNITS = "units"
        const val KEY_APPID = "appid"
        const val UNITS = "metric"

        const val REQUEST_LOCATION_PERMISSION = 1

        val locationPermission = ACCESS_FINE_LOCATION
    }
}