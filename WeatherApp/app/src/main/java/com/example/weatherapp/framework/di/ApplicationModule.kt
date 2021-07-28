package com.example.weatherapp.framework.di

import android.content.Context
import com.example.core.data.DataRepository
import com.example.core.data.DataSource
import com.example.core.data.WeatherRepository
import com.example.core.interactor.GetLocationFromDB
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.framework.database.DatabaseBinder
import com.example.weatherapp.framework.database.WeatherDatabase
import com.example.weatherapp.framework.database.dao.WeatherInfoDao
import com.example.weatherapp.framework.database.model.Weather
import com.example.weatherapp.framework.remote.NetworkHelper
import com.example.weatherapp.framework.remote.WeatherDataSource
import com.example.weatherapp.framework.remote.RetrofitClient
import com.example.weatherapp.framework.scheduledwork.WeatherWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideDataRepository(dataSource: DataSource<Weather>) : DataRepository<Weather> {
        return WeatherRepository(dataSource)
    }

    @Provides
    fun provideDataSource(dao: WeatherInfoDao, retrofitClient: RetrofitClient) : DataSource<Weather> {
        return WeatherDataSource(dao, retrofitClient)
    }

    @Provides
    fun provideDao(db: WeatherDatabase) = db.weatherInfoDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = DatabaseBinder.getInstance(context)

    @Provides
    fun provideRetrofitClient() = RetrofitClient()

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)

    @Provides
    fun provideGetWeatherInfo(repository: WeatherRepository<Weather>) = GetWeatherInfo(repository)

    @Provides
    fun provideGetLocationFromDB(repository: WeatherRepository<Weather>) = GetLocationFromDB(repository)

//    @Provides
//    fun provideWeatherWorkerFactory(getWeatherInfo: GetWeatherInfo<Weather>,
//                                    getLocationFromDB: GetLocationFromDB<Weather>) = WeatherWorkerFactory(getWeatherInfo, getLocationFromDB)
}