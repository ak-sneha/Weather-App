package com.example.weatherapp.framework.remote

import com.example.core.data.DataSource
import com.example.core.domain.mapper.WeatherDataMapper
import com.example.core.domain.model.WeatherData
import timber.log.Timber
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val retrofitClient: RetrofitClient) :
    DataSource {

    private val tag = RemoteDataSource::class.simpleName

    override fun getApiKey() = "7a832adfd82a10ac952e8ad31e2da9ff"

    override suspend fun getWeatherData(queryParams: Map<String, String>): WeatherData {
        Timber.tag(tag).e("inside getWeatherData")

        val remoteService = retrofitClient.getRemoteService()
        return remoteService.getWeatherInfoAsync(queryParams).let {
            println(it)
            WeatherDataMapper().invoke(it)
        }
    }

    override fun getCurrentLocation(): Pair<String, String> {
        return Pair("13.0060", "77.6469")
    }
}

