package com.example.weatherapp.ui.weatherDetails

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseActivity
import com.example.weatherapp.base.Constants
import com.example.weatherapp.base.ViewModelFactory
import com.example.weatherapp.databinding.ActivityWeatherDetailsBinding
import kotlinx.android.synthetic.main.layout_progressbar.*


class WeatherDetailsActivity : BaseActivity() {

    private lateinit var mWeatherDetailViewModel : WeatherDetailsViewModel
    private lateinit var mWeatherDetailBinding  : ActivityWeatherDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWeatherDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_weather_details)
        initialData()
        initialObservers()
    }

    private fun initialData(){
        mWeatherDetailViewModel = ViewModelProviders.of(this,ViewModelFactory(application)).get(WeatherDetailsViewModel::class.java)
        showOrHideProgress(View.VISIBLE)
        mWeatherDetailViewModel.getWeatherDetails(intent.getStringExtra(Constants.LATLONG),Constants.NUMBER_OF_DAYS,Constants.DURATION_PERIOD)
    }
    private fun initialObservers(){
        mWeatherDetailViewModel.mWeatherDetailsResponse.observe(this, Observer {currentWeatherDetails ->
            showOrHideProgress(View.GONE)
            currentWeatherDetails?.let {
                val current_condition = currentWeatherDetails.data.current_condition.get(0)
                val todayWeatherCondition = mWeatherDetailViewModel.mTodayWeatherCondition
                todayWeatherCondition.humidity = getString(R.string.humidity)+ current_condition.humidity.toString()
                todayWeatherCondition.temparatureStatus =current_condition.temp_C.toString() + " C " + current_condition.temp_F.toString() + " F"

                todayWeatherCondition.weatherImage = current_condition.weatherIconUrl.get(0).value
                todayWeatherCondition.weatherDescription = current_condition.weatherDesc.get(0).value
                Glide.with(this)
                    .load(current_condition.weatherIconUrl.get(0).value )
                    .placeholder(
                        ColorDrawable(
                            ContextCompat.getColor(
                                this,
                                R.color.accent_material_light
                            )
                        )
                    )
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(mWeatherDetailBinding.todayIcon)
                mWeatherDetailBinding.weatherDetails = todayWeatherCondition
            } ?: kotlin.run {
                showErrorDialog()
            }


        })
    }
    private fun showOrHideProgress(visibilty : Int){
        progressBar.visibility = visibilty
    }
}
