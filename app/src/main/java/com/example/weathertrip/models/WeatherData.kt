package com.example.weathertrip.models

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

data class WeatherData(
    val currentTemp: Int,
    val currentHumidity: String,
    val currentWeatherCode: Int,
    val hourlyForecasts: List<HourlyForecast>
)

data class HourlyForecast(
    val time: LocalDateTime,
    val temp: Int,
    val humidity: Int,
    val weatherCode: Int
)

fun WeatherData.getForecastAtTime(targetTime: LocalDateTime): HourlyForecast? {
    if (hourlyForecasts.isEmpty()) return null

    // Keresi azt az elemet, ahol a percekben mért időkülönbség a legkisebb
    return hourlyForecasts.minByOrNull {
        abs(ChronoUnit.MINUTES.between(targetTime, it.time))
    }
}