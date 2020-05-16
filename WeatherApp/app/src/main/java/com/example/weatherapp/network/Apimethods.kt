package com.example.weatherapp.network

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.model.WeatherDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMethods {

    @GET("search.ashx?"+BuildConfig.FORMAT_KEY)
    fun getSearchResults(@Query("query")query : String,
                         @Query("num_of_results")num_of_results : String
                            ) : Call<SearchResponse>

    @GET("weather.ashx?"+BuildConfig.FORMAT_KEY)
    fun getWeatherDetails(@Query("q")q : String,
                          @Query("num_of_days")num_of_days : String,
                          @Query("tp")tp : String) : Call<WeatherDetailsResponse>
}