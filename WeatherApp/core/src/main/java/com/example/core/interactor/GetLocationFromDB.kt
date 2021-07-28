package com.example.core.interactor

import com.example.core.data.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationFromDB<T> @Inject constructor(private val dataRepository: DataRepository<T>)  {

    suspend fun getLocationFromDB(): Pair<Double, Double> {
        return dataRepository.getLocationFromDB()
    }
}