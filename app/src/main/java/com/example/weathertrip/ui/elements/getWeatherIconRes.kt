package com.example.weathertrip.ui.elements

import androidx.annotation.DrawableRes
import com.example.weathertrip.R

@DrawableRes
fun getWeatherIconRes(code: Int): Int {
    return when(code){
        0 -> R.drawable.ic_weather_sunny
        1, 2 -> R.drawable.ic_weather_partly_cloudy
        3 -> R.drawable.ic_weather_cloudy
        45, 48 -> R.drawable.ic_weather_fog
        51, 53, 55 -> R.drawable.ic_weather_drizzle
        61, 63 -> R.drawable.ic_weather_rain
        65 -> R.drawable.ic_weather_heavy_rain
        71, 73, 77 -> R.drawable.ic_weather_snow
        75, 85, 86 -> R.drawable.ic_weather_blizzard
        80, 81, 82 -> R.drawable.ic_weather_scattered_showers
        95 -> R.drawable.ic_weather_scattered_thunderstorm
        96, 99 -> R.drawable.ic_weather_hail
        else -> R.drawable.ic_weather_sunny
    }
}