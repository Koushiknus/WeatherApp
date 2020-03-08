package com.example.weatherapp.ui.weatherDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.model.WeatherDetailsResponse
import com.example.weatherapp.network.ApiMethods
import com.example.weatherapp.ui.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherDetailsViewModel : BaseViewModel()  {

    var mWeatherDetailsResponse = MutableLiveData<WeatherDetailsResponse>()

    @set: Inject
    var apiMethods: ApiMethods? = null

    @set:Inject
    var mWeatherRepository: WeatherRepository? = null

    fun getWeatherDetails(latLong : String,numberOfDays : String,tp : String){
        viewModelScope.launch {
            val result = mWeatherRepository?.getWeatherDetails(latLong,numberOfDays,tp) as WeatherDetailsResponse
            mWeatherDetailsResponse.postValue(result)
        }
    }

}