package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.base.Constants
import com.example.weatherapp.database.RecentLocationDb
import com.example.weatherapp.network.ApiMethods
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
 object AppModule {

    @Provides
    @JvmStatic
    @Reusable
    internal fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(OkHttpProfilerInterceptor())

        return builder.build()
    }

    @Reusable
    @Provides
    @JvmStatic
    internal fun provideRetrofitInstance( okHttpClient : OkHttpClient): ApiMethods {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiMethods::class.java)
    }

}