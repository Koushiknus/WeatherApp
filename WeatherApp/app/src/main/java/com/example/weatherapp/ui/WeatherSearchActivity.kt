package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.base.ViewModelFactory

class WeatherSearchActivity : AppCompatActivity() {

    private var mSearchView: SearchView? = null


    private lateinit var mWeatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        // Get the support action bar
        val actionBar = supportActionBar

        // Set the action bar title, subtitle and elevation
        actionBar?.title = getString(R.string.welcome)
        actionBar?.subtitle = getString(R.string.search_places)
        actionBar?.elevation = 4.0F
        mWeatherViewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(WeatherViewModel::class.java)
        mWeatherViewModel.testSearchResults()
        mWeatherViewModel.testGetWeatherDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_country, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
