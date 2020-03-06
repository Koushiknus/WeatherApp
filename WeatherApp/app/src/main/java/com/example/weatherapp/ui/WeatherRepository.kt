package com.example.weatherapp.ui

import android.telecom.Call
import com.example.weatherapp.base.BaseRepository
import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.model.WeatherDetailsResponse
import com.example.weatherapp.network.ApiMethods
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepository : BaseRepository() {

    @set: Inject
    var mApiMethods : ApiMethods? = null

    suspend fun getSearchResult() : SearchResponse?{
        return suspendCoroutine { continuation ->
              mApiMethods?.let {
                  mApiMethods?.getSearchResults()?.enqueue(object : Callback<SearchResponse>{
                      override fun onFailure(call: retrofit2.Call<SearchResponse>, t: Throwable) {
                      }

                      override fun onResponse(
                          call: retrofit2.Call<SearchResponse>,
                          response: Response<SearchResponse>
                      ) {
                          val response = response.body() as SearchResponse
                          continuation.resume(response)
                      }
                  })
            }
        }
    }

    suspend fun getWeatherDetails() : WeatherDetailsResponse?{
        return suspendCoroutine { continuation ->

            mApiMethods?.let {
                mApiMethods?.getWeatherDetails()?.enqueue(object : Callback<WeatherDetailsResponse>{
                    override fun onFailure(
                        call: retrofit2.Call<WeatherDetailsResponse>,
                        t: Throwable
                    ) {
                    }

                    override fun onResponse(
                        call: retrofit2.Call<WeatherDetailsResponse>,
                        response: Response<WeatherDetailsResponse>
                    ) {
                        val response = response.body() as WeatherDetailsResponse
                        continuation.resume(response)
                    }
                })
            }

        }
    }
}