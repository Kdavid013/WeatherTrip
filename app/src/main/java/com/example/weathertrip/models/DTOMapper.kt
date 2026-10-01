package com.example.weathertrip.responses

import com.example.weathertrip.models.Coordinates
import com.example.weathertrip.models.HourlyForecast
import com.example.weathertrip.models.MapboxRoute
import com.example.weathertrip.models.SuggestionItemModel
import com.example.weathertrip.models.WeatherData
import java.time.LocalDateTime

fun SuggestionItemDTO.toDomainModel(): SuggestionItemModel {
    return SuggestionItemModel(
        id = this.mapbox_id,
        title = this.name,
        subtitle = this.place_formatted
    )
}

fun WeatherDataResponse.toDomainModel(): WeatherData {
    val forecasts = mutableListOf<HourlyForecast>()
    val times = expected?.time
    val temps = expected?.temperature
    val hums = expected?.humidity
    val code = expected?.weatherCode

    if (times != null && temps != null && hums != null && code != null) {
        for (i in times.indices) {
            try {
                forecasts.add(
                    HourlyForecast(
                        time = LocalDateTime.parse(times[i]), // Pl.: "2024-05-12T14:00" -> LocalDateTime
                        temp = temps.getOrNull(i)?.toInt() ?: continue,
                        humidity = hums.getOrNull(i)?.toInt() ?: continue,
                        weatherCode = code.getOrNull(i)?.toInt()?: continue
                    )
                )
            } catch (e: Exception) {
                // Ha egy dátum formátum hibás, átlépjük
            }
        }
    }

    return WeatherData(
        currentTemp = current?.temp?.toInt() ?: 18,
        currentHumidity = current?.humidity?.toString() ?: "N/A",
        currentWeatherCode = current?.weatherCode ?: 1,
        hourlyForecasts = forecasts
    )
}
fun RouteDTO.toDomainModel(): MapboxRoute{
    return MapboxRoute(
        duration = this.duration,
        distance = this.distance,
        coordinates = this.geometry.coordinates.map { coordList ->
            Coordinates(
                longitude = coordList[0],
                latitude = coordList[1]
            )
        }
    )
}