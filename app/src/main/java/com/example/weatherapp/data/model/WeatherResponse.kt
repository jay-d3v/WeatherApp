package com.example.weatherapp.data.model

data class WeatherResponse(
    val name: String,
    val timezone: Int,
    val sys: Sys,
    val main: Main,
    val weather: List<WeatherDetails>
)
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Main(
    val temp: Double
)

data class WeatherDetails(
    val main: String,
    val description: String,
    val icon: String
)

data class Weather(
    val main: String,
    val icon: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)
