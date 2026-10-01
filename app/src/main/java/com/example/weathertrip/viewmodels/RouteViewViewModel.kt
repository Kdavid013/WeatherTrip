    package com.example.weathertrip.viewmodels


    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.weathertrip.models.Coordinates
    import com.example.weathertrip.models.RouteStop
    import com.example.weathertrip.models.getForecastAtTime
    import com.example.weathertrip.responses.toDomainModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import java.time.LocalDateTime
    import java.time.format.DateTimeFormatter

    class RouteViewViewModel : ViewModel() {

        private val _routeCities = MutableStateFlow<List<RouteStop>>(emptyList())
        val routeCities = _routeCities.asStateFlow()

        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        fun loadRoute(profile: String, coordinates: String) {
            viewModelScope.launch {
                _isLoading.value = true
                try {

                    val response = NetworkModule.mapboxApi.getRoute(profile, coordinates)
                    val route = response.routes.firstOrNull()?.toDomainModel()
                        ?: throw Exception("Nem található útvonal")

                    Log.d("RouteDebug", "Útvonal sikeresen lekérve. Távolság: ${route.distance / 1000.0} km")

                    val stops = mutableListOf<RouteStop>()

                    if (route.coordinates.isNotEmpty()) {
                        // Indulási idő (Most)
                        val startTime = LocalDateTime.now()
                        val totalDurationSeconds = route.duration.toLong()

                        // Kezdőpont (Indulási hely - első koordináta)
                        val startCoord = route.coordinates.first()
                        val startStop = fetchStopData(startCoord, "Indulás", startTime)
                        stops.add(startStop)

                        // --- DINAMIKUS KÖZTES PONTOK SZÁMÍTÁSA ---
                        val distanceInKm = route.distance / 1000.0
                        val numberOfStops = (distanceInKm / 50.0).toInt().coerceAtMost(5)

                        // Most már egy Pair-t kapunk vissza: (Eredeti Index, Koordináta)
                        val intermediateCoordsWithIndices = getIntermediateCoordinatesWithIndices(route.coordinates, numberOfStops)

                        for ((indexInList, pair) in intermediateCoordsWithIndices.withIndex()) {
                            val originalCoordIndex = pair.first
                            val coord = pair.second

                            // Kiszámoljuk arányosan az érkezés idejét
                            val progressFraction = originalCoordIndex.toDouble() / route.coordinates.size
                            val estimatedSeconds = (totalDurationSeconds * progressFraction).toLong()
                            val arrivalTime = startTime.plusSeconds(estimatedSeconds)

                            val intermediateStop = fetchStopData(coord, "Megálló ${indexInList + 1}", arrivalTime)
                            stops.add(intermediateStop)
                        }

                        // Végpont (Célállomás - utolsó koordináta)
                        if (route.coordinates.size > 1) {
                            val endCoord = route.coordinates.last()
                            val arrivalTime = startTime.plusSeconds(totalDurationSeconds)
                            val endStop = fetchStopData(endCoord, "Célállomás", arrivalTime)
                            stops.add(endStop)
                        }
                    }

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
            arrivalTime: LocalDateTime
        ): RouteStop {
            var cityName = defaultName

            var currentTemp = "N/A"
            var expectedTemp = "N/A"
            var currCode = 1
            var expCode = 1

            // Formázzuk az időt UI megjelenítésre (pl. "15:00")
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val timeLabel = arrivalTime.format(timeFormatter)

            // 1. Városnév lekérése
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

            // 2. Időjárás lekérése és adatok kibontása a modellből
            try {
                val weatherData = NetworkModule.openMeteoApi.getWeather(coord.latitude, coord.longitude).toDomainModel()

                // Jelenlegi adatok beállítása
                currentTemp = "${weatherData.currentTemp}°C"
                currCode = weatherData.currentWeatherCode

                // Várható adatok kikeresése az érkezési idő alapján
                val forecastAtArrival = weatherData.getForecastAtTime(arrivalTime)

                if (forecastAtArrival != null) {
                    expectedTemp = "${forecastAtArrival.temp}°C"
                    expCode = forecastAtArrival.weatherCode
                } else {
                    expectedTemp = currentTemp
                    expCode = currCode
                }

            } catch (e: Exception) {
                Log.e("RouteDebug", "Hiba az időjárás lekérésekor ($coord)", e)
            }

            return RouteStop(
                cityName = cityName,
                currentTemp = currentTemp,
                expectedTemp = expectedTemp,
                expectedTime = timeLabel,
                currentWeatherCode = currCode,
                expectedWeatherCode = expCode
            )
        }
    }

    // FONTOS: Módosítva, hogy (Index, Koordináta) párt adjon vissza!
    private fun getIntermediateCoordinatesWithIndices(
        coordinates: List<Coordinates>,
        numberOfStops: Int
    ): List<Pair<Int, Coordinates>> {
        if (numberOfStops <= 0 || coordinates.size < 3) return emptyList()

        val step = coordinates.size / (numberOfStops + 1)
        val selectedCoordinates = mutableListOf<Pair<Int, Coordinates>>()

        for (i in 1..numberOfStops) {
            val index = i * step
            if (index < coordinates.size) {
                selectedCoordinates.add(Pair(index, coordinates[index]))
            }
        }
        return selectedCoordinates
    }