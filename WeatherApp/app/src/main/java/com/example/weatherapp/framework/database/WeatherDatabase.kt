package com.example.weatherapp.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.framework.database.dao.WeatherInfoDao
import com.example.weatherapp.framework.database.model.Weather

@Database(entities = [Weather::class], version = 3)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherInfoDao(): WeatherInfoDao
}