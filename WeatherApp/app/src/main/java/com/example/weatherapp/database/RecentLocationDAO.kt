package com.example.weatherapp.database

import androidx.room.*
import com.example.weatherapp.model.RecentLocation

@Dao
interface RecentLocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertRecentLocations(recentLocation : RecentLocation) : Long

    @Update
    suspend fun updateRecentLocation(recentLocation: RecentLocation) : Int

    @Query("delete from recent_table")
    suspend fun deleteAllRecentLocation()

    @Query("Select * FROM recent_table")
    suspend fun loadAllRecentLocation() : List<RecentLocation>
}