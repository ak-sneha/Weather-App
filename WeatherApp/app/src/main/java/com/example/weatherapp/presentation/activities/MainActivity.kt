package com.example.weatherapp.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.core.domain.AppConstants
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.framework.database.model.Weather
import com.example.weatherapp.presentation.extensions.toast
import com.example.weatherapp.presentation.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivityMainBinding

    private var pattern = "dd LLL yyyy hh:mm a"
    private var formatter: SimpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())

    private var timePattern = "hh:mm a"
    private var timeFormatter: SimpleDateFormat = SimpleDateFormat(timePattern, Locale.getDefault())

    private val viewModel: MainViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.mainRoot)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        animationDrawable = binding.root.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.weatherData.observe(this, {
            it?.let {
                bindDataWithView(it)
            }
        })

        viewModel.requestLocationPermission.observe(this, {
            requestLocationPermission()
        })

        viewModel.requestTurnGpsOn.observe(this, {
            requestTurnOnGps()
        })

        viewModel.networkAvailable.observe(this, {
            toast(getString(R.string.network_error))
        })

        viewModel.location.observe(this, {
            it?.let {
                viewModel.getWeatherInfo(context = baseContext)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindDataWithView(weather: Weather) {
        val degree = 0x00B0.toChar()
        binding.tvLocation.text = weather.location
        binding.tvCurrentDatetime.text = formatter.format(Date())
        binding.tvTemperature.text = "${weather.temperature.toInt()}${degree}C"
        binding.tvFeelsLike.text =
            "${weather.temp_max.toInt()}${degree}C/${weather.temp_min.toInt()}${degree}C ${
                getString(
                    R.string.feels_like
                )
            } ${weather.feels_like.toInt()}${degree}C"
        binding.tvWeatherCondition.text = weather.weatherCondition
        binding.tvWind.text = "${weather.wind} ${getString(R.string.wind_unit)}"
        binding.tvHumidity.text = "${weather.humidity}%"
        binding.tvPressure.text = "${weather.pressure} ${getString(R.string.pressure_unit)}"
        binding.tvSunriseAt.text = timeFormatter.format(weather.sunrise)
        binding.tvSunsetAt.text = timeFormatter.format(weather.sunset)

        if (viewModel.isNetworkAvailable()) {
            weather.weatherIcon.let {
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it}@2x.png")
                    .into(binding.ivWeatherIcon)
            }
        } else {
            binding.ivWeatherIcon.visibility = View.GONE
            toast(getString(R.string.network_error))
        }
    }

    override fun onResume() {
        super.onResume()
        animationDrawable.start()
        viewModel.getCurrentLocation(context = baseContext)
    }

    private fun requestTurnOnGps() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(getString(R.string.turn_gps_on))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.navigate_to_settings)
            ) { _, _ ->
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(callGPSSettingIntent)
            }
        alertDialogBuilder.setNegativeButton(
            getString(android.R.string.cancel)
        ) { dialog, _ -> dialog.cancel() }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                AppConstants.locationPermission
            )
        ) {
            AlertDialog.Builder(this@MainActivity)
                .setMessage(getString(R.string.permission_request_rationale))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(AppConstants.locationPermission),
                        AppConstants.REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(AppConstants.locationPermission),
                AppConstants.REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstants.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getCurrentLocation(baseContext)
            } else {
                Timber.d(getString(R.string.location_permission_dentied))

                val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    AppConstants.locationPermission
                )

                if (isNeverAskAgain) {
                    Snackbar.make(
                        binding.mainRoot,
                        getString(R.string.location_permission_Required),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.settings)) {
                            Intent(
                                ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:${this@MainActivity.packageName}")
                            ).apply {
                                addCategory(Intent.CATEGORY_DEFAULT)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(this)
                            }
                        }
                        .show()
                } else {
                    Timber.d("Location permission denied")
                }
            }
        }
    }

}