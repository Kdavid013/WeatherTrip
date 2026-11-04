package com.example.weathertrip

import com.example.weathertrip.ui.elements.getWeatherIconRes
import org.junit.Assert.assertEquals
import org.junit.Test

class GetWeatherIconResTest {

    @Test
    fun getWeatherIconRes_returnsCorrectDrawableForCode() {
        assertEquals(R.drawable.ic_weather_sunny, getWeatherIconRes(0))

        assertEquals(R.drawable.ic_weather_partly_cloudy, getWeatherIconRes(1))
        assertEquals(R.drawable.ic_weather_partly_cloudy, getWeatherIconRes(2))

        assertEquals(R.drawable.ic_weather_cloudy, getWeatherIconRes(3))

        assertEquals(R.drawable.ic_weather_fog, getWeatherIconRes(45))
        assertEquals(R.drawable.ic_weather_fog, getWeatherIconRes(48))

        assertEquals(R.drawable.ic_weather_drizzle, getWeatherIconRes(51))
        assertEquals(R.drawable.ic_weather_drizzle, getWeatherIconRes(53))
        assertEquals(R.drawable.ic_weather_drizzle, getWeatherIconRes(55))

        assertEquals(R.drawable.ic_weather_rain, getWeatherIconRes(61))
        assertEquals(R.drawable.ic_weather_rain, getWeatherIconRes(63))

        assertEquals(R.drawable.ic_weather_heavy_rain, getWeatherIconRes(65))

        assertEquals(R.drawable.ic_weather_snow, getWeatherIconRes(71))
        assertEquals(R.drawable.ic_weather_snow, getWeatherIconRes(73))
        assertEquals(R.drawable.ic_weather_snow, getWeatherIconRes(77))

        assertEquals(R.drawable.ic_weather_blizzard, getWeatherIconRes(75))
        assertEquals(R.drawable.ic_weather_blizzard, getWeatherIconRes(85))
        assertEquals(R.drawable.ic_weather_blizzard, getWeatherIconRes(86))

        assertEquals(R.drawable.ic_weather_scattered_showers, getWeatherIconRes(80))
        assertEquals(R.drawable.ic_weather_scattered_showers, getWeatherIconRes(81))
        assertEquals(R.drawable.ic_weather_scattered_showers, getWeatherIconRes(82))

        assertEquals(R.drawable.ic_weather_scattered_thunderstorm, getWeatherIconRes(95))

        assertEquals(R.drawable.ic_weather_hail, getWeatherIconRes(96))
        assertEquals(R.drawable.ic_weather_hail, getWeatherIconRes(99))

        // Unknown / fallback
        assertEquals(R.drawable.ic_weather_sunny, getWeatherIconRes(999))
    }
}
