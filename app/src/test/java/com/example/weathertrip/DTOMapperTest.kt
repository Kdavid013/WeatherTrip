package com.example.weathertrip

import com.example.weathertrip.responses.CurrentWeatherFeatue
import com.example.weathertrip.responses.ExpextedWeatherFeature
import com.example.weathertrip.responses.GeometryDTO
import com.example.weathertrip.responses.RouteDTO
import com.example.weathertrip.responses.SuggestionItemDTO
import com.example.weathertrip.responses.WeatherDataResponse
import com.example.weathertrip.responses.toDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DTOMapperTest {

    @Test
    fun suggestionItemDTO_toDomainModel_mapsFieldsCorrectly() {
        val dto = SuggestionItemDTO(
            name = "Budapest",
            place_formatted = "Hungary",
            mapbox_id = "mapbox-123"
        )

        val model = dto.toDomainModel()

        assertEquals("mapbox-123", model.id)
        assertEquals("Budapest", model.title)
        assertEquals("Hungary", model.subtitle)
    }

    @Test
    fun weatherDataResponse_toDomainModel_mapsFieldsAndForecasts() {
        val response = WeatherDataResponse(
            current = CurrentWeatherFeatue(
                temp = 21.5,
                humidity = 60,
                weatherCode = 1
            ),
            expected = ExpextedWeatherFeature(
                time = listOf("2026-09-20T12:00", "2026-09-20T13:00"),
                temperature = listOf(22.0f, 23.5f),
                humidity = listOf(55.0f, 50.0f),
                weatherCode = listOf(1, 2)
            )
        )

        val model = response.toDomainModel()

        assertEquals(21, model.currentTemp)
        assertEquals("60", model.currentHumidity)
        assertEquals(1, model.currentWeatherCode)
        assertEquals(2, model.hourlyForecasts.size)

        assertEquals(22, model.hourlyForecasts[0].temp)
        assertEquals(55, model.hourlyForecasts[0].humidity)
        assertEquals(1, model.hourlyForecasts[0].weatherCode)

        assertEquals(23, model.hourlyForecasts[1].temp)
        assertEquals(50, model.hourlyForecasts[1].humidity)
        assertEquals(2, model.hourlyForecasts[1].weatherCode)
    }

    @Test
    fun weatherDataResponse_toDomainModel_handlesNullCurrentAndHourlyGracefully() {
        val response = WeatherDataResponse(
            current = null,
            expected = null
        )

        val model = response.toDomainModel()

        assertEquals(18, model.currentTemp)
        assertEquals("N/A", model.currentHumidity)
        assertEquals(1, model.currentWeatherCode)
        assertEquals(0, model.hourlyForecasts.size)
    }

    @Test
    fun routeDTO_toDomainModel_mapsCoordinatesAndDistance() {
        val routeDTO = RouteDTO(
            duration = 3600.0,
            distance = 50000.0,
            geometry = GeometryDTO(
                type = "LineString",
                coordinates = listOf(
                    listOf(19.0402, 47.4979),
                    listOf(18.0000, 47.0000)
                )
            )
        )

        val model = routeDTO.toDomainModel()

        assertEquals(3600.0, model.duration, 0.001)
        assertEquals(50000.0, model.distance, 0.001)
        assertEquals(2, model.coordinates.size)

        assertEquals(19.0402, model.coordinates[0].longitude, 0.0001)
        assertEquals(47.4979, model.coordinates[0].latitude, 0.0001)

        assertEquals(18.0000, model.coordinates[1].longitude, 0.0001)
        assertEquals(47.0000, model.coordinates[1].latitude, 0.0001)
    }
}
