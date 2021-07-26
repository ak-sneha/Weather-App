package com.example.weatherapp.framework.remote

import com.example.core.domain.model.remote.Weather
import com.example.core.domain.model.remote.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RemoteService {

    @GET("weather")
    suspend fun getWeatherInfoAsync(@QueryMap params: Map<String, String>): Call<WeatherInfo<Weather>>

}