package com.example.weathertrip

import com.example.weathertrip.models.Coordinates
import com.example.weathertrip.models.MapboxRoute
import com.example.weathertrip.models.RouteStop
import com.example.weathertrip.models.SuggestionItemModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ModelsTest {

    @Test
    fun coordinates_propertiesAndEquality() {
        val coord1 = Coordinates(19.0402, 47.4979)
        val coord2 = Coordinates(19.0402, 47.4979)
        val coord3 = Coordinates(18.0000, 47.0000)

        assertEquals(19.0402, coord1.longitude, 0.0001)
        assertEquals(47.4979, coord1.latitude, 0.0001)

        assertEquals(coord1, coord2)
        assertNotEquals(coord1, coord3)
    }

    @Test
    fun routeStop_propertiesAndCopy() {
        val stop = RouteStop(
            cityName = "Budapest",
            currentTemp = "20°C",
            expectedTemp = "22°C",
            expectedTime = "12:00",
            currentWeatherCode = 1,
            expectedWeatherCode = 2
        )

        assertEquals("Budapest", stop.cityName)
        assertEquals("20°C", stop.currentTemp)
        assertEquals("22°C", stop.expectedTemp)
        assertEquals("12:00", stop.expectedTime)
        assertEquals(1, stop.currentWeatherCode)
        assertEquals(2, stop.expectedWeatherCode)

        val updatedStop = stop.copy(cityName = "Győr")
        assertEquals("Győr", updatedStop.cityName)
        assertEquals("20°C", updatedStop.currentTemp)
    }

    @Test
    fun mapboxRoute_propertiesAndCoordinates() {
        val coords = listOf(Coordinates(19.0, 47.0), Coordinates(18.0, 46.0))
        val route = MapboxRoute(
            duration = 7200.0,
            distance = 150000.0,
            coordinates = coords
        )

        assertEquals(7200.0, route.duration, 0.001)
        assertEquals(150000.0, route.distance, 0.001)
        assertEquals(2, route.coordinates.size)
    }

    @Test
    fun suggestionItemModel_propertiesAndEquality() {
        val model1 = SuggestionItemModel("1", "Title", "Subtitle")
        val model2 = SuggestionItemModel("1", "Title", "Subtitle")

        assertEquals("1", model1.id)
        assertEquals("Title", model1.title)
        assertEquals("Subtitle", model1.subtitle)
        assertEquals(model1, model2)
    }
}
