package com.example.core.data

import com.example.core.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface DataSource<T> {

    fun getApiKey() : String

//    queryParams: Map<String, String>
    suspend fun getWeatherData(): Flow<T>

    suspend fun fetchFromServer(queryParam: Map<String, String>)

    suspend fun getLocationFromDB(): Pair<Double, Double>

}