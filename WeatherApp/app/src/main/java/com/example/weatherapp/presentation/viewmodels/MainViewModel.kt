package com.example.weatherapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.DataRepository
import com.example.core.data.DataSource
import com.example.core.data.WeatherRepository
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.framework.remote.RemoteDataSource
import com.example.weatherapp.framework.remote.RetrofitClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel() {

    fun getWeatherInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                GetWeatherInfo(dataRepository = repository).getWeatherData()
            }
        }
    }
}
