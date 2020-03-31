package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.RecentLocation

@Dao
interface RecentLocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertRecentLocations(recentLocation : RecentLocation) : Long

    @Query("delete from recent_table")
    suspend fun deleteAllRecentLocation()

    @Query("Select * FROM recent_table")
   suspend fun loadAllRecentLocation() : List<RecentLocation>
}