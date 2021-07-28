package com.example.weatherapp

import com.example.core.domain.model.remote.Clouds
import com.example.core.domain.model.remote.WeatherDetail
import com.example.core.domain.model.remote.WeatherInfo
import com.example.core.domain.model.remote.Wind
import com.example.core.domain.model.remote.model.Coord
import com.example.core.domain.model.remote.model.Main
import com.example.core.domain.model.remote.model.Sys
import com.example.weatherapp.framework.WeatherDataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*

@ExperimentalCoroutinesApi
class WeatherDataMapperTest {

    lateinit var dataMapper: WeatherDataMapper

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        // Perform setup
        dataMapper = WeatherDataMapper()
    }

    @Test
    fun testMapper() = runBlocking {
        val weatherInfo = WeatherInfo(
            Coord(13.0059, 77.6466),
            listOf(
                WeatherDetail(
                    802, "Clouds", "scattered clouds", "03n"
                )
            ),
            "stations",
            Main(
                27.59, 28.59, 26.91,
                26.91,
                961,
                57
            ),
            8000,
            Wind(7.2, 260),
            Clouds(40),
            1627478763,
            Sys(
                1, 2950, "IN", 1627432437, 1627478259
            ),
            19800,
            1277333,
            "Bengaluru",
            200
        )

        val weather = dataMapper.invoke(weatherInfo)

        Assert.assertEquals(weatherInfo.name, weather.location)
        Assert.assertEquals(weatherInfo.weather[0].icon, weather.weatherIcon)
        Assert.assertEquals(weatherInfo.main.temp == weather.temperature, true)
        Assert.assertEquals(weatherInfo.main.tempMax == weather.temp_max, true)
        Assert.assertEquals(weatherInfo.main.tempMin == weather.temp_min, true)
        Assert.assertEquals(weatherInfo.main.feelsLike == weather.feels_like, true)
        Assert.assertEquals(weatherInfo.main.pressure, weather.pressure)
        Assert.assertEquals(weatherInfo.main.humidity, weather.humidity)
        Assert.assertEquals(weatherInfo.wind.speed == weather.wind, true)

    }

    @After
    fun tearDown() {
    }
}