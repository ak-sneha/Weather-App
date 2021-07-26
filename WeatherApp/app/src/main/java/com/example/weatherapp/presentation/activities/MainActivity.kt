package com.example.weatherapp.presentation.activities

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.core.interactor.GetWeatherInfo
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.framework.remote.RemoteDataSource
import com.example.weatherapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.mainRoot)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        animationDrawable = binding.root.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)
    }

    override fun onResume() {
        super.onResume()
        animationDrawable.start()
        viewModel.getWeatherInfo()
    }
}