package com.example.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val city: String,
    val country: String,
    val temp: Double,
    val description: String,
    val icon: String,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long
)
