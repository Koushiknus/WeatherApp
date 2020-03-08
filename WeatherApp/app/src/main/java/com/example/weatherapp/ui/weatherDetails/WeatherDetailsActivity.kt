package com.example.weatherapp.ui.weatherDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityWeatherDetailsBinding

class WeatherDetailsActivity : AppCompatActivity() {

    private lateinit var mWeatherDetailsActivity: WeatherDetailsActivity
    private lateinit var mWeatherDetailBinding  : ActivityWeatherDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

    }
}
