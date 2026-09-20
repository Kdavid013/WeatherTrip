package com.example.weathertrip.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.weathertrip.composeui.NavBar
import com.example.weathertrip.viewmodels.BleViewModel
import com.example.weathertrip.views.HomeView
import com.example.weathertrip.views.RouteView
import com.example.weathertrip.views.BleScreen
import com.example.weathertrip.views.StatisticsView


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    bleViewModel: BleViewModel
) {
    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    // 1. Létrehozunk két változót a kiválasztott útvonal adatainak
    var activeProfile by remember { mutableStateOf("") }
    var activeCoordinates by remember { mutableStateOf("") }

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
                        HomeView(
                            onNavigateToRoute = { profile, coordinates ->
                                // 2. Eltároljuk az adatokat a szülőben
                                activeProfile = profile
                                activeCoordinates = coordinates

                                // 3. Átváltunk a Navigation Bar-on is szereplő Trip fülre
                                navigator.navigate(Route.Trip)
                            }
                        )
                    }

                    entry<Route.Trip> {
                        // 4. Átadjuk az eltárolt adatokat a RouteView-nak
                        RouteView(
                            profile = activeProfile,
                            coordinates = activeCoordinates
                        )
                    }

                    entry<Route.Statictics> {
                        StatisticsView()
                    }
                    entry<Route.Settings> {
                        BleScreen(viewModel = bleViewModel)
                    }
                }
            )
        )
    }
}