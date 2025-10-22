package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface WeatherAPIService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}