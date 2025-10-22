package com.example.weatherapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase

class WeatherViewModelFactory(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val repository: WeatherRepository

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(getCurrentWeatherUseCase, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}