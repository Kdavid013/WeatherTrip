package com.example.weathertrip

import com.example.weathertrip.models.HourlyForecast
import com.example.weathertrip.models.WeatherData
import com.example.weathertrip.models.getForecastAtTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime

class WeatherDataTest {

    @Test
    fun getForecastAtTime_returnsNullWhenListIsEmpty() {
        val weatherData = WeatherData(
            currentTemp = 20,
            currentHumidity = "50%",
            currentWeatherCode = 1,
            hourlyForecasts = emptyList()
        )

        val result = weatherData.getForecastAtTime(LocalDateTime.now())
        assertNull(result)
    }

    @Test
    fun getForecastAtTime_returnsClosestForecast() {
        val baseTime = LocalDateTime.of(2026, 9, 20, 12, 0)

        val forecast1 = HourlyForecast(time = baseTime.plusHours(1), temp = 22, humidity = 50, weatherCode = 1)
        val forecast2 = HourlyForecast(time = baseTime.plusHours(3), temp = 25, humidity = 45, weatherCode = 2)

        val weatherData = WeatherData(
            currentTemp = 20,
            currentHumidity = "50%",
            currentWeatherCode = 1,
            hourlyForecasts = listOf(forecast1, forecast2)
        )

        // Target time is 12:40, closer to 13:00 (forecast1)
        val target1 = baseTime.plusMinutes(40)
        val result1 = weatherData.getForecastAtTime(target1)
        assertEquals(forecast1, result1)

        // Target time is 14:20, closer to 15:00 (forecast2)
        val target2 = baseTime.plusHours(2).plusMinutes(20)
        val result2 = weatherData.getForecastAtTime(target2)
        assertEquals(forecast2, result2)
    }
}
