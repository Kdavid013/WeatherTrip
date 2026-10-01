package com.example.weathertrip.models

data class RouteStop(
    val cityName: String,
    val currentTemp: String,
    val expectedTemp: String,
    val expectedTime: String,
    val currentWeatherCode: Int,
    val expectedWeatherCode: Int
)
