package com.example.weatherapp.di

import com.example.weatherapp.ui.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object RepositoryModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWeatherRepository() : WeatherRepository{
        return WeatherRepository()
    }
}