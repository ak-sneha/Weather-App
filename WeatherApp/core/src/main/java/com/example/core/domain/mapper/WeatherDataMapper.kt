package com.example.core.domain.mapper

import com.example.core.domain.model.WeatherData
import com.example.core.domain.model.remote.WeatherInfo
import com.example.core.domain.model.remote.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class WeatherDataMapper : Mapper<WeatherInfo<Weather>, WeatherData> {

    private val tag = WeatherDataMapper::class.simpleName

    override suspend fun invoke(input: WeatherInfo<Weather>) = withContext(Dispatchers.Default) {
        return@withContext with(input) {
            Timber.tag(tag).e("inside WeatherDataMapper invoke")
            WeatherData(
                name,
                coord.lat,
                coord.lon,
                Date(),
                main.temp,
                main.tempMin,
                main.tempMax,
                wind.speed,
                main.pressure,
                main.humidity,
                Date(sys.sunrise.toLong()),
                Date(sys.sunset.toLong())
            )
        }
    }

}