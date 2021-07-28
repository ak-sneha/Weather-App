package com.example.core.data

import kotlinx.coroutines.flow.Flow

interface DataRepository<T> {

    suspend fun getWeatherInfo(): Flow<T>

    suspend fun fetchFromServer(location: Pair<Double, Double>)

    suspend fun getLocationFromDB(): Pair<Double, Double>
}