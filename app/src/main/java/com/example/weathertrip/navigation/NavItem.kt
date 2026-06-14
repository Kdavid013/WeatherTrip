package com.example.weathertrip.navigation

import androidx.annotation.DrawableRes
import com.example.weathertrip.R

class NavItem (
    @DrawableRes val icon: Int,
    val label: String
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Home to NavItem(
        icon = R.drawable.home,
        label = "Home"
    ),
    Route.Trip to NavItem(
        icon = R.drawable.route,
        label = "Route"
    ),
    Route.Statictics to NavItem(
        icon = R.drawable.statistics,
        label = "Route"
    ),
    Route.Settings to NavItem(
        icon = R.drawable.settings,
        label = "Route"
    ),

)