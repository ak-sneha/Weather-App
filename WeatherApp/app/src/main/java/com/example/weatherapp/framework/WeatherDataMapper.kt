package com.example.weatherapp.framework

import com.example.core.domain.mapper.Mapper
import com.example.core.domain.model.WeatherData
import com.example.core.domain.model.remote.WeatherInfo
import com.example.core.domain.model.remote.WeatherDetail
import com.example.weatherapp.framework.database.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class WeatherDataMapper : Mapper<WeatherInfo<WeatherDetail>, Weather> {

    private val tag = WeatherDataMapper::class.simpleName

    override suspend fun invoke(input: WeatherInfo<WeatherDetail>) = withContext(Dispatchers.Default) {
        return@withContext with(input) {
            Timber.tag(tag).e("inside WeatherDataMapper invoke")
            Weather(0,
                name,
                coord.lat,
                coord.lon,
                Date(),
                main.temp,
                main.tempMin,
                main.tempMax,
                main.feelsLike,
                weather[0].main,
                wind.speed,
                main.pressure,
                main.humidity,
                Date(sys.sunrise.toLong()),
                Date(sys.sunset.toLong()),
                weather[0].icon
            )
        }
    }

}