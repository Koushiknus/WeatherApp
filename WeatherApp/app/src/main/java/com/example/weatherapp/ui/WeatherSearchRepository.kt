package com.example.weatherapp.ui

import android.util.Log
import com.example.weatherapp.base.BaseRepository
import com.example.weatherapp.database.RecentLocationDAO
import com.example.weatherapp.database.RecentLocationDb
import com.example.weatherapp.model.RecentLocation
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
                          try{
                              val response = response.body() as SearchResponse
                              continuation.resume(response)
                          }catch (e: Exception){
                              continuation.resume(null)
                          }

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
                        try{
                            val response = response.body() as WeatherDetailsResponse
                            continuation.resume(response)
                        }catch (e: Exception){
                            e.printStackTrace()
                            continuation.resume(null)
                        }

                    }
                })
            }

        }
    }

    suspend fun insertRecentLocations(dao : RecentLocationDAO, recentLocation: RecentLocation){
        Log.v("InsideInsert","InsideInsert")
       val test =  dao.insertRecentLocations(recentLocation)
        Log.v("InsertStatus",test.toString())
    }

    suspend fun getAllLocation(dao : RecentLocationDAO): List<RecentLocation> {
        return dao.loadAllRecentLocation()
    }

    suspend fun updateAllRecentLocation(dao : RecentLocationDAO, recentLocation: RecentLocation){
      val result =   dao.updateRecentLocation(recentLocation)
        Log.v("UpdateResultIs",result.toString())
    }


}