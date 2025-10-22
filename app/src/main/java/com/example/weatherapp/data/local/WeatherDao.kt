package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table  WHERE userId = :userId ORDER BY timestamp DESC")
    fun getRecentWeatherForUser(userId: Int): Flow<List<WeatherEntity>>

    @Query("DELETE FROM weather_table")
    suspend fun clearAll()
}