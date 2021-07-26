package com.example.core.data

import com.example.core.domain.model.WeatherData

interface DataRepository {

    suspend fun getWeatherInfo(): WeatherData
}