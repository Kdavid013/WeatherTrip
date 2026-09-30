package com.example.weathertrip.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertrip.models.Coordinates
import com.example.weathertrip.models.RouteStop
import com.example.weathertrip.responses.toDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RouteViewViewModel : ViewModel() {
    
    private val _routeCities = MutableStateFlow<List<RouteStop>>(emptyList())
    val routeCities = _routeCities.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadRoute(profile: String, coordinates: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Lekérés a Mapbox API-tól és Mapper használata
                val response = NetworkModule.mapboxApi.getRoute(profile, coordinates)
                val route = response.routes.firstOrNull()?.toDomainModel()
                    ?: throw Exception("Nem található útvonal")

                Log.d("RouteDebug", "Útvonal sikeresen lekérve. Távolság: ${route.distance / 1000.0} km, pontok száma: ${route.coordinates.size}")

                val stops = mutableListOf<RouteStop>()

                if (route.coordinates.isNotEmpty()) {
                    // Kezdőpont (Indulási hely - első koordináta)
                    val startCoord = route.coordinates.first()
                    val startStop = fetchStopData(startCoord, "Indulás", "Most")
                    stops.add(startStop)

                    // --- DINAMIKUS KÖZTES PONTOK SZÁMÍTÁSA ---
                    val distanceInKm = route.distance / 1000.0
                    val numberOfStops = (distanceInKm / 50.0).toInt().coerceAtMost(5)
                    val intermediateCoords = getIntermediateCoordinates(route.coordinates, numberOfStops)

                    for ((index, coord) in intermediateCoords.withIndex()) {
                        val intermediateStop = fetchStopData(coord, "Megálló ${index + 1}", "Útközben")
                        stops.add(intermediateStop)
                    }

                    // Végpont (Célállomás - utolsó koordináta)
                    if (route.coordinates.size > 1) {
                        val endCoord = route.coordinates.last()
                        val endStop = fetchStopData(endCoord, "Célállomás", "Érkezéskor")
                        stops.add(endStop)
                    }
                }

                Log.d("RouteDebug", "Összesen ${stops.size} megálló töltődött be.")
                _routeCities.value = stops

            } catch (e: Exception) {
                Log.e("RouteDebug", "Hiba az útvonal lekérésekor!", e)
                _routeCities.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchStopData(
        coord: Coordinates,
        defaultName: String,
        timeLabel: String
    ): RouteStop {
        var cityName = defaultName
        var currentTemp = "N/A"

        try {
            val geoResponse = NetworkModule.mapboxApi.getCityName(
                longitude = coord.longitude,
                latitude = coord.latitude
            )
            if (geoResponse.features.isNotEmpty()) {
                cityName = geoResponse.features.firstOrNull()?.properties?.name ?: defaultName
            }
        } catch (e: Exception) {
            Log.e("RouteDebug", "Hiba a városnév lekérésekor ($coord)", e)
        }

        try {
            val weather = NetworkModule.openMeteoApi.getWeather(coord.latitude, coord.longitude).toDomainModel()
            currentTemp = weather.currentTemp
            Log.d(
                "RouteDebug",
                "Sikeres időjárás lekérés [$cityName] (${coord.latitude}, ${coord.longitude}): " +
                        "Hőmérséklet = ${weather.currentTemp}°C, Páratartalom = ${weather.currentHumidity}%"
            )
        } catch (e: Exception) {
            Log.e("RouteDebug", "Hiba az időjárás lekérésekor ($coord)", e)
        }

        return RouteStop(cityName, currentTemp, currentTemp, timeLabel)
    }
}

private fun getIntermediateCoordinates(
    coordinates: List<Coordinates>,
    numberOfStops: Int
): List<Coordinates> {
    if (numberOfStops <= 0 || coordinates.size < 3) return emptyList()

    val step = coordinates.size / (numberOfStops + 1)
    val selectedCoordinates = mutableListOf<Coordinates>()

    for (i in 1..numberOfStops) {
        // Az adott lépésköznek megfelelő indexű koordináta kiválasztása
        val index = i * step
        if (index < coordinates.size) {
            selectedCoordinates.add(coordinates[index])
        }
    }
    return selectedCoordinates
}