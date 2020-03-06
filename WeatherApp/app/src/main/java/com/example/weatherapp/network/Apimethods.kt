package com.example.weatherapp.network

import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.model.WeatherDetailsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiMethods {

    @GET("search.ashx?query=London&num_of_results=3&format=json&key=e35d1e43b44e4d698da30324202302")
    fun getSearchResults() : Call<SearchResponse>

    @GET("weather.ashx?key=e35d1e43b44e4d698da30324202302&q=48.85,2.35&num_of_days=2&tp=3&format=json")
    fun getWeatherDetails() : Call<WeatherDetailsResponse>
}