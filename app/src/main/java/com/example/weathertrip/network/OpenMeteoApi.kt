package com.example.weathertrip.network

import com.example.weathertrip.responses.WeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m",
        @Query("hourly") hourly: String = "temperature_2m,relative_humidity_2m"
    ): WeatherDataResponse
}