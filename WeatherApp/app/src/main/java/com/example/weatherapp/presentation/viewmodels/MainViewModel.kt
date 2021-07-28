package com.example.weatherapp.presentation.viewmodels

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.core.domain.AppConstants
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.framework.database.model.Weather
import com.example.weatherapp.framework.remote.NetworkHelper
import com.example.weatherapp.framework.scheduledwork.WeatherUpdateWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val getWeatherInfo: GetWeatherInfo<Weather>
) : ViewModel() {

    private val tag = MainViewModel::class.simpleName

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private val _mutableWeatherData = MutableLiveData<Weather>()
    val weatherData: LiveData<Weather> get() = _mutableWeatherData

    val requestLocationPermission = MutableLiveData<Boolean>()
    val requestTurnGpsOn = MutableLiveData<Boolean>()
    val networkAvailable = MutableLiveData<Boolean>()

    private val currentLocation = MutableLiveData<Pair<Double, Double>>()
    val location: LiveData<Pair<Double, Double>> get() = currentLocation

    fun getCurrentLocation(context: Context) {
        println(checkLocationPermission(context))
        if (checkLocationPermission(context)) {

            if (isGpsOn(context)) {
                // get current location
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                mFusedLocationClient?.let { locationProviderClient ->
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                            location?.let {
                                Pair(location.latitude, location.longitude).also {
                                    this.currentLocation.value = it
                                }
                            }
                        }
                    }
                }
            } else {
                // turn on GPS
                requestTurnGpsOn.value = true
                return
            }
        } else {
            // get permission
            requestLocationPermission.value = true
            return
        }
    }

    fun getWeatherInfo(context: Context) {
        Timber.d(tag, "Inside getWeatherInfo")
        viewModelScope.launch {
            var weather: Flow<Weather>
            withContext(Dispatchers.IO) {
                weather = getWeatherInfo.getWeatherFromDB()

            }
            weather.collect {
                _mutableWeatherData.value = it
                println(it)
                if (_mutableWeatherData.value == null || Date().time - (_mutableWeatherData.value!!.lastUpdateDate.time) > (2 * 60 * 1000)) {
                    if (networkHelper.isNetworkConnected()) {
                        withContext(Dispatchers.IO) {
                            location.value?.let { pair ->
                                getWeatherInfo.getWeatherFromServer(
                                    location = pair
                                )
                            }
                        }
                    } else {
                        networkAvailable.value = false
                    }
                }
            }


        }
    }

    private fun startWorkManager(context: Context) {
        Timber.d(tag, "Inside startWorkManager")
        WorkManager.getInstance(context).cancelAllWork()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val updateWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<WeatherUpdateWorker>(2, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(context).enqueue(updateWorkRequest)
    }

    private fun checkLocationPermission(context: Context): Boolean {
        Timber.d(tag, "Inside checkLocationPermission")
        return (ContextCompat.checkSelfPermission(
            context,
            AppConstants.locationPermission
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun isGpsOn(context: Context): Boolean {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    fun isNetworkAvailable(): Boolean {
        return networkHelper.isNetworkConnected()
    }
}

