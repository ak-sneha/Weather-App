package com.example.weatherapp.framework.database

import com.example.weatherapp.framework.database.model.Weather
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    suspend fun getWeatherInfo(): Flow<Weather>

    suspend fun insertWeatherInfo(entity: Weather)
}