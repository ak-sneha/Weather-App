package com.example.core.interactor

import com.example.core.data.DataRepository
import com.example.core.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherInfo<T> @Inject constructor(private val dataRepository: DataRepository<T>) {

    suspend fun getWeatherFromDB(): Flow<T> {
        return dataRepository.getWeatherInfo()
    }

    suspend fun getWeatherFromServer(location: Pair<Double, Double>) {
        return dataRepository.fetchFromServer(location)
    }
}