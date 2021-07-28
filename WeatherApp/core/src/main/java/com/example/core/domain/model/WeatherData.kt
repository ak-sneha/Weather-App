package com.example.core.domain.model

import java.util.*

data class WeatherData(
    val location: String?,
    val latitude: Double?,
    val longitude: Double?,
    val lastUpdateDate: Date?,
    val temperature: Double,
    val temp_min: Double?,
    val temp_max: Double?,
    val wind: Double?,
    val pressure: Int?,
    val humidity: Int?,
    val sunrise: Date?,
    val sunset: Date?
)
