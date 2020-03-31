package com.example.weatherapp.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.RecentLocation
import dagger.Module
import kotlinx.coroutines.CoroutineScope


@Database(entities = [RecentLocation::class], version = 1,exportSchema = false)
@TypeConverters(com.example.weatherapp.base.Converters::class)
abstract class RecentLocationDb : RoomDatabase() {
    abstract fun getRecentLocation(): RecentLocationDAO?

    companion object{
        @Volatile
        private var INSTANCE : RecentLocationDb? = null

        fun getDatabase(context:Context) : RecentLocationDb{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                                RecentLocationDb::class.java,
                                 "recent_location_database"   )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}