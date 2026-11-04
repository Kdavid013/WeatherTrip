package com.example.weathertrip

import com.example.weathertrip.responses.Feature
import com.example.weathertrip.responses.GeocodingFeature
import com.example.weathertrip.responses.GeocodingProperties
import com.example.weathertrip.responses.Geometry
import com.example.weathertrip.responses.GeometryDTO
import com.example.weathertrip.responses.MapboxRetrieveResponse
import com.example.weathertrip.responses.MapboxRouteResponse
import com.example.weathertrip.responses.MapboxSuggestResponse
import com.example.weathertrip.responses.ReverseGeoResponse
import com.example.weathertrip.responses.RouteDTO
import com.example.weathertrip.responses.SuggestionItemDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class ResponsesTest {

    @Test
    fun mapboxSuggestResponse_instantiation() {
        val item = SuggestionItemDTO("Budapest", "Hungary", "id-1")
        val response = MapboxSuggestResponse(listOf(item))

        assertEquals(1, response.suggestions.size)
        assertEquals("Budapest", response.suggestions[0].name)
    }

    @Test
    fun mapboxRetrieveResponse_instantiation() {
        val geometry = Geometry("Point", listOf(19.0402, 47.4979))
        val feature = Feature("Feature", geometry)
        val response = MapboxRetrieveResponse("FeatureCollection", listOf(feature))

        assertEquals("FeatureCollection", response.type)
        assertEquals(1, response.features.size)
        assertEquals(19.0402, response.features[0].geometry.coordinates[0], 0.0001)
        assertEquals(47.4979, response.features[0].geometry.coordinates[1], 0.0001)
    }

    @Test
    fun reverseGeoResponse_instantiation() {
        val properties = GeocodingProperties("Debrecen")
        val feature = GeocodingFeature(properties)
        val response = ReverseGeoResponse(listOf(feature))

        assertEquals(1, response.features.size)
        assertEquals("Debrecen", response.features[0].properties.name)
    }

    @Test
    fun mapboxRouteResponse_instantiation() {
        val geometry = GeometryDTO("LineString", listOf(listOf(19.0, 47.0)))
        val route = RouteDTO(1200.0, 30000.0, geometry)
        val response = MapboxRouteResponse(listOf(route))

        assertEquals(1, response.routes.size)
        assertEquals(1200.0, response.routes[0].duration, 0.001)
        assertEquals(30000.0, response.routes[0].distance, 0.001)
    }
}
