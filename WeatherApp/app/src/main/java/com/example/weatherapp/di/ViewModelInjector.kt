package com.example.weatherapp.di

import com.example.weatherapp.ui.WeatherSearchViewModel
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), RepositoryModule::class])
interface ViewModelInjector {

    fun inject(weatherSearchViewModel: WeatherSearchViewModel)
    fun inject(weatherDetailsViewModel: WeatherDetailsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun appModule(appModule: AppModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
    }
}