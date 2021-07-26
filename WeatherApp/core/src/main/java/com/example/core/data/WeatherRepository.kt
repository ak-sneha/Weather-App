package com.example.core.data

import com.example.core.domain.AppConstants
import com.example.core.domain.model.WeatherData
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val dataSource: DataSource): DataRepository {

    private val tag = WeatherRepository::class.simpleName

    override suspend fun getWeatherInfo(): WeatherData {

        Timber.tag(tag).e("inside getWeatherInfo")

        val queryParams = mutableMapOf<String, String>().also {
            val (latitude, longitude) = dataSource.getCurrentLocation()
            it[AppConstants.KEY_LATITUDE] = latitude
            it[AppConstants.KEY_LONGITUDE] = longitude
            it[AppConstants.KEY_UNITS] = AppConstants.UNITS
            it[AppConstants.KEY_APPID] = dataSource.getApiKey()
        }

        return dataSource.getWeatherData(queryParams)
    }
}