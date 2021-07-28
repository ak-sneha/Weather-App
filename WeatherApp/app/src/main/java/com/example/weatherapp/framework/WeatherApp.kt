package com.example.weatherapp.framework

import android.app.Application
import androidx.viewbinding.BuildConfig
import androidx.work.Configuration
import com.example.weatherapp.framework.database.model.Weather
import com.example.weatherapp.framework.scheduledwork.WeatherWorkerFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp: Application(), Configuration.Provider{

    @Inject
    lateinit var myWorkerFactory: WeatherWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(myWorkerFactory)
            .build()
}