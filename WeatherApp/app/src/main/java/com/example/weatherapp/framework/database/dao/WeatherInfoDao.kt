package com.example.weatherapp.framework.database.dao

import androidx.room.*
import com.example.weatherapp.framework.database.model.Weather
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherInfoDao {

    @Query("SELECT * FROM weather where id = 0")
    fun getWeather(): Flow<Weather>

    @Query("SELECT * FROM weather where id = 0")
    fun getLocation(): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)

}
