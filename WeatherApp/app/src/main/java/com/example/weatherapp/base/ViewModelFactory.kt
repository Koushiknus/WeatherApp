package com.example.weatherapp.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.WeatherSearchViewModel
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsViewModel
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory(context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherSearchViewModel::class.java))
            return WeatherSearchViewModel() as T
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java))
            return WeatherDetailsViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}