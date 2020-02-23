package com.example.weatherapp.base

import com.example.weatherapp.di.AppModule
import com.example.weatherapp.di.DaggerRepositoryInjector
import com.example.weatherapp.di.RepositoryInjector
import com.example.weatherapp.di.RepositoryModule
import com.example.weatherapp.ui.WeatherRepository

abstract class BaseRepository {

    private val injector : RepositoryInjector = DaggerRepositoryInjector
        .builder()
        .appModule(AppModule)
        .repositoryModule(RepositoryModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){
            is WeatherRepository -> injector.inject(this)
        }
    }
}