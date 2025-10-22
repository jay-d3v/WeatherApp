package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.model.WeatherResponse

@Composable
fun RecentWeatherScreen(recentWeatherList: List<WeatherResponse>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0x33ADD8E6),
                        Color(0x66B0E0E6)
                    )
                ))
            .padding(16.dp)
    ) {
        if (recentWeatherList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recentWeatherList) { weather ->
                    WeatherCard(weather = weather)
                }
            }
        }
    }
}