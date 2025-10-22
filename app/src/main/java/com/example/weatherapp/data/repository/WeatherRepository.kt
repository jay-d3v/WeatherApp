package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.session.SessionManager
import com.example.weatherapp.domain.mapper.toEntity
import com.example.weatherapp.domain.mapper.toWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class WeatherRepository(private val weatherDao: WeatherDao) {

    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse? {
        return try {
            val response = RetrofitInstance.apiService.getCurrentWeather(lat, lon, apiKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveWeather(weatherResponse: WeatherResponse, userId: Int) {
        val entity = weatherResponse.toEntity(userId)
        weatherDao.insertWeather(entity)
    }

    fun getRecentWeather(): Flow<List<WeatherResponse>> {
        val userId = SessionManager.currentUser?.userId ?: return flowOf(emptyList())
        return weatherDao.getRecentWeatherForUser(userId).map { list ->
            list.map { it.toWeatherResponse() }
        }
    }
}