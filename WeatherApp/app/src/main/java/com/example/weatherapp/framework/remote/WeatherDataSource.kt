package com.example.weatherapp.framework.remote

import com.example.core.data.DataSource
import com.example.weatherapp.framework.WeatherDataMapper
import com.example.weatherapp.framework.database.dao.WeatherInfoDao
import com.example.weatherapp.framework.database.model.Weather
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class WeatherDataSource @Inject constructor(
    private val dao: WeatherInfoDao,
    private val remoteClient: RetrofitClient
) :
    DataSource<Weather> {

    private val tag = WeatherDataSource::class.simpleName

    //TODO Save API key to NDK or C++ file for security
    override fun getApiKey() = "d7faface0ad1746878e2528bcb324490"

    override suspend fun getWeatherData() = dao.getWeather()

    override suspend fun fetchFromServer(queryParam: Map<String, String>) {
        Timber.tag(tag).e("inside fetchFromServer")
        val remoteService = remoteClient.getRemoteService()
        return remoteService.getWeatherInfoAsync(queryParam).let {
            println(it)
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            println(simpleDateFormat.format(Date().time + it.sys.sunrise.toLong()))
            println(Date(it.sys.sunset.toLong()))
            val weather = WeatherDataMapper().invoke(it)
            dao.insert(weather)

        }
    }

    override suspend fun getLocationFromDB(): Pair<Double, Double> {
        val location = dao.getLocation()
        return Pair(location.latitude, location.longitude)
    }
}

