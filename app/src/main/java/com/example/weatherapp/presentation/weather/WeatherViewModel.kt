package com.example.weatherapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.session.SessionManager
import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _recentWeatherState = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val recentWeatherState: StateFlow<List<WeatherResponse>> = _recentWeatherState

    fun loadWeather(lat: Double, lon: Double, apiKey: String) {
        val userId = SessionManager.currentUser?.userId ?: return

        viewModelScope.launch {
            try {
                val result = getCurrentWeatherUseCase(lat, lon, apiKey)
                _weatherState.value = result

                result?.let { weather ->
                    repository.saveWeather(weather, userId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshRecentWeather() {
        viewModelScope.launch {
            repository.getRecentWeather().collect { list ->
                _recentWeatherState.value = list
            }
        }
    }
}