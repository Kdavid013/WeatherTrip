package com.example.weathertrip.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.weathertrip.composeui.NavBar

@Composable
fun MainView (){

    Scaffold(
        modifier = Modifier.fillMaxSize(),

    ) { innerPadding ->
         ContentScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier){
    HomeView()
}