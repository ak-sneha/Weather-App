package com.example.weatherapp.framework.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.framework.database.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class Weather(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "lastUpdateDate") val lastUpdateDate: Date,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "temp_min") val temp_min: Double,
    @ColumnInfo(name = "temp_max") val temp_max: Double,
    @ColumnInfo(name = "feels_like") val feels_like: Double,
    @ColumnInfo(name = "weather_condition") val weatherCondition: String,
    @ColumnInfo(name = "wind") val wind: Double,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "sunrise") val sunrise: Date,
    @ColumnInfo(name = "sunset") val sunset: Date,
    @ColumnInfo(name = "weather_icon") val weatherIcon: String
)
