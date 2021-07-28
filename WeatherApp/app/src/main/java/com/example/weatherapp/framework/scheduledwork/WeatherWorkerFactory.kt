package com.example.weatherapp.framework.scheduledwork

import androidx.work.DelegatingWorkerFactory
import com.example.core.interactor.GetLocationFromDB
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.framework.database.model.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherWorkerFactory @Inject constructor(
    private val getWeatherInfo: GetWeatherInfo<Weather>,
    private val getLocationFromDB: GetLocationFromDB<Weather>
) : DelegatingWorkerFactory() {
    init {
        addFactory(this)
        // Add here other factories that you may need in your application
    }
}