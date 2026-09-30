package com.example.weathertrip.responses

import com.google.gson.annotations.SerializedName
import java.util.Properties

data class ReverseGeoResponse(
    val features: List<GeocodingFeature>
)
data class GeocodingFeature(
    val properties: GeocodingProperties
)

data class GeocodingProperties(
    @SerializedName("name")
    val name: String
)