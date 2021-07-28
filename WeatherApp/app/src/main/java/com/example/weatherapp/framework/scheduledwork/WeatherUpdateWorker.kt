package com.example.weatherapp.framework.scheduledwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.interactor.GetLocationFromDB
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.framework.database.model.Weather

class WeatherUpdateWorker(
    appContext: Context, workerParams: WorkerParameters,
    private val getWeatherInfo: GetWeatherInfo<Weather>,
    private val getLocationFromDB: GetLocationFromDB<Weather>
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val locationFromDB = getLocationFromDB.getLocationFromDB()
        getWeatherInfo.getWeatherFromServer(locationFromDB)
        return Result.success()
    }

}