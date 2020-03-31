package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = "recent_table")
data class RecentLocation ( @PrimaryKey
                            var mRecentLocations :ArrayList<String>)
