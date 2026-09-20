package com.example.weathertrip.responses

data class MapboxRetrieveResponse(
    val type: String,
    val features: List<Feature>
)

data class Feature(
    val type: String,
    val geometry: Geometry
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)