package com.example.weathertrip.responses

import com.google.gson.annotations.SerializedName

data class WeatherDataResponse(
    @SerializedName("current")
    val current : CurrentWeatherFeatue? = null,
    @SerializedName("hourly")
    val expected : ExpextedWeatherFeature? = null
)
data class CurrentWeatherFeatue(
    @SerializedName("temperature_2m")
    val temp: Double? = null,
    @SerializedName("relative_humidity_2m")
    val humidity: Int? = null
)

data class ExpextedWeatherFeature(
    val time : List<String>? = null,
    @SerializedName("temperature_2m")
    val temperature: List<Float>? = null,
    @SerializedName("relative_humidity_2m")
    val humidity: List<Float>? = null
)