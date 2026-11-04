package com.example.weathertrip

import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.weathertrip.navigation.NavigationState
import com.example.weathertrip.navigation.Navigator
import com.example.weathertrip.navigation.Route
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigatorTest {

    private fun createNavBackStack(vararg keys: NavKey): NavBackStack<NavKey> {
        val stack = NavBackStack<NavKey>(keys.first())
        for (i in 1 until keys.size) {
            stack.add(keys[i])
        }
        return stack
    }

    @Test
    fun navigate_switchesTopLevelRoute_whenRouteIsTopLevel() {
        val topLevelState = mutableStateOf<NavKey>(Route.Home)
        val homeStack = createNavBackStack(Route.Home)
        val tripStack = createNavBackStack(Route.Trip)

        val navState = NavigationState(
            startRoute = Route.Home,
            topLevelRoute = topLevelState,
            backStacks = mapOf(
                Route.Home to homeStack,
                Route.Trip to tripStack
            )
        )

        val navigator = Navigator(navState)
        navigator.navigate(Route.Trip)

        assertEquals(Route.Trip, navState.topLevelRoute)
    }

    @Test
    fun navigate_addsToCurrentBackStack_whenRouteIsNotTopLevel() {
        val topLevelState = mutableStateOf<NavKey>(Route.Home)
        val homeStack = createNavBackStack(Route.Home)

        val navState = NavigationState(
            startRoute = Route.Home,
            topLevelRoute = topLevelState,
            backStacks = mapOf(
                Route.Home to homeStack
            )
        )

        val navigator = Navigator(navState)
        val detailRoute = Route.TripDetails("mapbox/driving", "19.0,47.0;18.0,46.0")
        navigator.navigate(detailRoute)

        assertEquals(2, homeStack.size)
        assertEquals(detailRoute, homeStack.last())
    }

    @Test
    fun goBack_resetsStartRoute_whenAtTopLevelRoute() {
        val topLevelState = mutableStateOf<NavKey>(Route.Trip)
        val homeStack = createNavBackStack(Route.Home)
        val tripStack = createNavBackStack(Route.Trip)

        val navState = NavigationState(
            startRoute = Route.Home,
            topLevelRoute = topLevelState,
            backStacks = mapOf(
                Route.Home to homeStack,
                Route.Trip to tripStack
            )
        )

        val navigator = Navigator(navState)
        navigator.goBack()

        assertEquals(Route.Home, navState.topLevelRoute)
    }

    @Test
    fun goBack_removesLastEntry_whenInSubRoute() {
        val topLevelState = mutableStateOf<NavKey>(Route.Home)
        val detailRoute = Route.TripDetails("mapbox/driving", "19.0,47.0;18.0,46.0")
        val homeStack = createNavBackStack(Route.Home, detailRoute)

        val navState = NavigationState(
            startRoute = Route.Home,
            topLevelRoute = topLevelState,
            backStacks = mapOf(
                Route.Home to homeStack
            )
        )

        val navigator = Navigator(navState)
        navigator.goBack()

        assertEquals(1, homeStack.size)
        assertEquals(Route.Home, homeStack.last())
    }
}
