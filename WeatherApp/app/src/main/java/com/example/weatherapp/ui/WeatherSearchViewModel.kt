package com.example.weatherapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.database.RecentLocationDAO
import com.example.weatherapp.database.RecentLocationDb
import com.example.weatherapp.model.RecentLocation
import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.network.ApiMethods
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class WeatherSearchViewModel(application: Application) : BaseViewModel(application) {
    @set: Inject
    var apiMethods: ApiMethods? = null

     var mRecentLocationDAO: RecentLocationDAO ? = null

    @set:Inject
    var mWeatherSearchRepository: WeatherSearchRepository? = null
    var mSearchResponse = MutableLiveData<SearchResponse?>()
    var mListOfRecentLocation = MutableLiveData<List<RecentLocation>>()
    var mSaveUserFirstTime = true

    init {
        mRecentLocationDAO = RecentLocationDb.getDatabase(application).getRecentLocation()
    }

    fun searchWeatherByCountry(selectedCountry: String) {
        viewModelScope.launch {
           val result = mWeatherSearchRepository?.getSearchResult(selectedCountry)
            if(result!=null){
                mSearchResponse.postValue(result)
            }else{
                mSearchResponse.postValue(null)
            }
        }

    }

    fun insertRecentLocationstoDb(recentLocation: RecentLocation){
        try{
            viewModelScope.launch {
                mWeatherSearchRepository?.insertRecentLocations(mRecentLocationDAO!!,recentLocation)
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun updateRecentLocationsToDb(recentLocation: RecentLocation){
        viewModelScope.launch {
            mRecentLocationDAO?.let { mWeatherSearchRepository?.updateAllRecentLocation(it,recentLocation) }
        }
    }

    fun getAllRecentLocation(){
        viewModelScope.launch {
            val result = (mWeatherSearchRepository?.getAllLocation(mRecentLocationDAO!!))
            mListOfRecentLocation.postValue(result)
        }
    }
}