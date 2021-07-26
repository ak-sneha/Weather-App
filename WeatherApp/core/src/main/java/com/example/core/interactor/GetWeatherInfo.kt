package com.example.core.interactor

import com.example.core.data.DataRepository
import com.example.core.domain.model.WeatherData
import javax.inject.Inject

class GetWeatherInfo @Inject constructor(private val dataRepository: DataRepository) {

    suspend fun getWeatherData(): WeatherData {
        return dataRepository.getWeatherInfo()
    }
}