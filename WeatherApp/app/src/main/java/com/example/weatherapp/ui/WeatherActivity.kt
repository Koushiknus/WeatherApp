package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.base.ViewModelFactory

class WeatherActivity : AppCompatActivity() {

    private lateinit var mWeatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        mWeatherViewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(WeatherViewModel::class.java)
        mWeatherViewModel.testSearchResults()
        mWeatherViewModel.testGetWeatherDetails()
    }
}
