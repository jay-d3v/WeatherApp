package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.local.UserDatabase
import com.example.weatherapp.data.local.WeatherDatabase
import com.example.weatherapp.data.repository.UserRepository
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherapp.presentation.user.LoginViewModel
import com.example.weatherapp.presentation.user.LoginViewModelFactory
import com.example.weatherapp.presentation.user.LoginScreen
import com.example.weatherapp.presentation.user.RegisterScreen
import com.example.weatherapp.presentation.weather.RecentWeatherScreen
import com.example.weatherapp.presentation.weather.WeatherScreen
import com.example.weatherapp.presentation.weather.WeatherViewModel
import com.example.weatherapp.presentation.weather.WeatherViewModelFactory
import com.example.weatherapp.ui.theme.WeatherAppTheme
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableIntStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val repository by lazy { WeatherRepository(WeatherDatabase.getDatabase(this).weatherDao()) }
    private val useCase by lazy { GetCurrentWeatherUseCase(repository) }
    private val weatherViewModel: WeatherViewModel by viewModels { WeatherViewModelFactory(useCase, repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
        enableEdgeToEdge()

        setContent {
            WeatherAppTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var showRegister by remember { mutableStateOf(false) }

                val context = LocalContext.current
                val userDao = UserDatabase.getDatabase(context).userDao()
                val userRepository = UserRepository(userDao)
                val loginViewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(userRepository)
                )

                val currentWeather by weatherViewModel.weatherState.collectAsState(initial = null)

                var selectedTab by remember { mutableIntStateOf(0) }
                val tabs = listOf("Current Weather", "Recent Weather")
                val recentWeather by weatherViewModel.recentWeatherState.collectAsState(initial = emptyList())

                when {
                    !isLoggedIn && !showRegister -> {
                        LoginScreen(
                            loginViewModel = loginViewModel,
                            onLoginSuccess = {
                                isLoggedIn = true
                                weatherViewModel.refreshRecentWeather()
                            },
                            onNavigateToRegister = {
                                showRegister = true
                            }
                        )
                    }

                    showRegister -> {
                        RegisterScreen(
                            loginViewModel = loginViewModel,
                            onRegisterSuccess = {
                                showRegister = false
                            },
                            onNavigateBack = {
                                showRegister = false
                            }
                        )
                    }

                    else -> {
                        LaunchedEffect(Unit) {
                            "e841e95af9823e75632dd1e6377090bb"
                            getCurrentLocation { lat, lon ->
                                weatherViewModel.loadWeather(lat, lon, BuildConfig.WEATHER_API_KEY)
                            }
                        }

                        Column {
                            TabRow(
                                selectedTabIndex = selectedTab,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = WindowInsets.statusBars.asPaddingValues()
                                            .calculateTopPadding()
                                    )
                            ) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = selectedTab == index,
                                        onClick = { selectedTab = index },
                                        text = { Text(title) }
                                    )
                                }
                            }

                            when (selectedTab) {
                                0 -> WeatherScreen(weatherResponse = currentWeather)
                                1 -> RecentWeatherScreen(recentWeatherList = recentWeather)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }
    }

    private fun getCurrentLocation(onLocation: (Double, Double) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    onLocation(location.latitude, location.longitude)
                } else {
                    // (Manila)
                    onLocation(14.5995, 120.9842)
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }
    }
}
