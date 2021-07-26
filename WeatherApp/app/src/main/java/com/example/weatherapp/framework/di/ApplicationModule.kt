package com.example.weatherapp.framework.di

import com.example.core.data.DataRepository
import com.example.core.data.DataSource
import com.example.core.data.WeatherRepository
import com.example.weatherapp.framework.remote.RemoteDataSource
import com.example.weatherapp.framework.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(dagger.hilt.android.components.ViewModelComponent::class)
object ApplicationModule {

    @Provides
    fun provideDataRepository(dataSource: DataSource) : DataRepository {
        return WeatherRepository(dataSource)
    }

    @Provides
    fun provideDataSource(retrofitClient: RetrofitClient) : DataSource {
        return RemoteDataSource(retrofitClient)
    }

    @Provides
    fun provideRetrofitClient() = RetrofitClient()
}