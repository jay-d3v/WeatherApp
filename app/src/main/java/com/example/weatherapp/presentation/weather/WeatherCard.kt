package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.model.WeatherResponse

@Composable
fun WeatherCard(weather: WeatherResponse) {
    val city = weather.name
    val country = weather.sys.country
    val temp = weather.main.temp
    val weatherMain = weather.weather.firstOrNull()?.main ?: "Unknown"
    val description = weather.weather.firstOrNull()?.description ?: ""
    val sunrise = weather.sys.sunrise
    val sunset = weather.sys.sunset
    val timezoneOffset = weather.timezone

    val currentTime = System.currentTimeMillis() / 1000L + timezoneOffset
    val isNight = currentTime < sunrise || currentTime > sunset

    val weatherIcon = when {
        weatherMain.contains("rain", ignoreCase = true) -> R.drawable.wi_rain
        weatherMain.contains("cloud", ignoreCase = true) -> if (isNight) R.drawable.wi_night_alt_cloudy else R.drawable.wi_day_cloudy
        weatherMain.contains("clear", ignoreCase = true) -> if (isNight) R.drawable.wi_moon_crescent else R.drawable.wi_day_sunny
        else -> if (isNight) R.drawable.wi_moon_crescent else R.drawable.wi_day_sunny
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(320.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x66ADD8E6)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$city, $country",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = weatherMain,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${temp.toInt()}°C",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description.replaceFirstChar { it.uppercase() },
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}