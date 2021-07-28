package com.example.weatherapp.framework.database

import android.content.Context
import androidx.room.Room

object DatabaseBinder {

    private var INSTANCE: WeatherDatabase? = null

    fun getInstance(context: Context): WeatherDatabase {
        if (INSTANCE == null) {
            synchronized(WeatherDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weatherapp-example-coroutines"
        ).build()
}