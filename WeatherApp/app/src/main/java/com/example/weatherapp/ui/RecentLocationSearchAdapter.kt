package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.RecentSearchAdapterBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet

class RecentLocationSearchAdapter() : RecyclerView.Adapter<RecentLocationSearchAdapter.ViewHolder>() {

    private val mListOfRecentLocations = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecentSearchAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding : RecentSearchAdapterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindView(item : String){
            binding.recentLocations.text = item
        }
    }

    override fun getItemCount(): Int {
        return mListOfRecentLocations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mListOfRecentLocations[position])
    }

    fun setData(data: LinkedHashSet<String>){
        mListOfRecentLocations.clear()
       // Collections.reverse(data)
        mListOfRecentLocations.addAll(data)
        Collections.reverse(mListOfRecentLocations)
        notifyDataSetChanged()
    }
}