package com.example.weathertrip.models

data class MapboxRoute(
    val duration: Double,
    val distance: Double,
    val coordinates: List<Coordinates>
)

data class Coordinates(
    val longitude: Double,
    val latitude: Double
)