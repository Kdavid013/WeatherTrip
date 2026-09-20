package com.example.weathertrip.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.weathertrip.apptheme.fontColor
import com.example.weathertrip.apptheme.gradientColors
import com.example.weathertrip.ui.elements.RouteCard

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.viewmodels.RouteViewViewModel

@Composable
fun RouteView(
    profile: String,
    coordinates: String,
    viewModel: RouteViewViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val routeCities by viewModel.routeCities.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Amikor ez a Composable bekerül a kompozícióba, elindul a hívás
    LaunchedEffect(profile, coordinates) {
        viewModel.loadRoute(profile, coordinates)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp),
        contentAlignment = Alignment.Center // Középre igazítjuk a betöltés jelzőt
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = fontColor)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(routeCities) { cityName ->
                    // Győződj meg róla, hogy a RouteCard fogadja a cityName String paramétert!
                    RouteCard(cityName = cityName.toString())
                }
            }
        }
    }
}