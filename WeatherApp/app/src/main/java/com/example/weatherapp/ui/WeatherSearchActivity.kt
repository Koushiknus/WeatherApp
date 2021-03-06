package com.example.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseActivity
import com.example.weatherapp.base.Constants
import com.example.weatherapp.base.ViewModelFactory
import com.example.weatherapp.model.RecentLocation
import com.example.weatherapp.ui.weatherDetails.WeatherDetailsActivity
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet


class WeatherSearchActivity : BaseActivity() {

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
        mWeatherSearchViewModel = ViewModelProviders.of(this,ViewModelFactory(application)).get(WeatherSearchViewModel::class.java)
        initialObservers()
        mWeatherSearchViewModel.getAllRecentLocation()
        initialData()
        fetchLocalDataFromDb()

    }

    private fun fetchLocalDataFromDb(){
        mWeatherSearchViewModel.getAllRecentLocation()
    }


    private fun initialData(){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item, Constants.COUNTRY_ARRAY
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        country_list.adapter = adapter
        country_list.setTitle(getString(R.string.select_country))
        country_list.setPositiveButton(getString(R.string.ok))
        country_list.post(Runnable {
            country_list.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) { // Only called when the user changes the selection

                    val selectedCountry = parent?.getItemAtPosition(position).toString()
                    Log.v("OnItemSelected",selectedCountry)
                    loadSearchAPI(selectedCountry)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        })
    }

    private fun loadSearchAPI(selectedCountry : String){
        if(!selectedCountry.equals(Constants.SELECT_LOCATION)){
            if(mRecentLocationsList.contains(selectedCountry)){
                mRecentLocationsList.remove(selectedCountry)
            }
            showNoSearchHistory()
            mRecentLocationsList.add(selectedCountry)
            mRecentLocationSearchAdapter.setData(mRecentLocationsList)
            mWeatherSearchViewModel.searchWeatherByCountry(selectedCountry)

        }
    }

    override fun onResume() {
        super.onResume()
        mRecentLocationSearchAdapter.setData(mRecentLocationsList)
        recyclerview_recent_search.adapter?.notifyDataSetChanged()

       // mWeatherSearchViewModel.getAllRecentLocation()
    }

    private fun initialObservers(){
        mWeatherSearchViewModel.mSearchResponse.observe(this, Observer {
            Log.v("Lat Long Received",it?.search_api?.result?.get(0)?.latitude.toString()+","+it?.search_api?.result?.get(0)?.longitude.toString())
            val searchResponse = it?.search_api?.result?.get(0)
            val latitude = searchResponse?.latitude
            val longitude = searchResponse?.longitude
            if(latitude!=null && longitude!=null){
                val latLong = it?.search_api?.result?.get(0)?.latitude.toString()+","+it?.search_api?.result?.get(0)?.longitude.toString()
                latLong.let {
                    Intent(this, WeatherDetailsActivity::class.java).apply {
                        putExtra(Constants.LATLONG,it)
                        startActivity(this)
                        saveRecentDataToDb()
                    }
                }
            }else{
                showErrorDialog()
            }
        })
        mWeatherSearchViewModel.mListOfRecentLocation.observe(this, Observer {
            Log.v("RecentLocations",it.toString())
            mRecentLocationsList.clear()
            if(it.isNotEmpty()){
                mRecentLocationsList.addAll(it.get(0).mRecentLocations)
                lbl_no_recent_search.visibility = View.GONE
                lbl_recent_search.visibility = View.VISIBLE

            }else{
                lbl_no_recent_search.visibility = View.VISIBLE
                lbl_recent_search.visibility = View.GONE
            }
            mRecentLocationSearchAdapter.setData(mRecentLocationsList)
            recyclerview_recent_search.adapter?.notifyDataSetChanged()
        })

        mRecentLocationSearchAdapter.mChoosenLocation.observe(this, Observer {
            loadSearchAPI(it)
        })
    }

    private fun showNoSearchHistory(){
        lbl_no_recent_search.visibility = View.GONE
        lbl_recent_search.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("OndestroyInvoked","OndestroyInvoked")
    }

    private fun saveRecentDataToDb(){
        val recentData = mRecentLocationSearchAdapter.getMostRecentData()
        Collections.reverse(recentData)
        Log.v("RecentData",recentData.toString())
        var mRecentLocation = RecentLocation(1,ArrayList<String>())
        mRecentLocation.mRecentLocations = recentData
        if(mWeatherSearchViewModel.mSaveUserFirstTime){
            mWeatherSearchViewModel.mSaveUserFirstTime = false
            mWeatherSearchViewModel.insertRecentLocationstoDb(mRecentLocation)
        }else{
            mWeatherSearchViewModel.updateRecentLocationsToDb(mRecentLocation)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_country, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
