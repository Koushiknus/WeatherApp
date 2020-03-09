package com.example.weatherapp.ui

import com.example.weatherapp.base.BaseRepository
import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.model.WeatherDetailsResponse
import com.example.weatherapp.network.ApiMethods
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherSearchRepository : BaseRepository() {

    @set: Inject
    var mApiMethods : ApiMethods? = null

    suspend fun getSearchResult(selectedCountry: String): SearchResponse?{
        return suspendCoroutine { continuation ->
              mApiMethods?.let {
                  mApiMethods?.getSearchResults(selectedCountry,"3")?.enqueue(object : Callback<SearchResponse>{
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

    suspend fun getWeatherDetails(latLong : String,numberOfDays : String,tp : String) : WeatherDetailsResponse?{
        return suspendCoroutine { continuation ->

            mApiMethods?.let {
                mApiMethods?.getWeatherDetails(latLong,numberOfDays,tp)?.enqueue(object : Callback<WeatherDetailsResponse>{
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