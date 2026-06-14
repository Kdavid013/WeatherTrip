package com.example.weathertrip.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.weathertrip.composeui.NavBar
import com.example.weathertrip.views.HomeView
import com.example.weathertrip.views.RouteView
import com.example.weathertrip.views.SettingsView
import com.example.weathertrip.views.StatisticsView


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }
    Scaffold(
        modifier = modifier,
        bottomBar = {
               NavBar(
                   selectedKey = navigationState.topLevelRoute,
                   onSelectKey = {
                       navigator.navigate(it)
                   }
               )
            }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.Home> {
                        HomeView()
                    }
                    entry<Route.Trip> {
                        RouteView()
                    }
                    entry<Route.Statictics> {
                        StatisticsView()
                    }
                    entry<Route.Settings> {
                        SettingsView()
                    }
                }
            )
        )
    }

}