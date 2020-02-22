package com.example.weatherapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), RepositoryModule::class])
interface RepositoryInjector {
    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector
        fun appModule(appModule: AppModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
    }
}