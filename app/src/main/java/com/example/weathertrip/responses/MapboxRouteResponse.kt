package com.example.weathertrip.responses

data class MapboxRouteResponse(
    val routes: List<RouteDTO>
)
data class RouteDTO(
    val duration: Double,
    val distance: Double,
    val geometry: GeometryDTO
)

data class GeometryDTO(
    val type: String,
    val coordinates: List<List<Double>>
)