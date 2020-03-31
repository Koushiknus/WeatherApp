package com.example.weatherapp.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.weatherapp.di.AppModule
import com.example.weatherapp.di.DaggerViewModelInjector
import com.example.weatherapp.di.RepositoryModule
import com.example.weatherapp.di.ViewModelInjector
import com.example.weatherapp.ui.WeatherSearchViewModel
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

   private val injector : ViewModelInjector = DaggerViewModelInjector
        .builder()
        .appModule(AppModule)
        .repositoryModule(RepositoryModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when (this) {
           is WeatherSearchViewModel -> injector.inject(this)
           is WeatherDetailsViewModel -> injector.inject(this)
        }
    }
}