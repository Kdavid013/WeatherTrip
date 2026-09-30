package com.example.weathertrip.responses

import com.example.weathertrip.models.Coordinates
import com.example.weathertrip.models.MapboxRoute
import com.example.weathertrip.models.SuggestionItemModel
import com.example.weathertrip.models.WeatherData

fun SuggestionItemDTO.toDomainModel(): SuggestionItemModel {
    return SuggestionItemModel(
        id = this.mapbox_id,
        title = this.name,
        subtitle = this.place_formatted
    )
}

fun WeatherDataResponse.toDomainModel(): WeatherData{
    return WeatherData(
        currentTemp = this.current?.temp?.toString() ?: "N/A",
        currentHumidity = this.current?.humidity?.toString() ?: "N/A"
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