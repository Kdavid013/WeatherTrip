package com.example.weathertrip.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrip.R
import com.example.weathertrip.apptheme.*
import com.example.weathertrip.ui.elements.AutoCompleteInputField
import com.example.weathertrip.ui.elements.InputField
import com.example.weathertrip.viewmodels.HomeViewViewModel

@Composable
fun HomeView(
    viewModel: HomeViewViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // ViewModel állapotok összegyűjtése Compose State-ként
    val startQuery by viewModel.startQuery.collectAsState()
    val startSuggestions by viewModel.startSuggestions.collectAsState()

    val destinationQuery by viewModel.destinationQuery.collectAsState()
    val destinationSuggestions by viewModel.destinationSuggestions.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Kezdőpont mező a javaslatokkal
            AutoCompleteInputField(
                label = stringResource(R.string.start_point),
                placeholder = "From",
                value = startQuery,
                suggestions = startSuggestions,
                onValueChange = { viewModel.onStartQueryChange(it) },
                onSuggestionSelected = { viewModel.selectStartLocation(it) }
            )

            // 2. Célállomás mező a javaslatokkal
            AutoCompleteInputField(
                label = "Destination",
                placeholder = "To",
                value = destinationQuery,
                suggestions = destinationSuggestions,
                onValueChange = { viewModel.onDestinationQueryChange(it) },
                onSuggestionSelected = { viewModel.selectDestinationLocation(it) }
            )

            // 3. Dátumválasztó (hagyományos InputField)
            InputField("Date", "Pick a date")
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* TODO: Útvonal lekérése a koordináták alapján */ },
                shape = CircleShape,
                modifier = Modifier.size(100.dp),
                border = BorderStroke(1.dp, fontColor),
                colors = ButtonDefaults.buttonColors(containerColor = fieldColor)
            ) {
                Text(
                    text = "GO",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = fontColor
                )
            }
        }
    }
}