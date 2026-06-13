package com.example.weathertrip.Views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.weathertrip.R
import com.example.weathertrip.composeui.navBar
import com.example.weathertrip.navbar.NavItem

@Composable
fun MainView (){


    val navItemList = listOf(
        NavItem("Home", painterResource(R.drawable.home)),
        NavItem("Route", painterResource(R.drawable.route)),
        NavItem("Statistics", painterResource(R.drawable.statistics)),
        NavItem("Settings", painterResource(R.drawable.settings))
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            navBar(navItemList)
        }
    ) { innerPadding ->
         ContentScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier){

}