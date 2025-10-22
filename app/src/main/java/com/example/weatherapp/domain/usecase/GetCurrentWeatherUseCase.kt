package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherResponse {
        return repository.getCurrentWeather(lat, lon, apiKey)!!
    }
}