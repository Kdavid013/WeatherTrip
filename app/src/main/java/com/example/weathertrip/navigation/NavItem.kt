package com.example.weathertrip.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weathertrip.R

class NavItem (
    @DrawableRes val icon: Int,
    @StringRes val labelRes: Int
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Home to NavItem(
        icon = R.drawable.home,
        labelRes = R.string.nav_home
    ),
    Route.Trip to NavItem(
        icon = R.drawable.route,
        labelRes = R.string.nav_route
    ),
    Route.Statictics to NavItem(
        icon = R.drawable.statistics,
        labelRes = R.string.nav_statistics
    ),
    Route.Settings to NavItem(
        icon = R.drawable.settings,
        labelRes = R.string.nav_settings
    )
)
