package com.example.weatherapp.framework.remote

import com.example.core.domain.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


class RetrofitClient @Inject constructor() {

    private var remoteService: RemoteService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(AppConstants.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        remoteService = retrofit.create(RemoteService::class.java)
    }

    fun  getRemoteService(): RemoteService {
        return remoteService
    }
}