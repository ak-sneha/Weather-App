package com.example.core.data

import com.example.core.domain.model.WeatherData

interface DataSource {

    fun getApiKey() : String

    suspend fun getWeatherData(queryParams: Map<String, String>): WeatherData

    fun getCurrentLocation() : Pair<String, String>
}