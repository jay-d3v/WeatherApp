package com.example.weatherapp.domain.mapper

import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.WeatherDetails

fun WeatherResponse.toEntity(userId: Int): WeatherEntity {
    return WeatherEntity(
        userId = userId,
        city = this.name,
        country = this.sys.country,
        temp = this.main.temp,
        description = this.weather.firstOrNull()?.description ?: "",
        icon = this.weather.firstOrNull()?.icon ?: "",
        sunrise = this.sys.sunrise,
        sunset = this.sys.sunset,
        timestamp = System.currentTimeMillis() / 1000
    )
}

fun WeatherEntity.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        name = this.city,
        timezone = 0,
        sys = Sys(
            country = this.country,
            sunrise = this.sunrise,
            sunset = this.sunset
        ),
        main = Main(temp = this.temp),
        weather = listOf(
            WeatherDetails(
                main = this.description,
                description = this.description,
                icon = this.icon
            )
        )
    )
}