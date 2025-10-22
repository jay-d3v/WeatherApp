package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.model.WeatherResponse

@Composable
fun WeatherScreen(weatherResponse: WeatherResponse?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0x33ADD8E6),
                        Color(0x66B0E0E6)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (weatherResponse == null) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherCard(weather = weatherResponse)

                Spacer(modifier = Modifier.height(16.dp))

                val sunriseTime = DateUtils.formatTimeFromUnix(
                    weatherResponse.sys.sunrise,
                    weatherResponse.timezone
                )
                val sunsetTime = DateUtils.formatTimeFromUnix(
                    weatherResponse.sys.sunset,
                    weatherResponse.timezone
                )

                TextWithLabel(label = "Sunrise", value = sunriseTime)
                Spacer(modifier = Modifier.height(8.dp))
                TextWithLabel(label = "Sunset", value = sunsetTime)
            }
        }
    }
}

@Composable
fun TextWithLabel(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Text(
            text = "$label:",
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
        androidx.compose.material3.Text(text = value)
    }
}