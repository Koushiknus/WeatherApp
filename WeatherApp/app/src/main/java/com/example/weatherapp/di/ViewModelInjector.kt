package com.example.weatherapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), RepositoryModule::class])
interface ViewModelInjector {

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun appModule(appModule: AppModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
    }
}