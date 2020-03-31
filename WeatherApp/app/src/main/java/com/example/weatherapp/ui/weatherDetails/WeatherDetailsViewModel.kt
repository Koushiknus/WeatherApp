package com.example.weatherapp.ui.weatherDetails

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.model.TodayWeatherCondition
import com.example.weatherapp.model.WeatherDetailsResponse
import com.example.weatherapp.network.ApiMethods
import com.example.weatherapp.ui.WeatherSearchRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherDetailsViewModel(application: Application) : BaseViewModel(application)  {

    var mWeatherDetailsResponse = MutableLiveData<WeatherDetailsResponse>()
    var mTodayWeatherCondition : TodayWeatherCondition = TodayWeatherCondition()

    @set: Inject
    var apiMethods: ApiMethods? = null

    @set:Inject
    var mWeatherSearchRepository: WeatherSearchRepository? = null

    fun getWeatherDetails(latLong : String,numberOfDays : String,tp : String){
        viewModelScope.launch {
            val result = mWeatherSearchRepository?.getWeatherDetails(latLong,numberOfDays,tp) as WeatherDetailsResponse
            mWeatherDetailsResponse.postValue(result)
        }
    }

}