package com.example.weathertrip

import androidx.compose.runtime.mutableStateOf
import com.example.weathertrip.navigation.NavigationState
import com.example.weathertrip.navigation.Route
import com.example.weathertrip.navigation.TOP_LEVEL_DESTINATIONS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationTest {

    @Test
    fun topLevelDestinations_containsExpectedRoutes() {
        assertTrue(TOP_LEVEL_DESTINATIONS.containsKey(Route.Home))
        assertTrue(TOP_LEVEL_DESTINATIONS.containsKey(Route.Trip))
        assertTrue(TOP_LEVEL_DESTINATIONS.containsKey(Route.Statictics))
        assertTrue(TOP_LEVEL_DESTINATIONS.containsKey(Route.Settings))

        assertEquals(R.string.nav_home, TOP_LEVEL_DESTINATIONS[Route.Home]?.labelRes)
        assertEquals(R.string.nav_route, TOP_LEVEL_DESTINATIONS[Route.Trip]?.labelRes)
    }

    @Test
    fun tripDetailsRoute_storesProfileAndCoordinates() {
        val tripDetails = Route.TripDetails(profile = "mapbox/driving", coordinates = "19.0,47.0;18.0,46.0")

        assertEquals("mapbox/driving", tripDetails.profile)
        assertEquals("19.0,47.0;18.0,46.0", tripDetails.coordinates)
    }

    @Test
    fun navigationState_stacksInUse_returnsStartRouteWhenActive() {
        val topLevelState = mutableStateOf<androidx.navigation3.runtime.NavKey>(Route.Home)
        val navState = NavigationState(
            startRoute = Route.Home,
            topLevelRoute = topLevelState,
            backStacks = emptyMap()
        )

        assertEquals(listOf(Route.Home), navState.stacksInUse)

        topLevelState.value = Route.Trip
        assertEquals(listOf(Route.Home, Route.Trip), navState.stacksInUse)
    }
}
