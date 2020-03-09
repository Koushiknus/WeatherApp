package com.example.weatherapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.model.SearchResponse
import com.example.weatherapp.network.ApiMethods
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherSearchViewModel : BaseViewModel() {
    @set: Inject
    var apiMethods: ApiMethods? = null

    @set:Inject
    var mWeatherSearchRepository: WeatherSearchRepository? = null
    var mSearchResponse = MutableLiveData<SearchResponse>()
    var mOnLoadOfScreenFirstTime = true

    fun testSearchResults(selectedCountry: String) {
        viewModelScope.launch {
           val result = mWeatherSearchRepository?.getSearchResult(selectedCountry) as SearchResponse
            mSearchResponse.postValue(result)
           Log.v("ResultReceived",result.toString())
        }

    }
}