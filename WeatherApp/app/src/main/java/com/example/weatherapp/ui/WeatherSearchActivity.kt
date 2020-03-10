package com.example.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.base.Constants
import com.example.weatherapp.base.ViewModelFactory
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsActivity
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherSearchActivity : AppCompatActivity() {

    private lateinit var mWeatherSearchViewModel: WeatherSearchViewModel
    private lateinit var mRecentLocationSearchAdapter: RecentLocationSearchAdapter

    var mRecentLocationsList = LinkedHashSet<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        mRecentLocationSearchAdapter = RecentLocationSearchAdapter()
        recyclerview_recent_search.setHasFixedSize(false)
        recyclerview_recent_search.layoutManager = LinearLayoutManager(this)
        recyclerview_recent_search.addItemDecoration(
            DividerItemDecoration(
                recyclerview_recent_search.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerview_recent_search.adapter = mRecentLocationSearchAdapter
        // Get the support action bar
        val actionBar = supportActionBar

        // Set the action bar title, subtitle and elevation
        actionBar?.title = getString(R.string.welcome)
        actionBar?.subtitle = getString(R.string.search_places)
        actionBar?.elevation = 4.0F
        mWeatherSearchViewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(WeatherSearchViewModel::class.java)
        initialObservers()
        initialData()

    }


    private fun initialData(){
        val arraySpinner = arrayOf(Constants.SELECT_LOCATION,"Singapore","India") // test data
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item, arraySpinner
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        country_list.adapter = adapter
        country_list.setTitle(getString(R.string.select_country))
        country_list.setPositiveButton(getString(R.string.ok))

        country_list?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = parent?.getItemAtPosition(position).toString()
                if(!selectedCountry.equals(Constants.SELECT_LOCATION)){
                    mRecentLocationsList.add(selectedCountry)
                    mWeatherSearchViewModel.testSearchResults(selectedCountry)
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        mRecentLocationSearchAdapter.setData(mRecentLocationsList)
        recyclerview_recent_search.adapter?.notifyDataSetChanged()
        Log.v("LinkedHashSetContents",mRecentLocationsList.size.toString())
    }

    private fun initialObservers(){
        mWeatherSearchViewModel.mSearchResponse.observe(this, Observer {
            Log.v("Lat Long Received",it.search_api.result.get(0).latitude.toString()+","+it.search_api.result.get(0).longitude.toString())
            val latLong = it.search_api.result.get(0).latitude.toString()+","+it.search_api.result.get(0).longitude.toString()
            Intent(this, WeatherDetailsActivity::class.java).apply {
                putExtra(Constants.LATLONG,latLong)
                startActivity(this)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_country, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
