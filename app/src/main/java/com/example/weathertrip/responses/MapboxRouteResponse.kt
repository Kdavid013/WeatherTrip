package com.example.weathertrip.responses


data class MapboxRouteResponse(
    val waypoints: List<WaypointDTO>,
    val routes: List<RouteDTO>
)

data class WaypointDTO(
    val name: String,
    val location: List<Double>
)

data class RouteDTO(
    val duration: Double,
    val distance: Double
)