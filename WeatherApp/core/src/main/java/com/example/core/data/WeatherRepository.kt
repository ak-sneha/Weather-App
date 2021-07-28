package com.example.core.data

import com.example.core.domain.AppConstants
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class WeatherRepository<T> @Inject constructor(private val dataSource: DataSource<T>): DataRepository<T> {

    private val tag = WeatherRepository::class.simpleName

    override suspend fun getWeatherInfo(): Flow<T> = dataSource.getWeatherData()

    override suspend fun fetchFromServer(location: Pair<Double, Double>) {
        Timber.tag(tag).e("inside fetchFromServer")

        val queryParams = mutableMapOf<String, String>().also {
            it[AppConstants.KEY_LATITUDE] = location.first.toString()
            it[AppConstants.KEY_LONGITUDE] = location.second.toString()
            it[AppConstants.KEY_UNITS] = AppConstants.UNITS
            it[AppConstants.KEY_APPID] = dataSource.getApiKey()
        }

        dataSource.fetchFromServer(queryParams)
    }

    override suspend fun getLocationFromDB() = dataSource.getLocationFromDB()
}