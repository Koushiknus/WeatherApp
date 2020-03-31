package com.example.weatherapp.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.WeatherSearchViewModel
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsViewModel
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory(context: Application) : ViewModelProvider.Factory {

    private val mContext = context

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherSearchViewModel::class.java))
            return WeatherSearchViewModel(mContext) as T
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java))
            return WeatherDetailsViewModel(mContext) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}