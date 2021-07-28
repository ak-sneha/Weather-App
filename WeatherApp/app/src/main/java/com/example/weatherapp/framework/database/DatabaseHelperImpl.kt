package com.example.weatherapp.framework.database

import com.example.weatherapp.framework.database.model.Weather

class DatabaseHelperImpl(private val appDatabase: WeatherDatabase) : DatabaseHelper {
    override suspend fun getWeatherInfo() = appDatabase.weatherInfoDao().getWeather()

    override suspend fun insertWeatherInfo(entity: Weather) =
        appDatabase.weatherInfoDao().insert(entity)
}